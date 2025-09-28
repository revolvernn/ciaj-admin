package com.ciaj.comm.utils;

import com.ciaj.boot.component.serializer.DateTimeFormat;
import com.ciaj.boot.component.serializer.DecimalFormat;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.*;


/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/26 17:05
 * @Version 1.0
 */
@Log4j2
public class ExcelUtil {
    private String fileName;
    private String[] filed;
    private String[] title;
    private List<?> data;

    /**
     * 数据构建
     *
     * @param fileName 文件名
     * @param filed    数据字段属性
     * @param title    数据标题字段
     * @param data     数据
     * @return
     */
    public ExcelUtil build(String fileName, String[] filed, String[] title, List<?> data) {
        this.fileName = fileName;
        this.filed = filed;
        this.title = title;
        this.data = data;
        return this;
    }

    // 判断文件是否为excel文件
    public static boolean isExcel(String name) {
        if (name.contains(".")) {
            name = name.substring(name.lastIndexOf("."));
            return ".xls".equalsIgnoreCase(name) || ".xlsx".equalsIgnoreCase(name);
        }
        return false;
    }


    /**
     * 导出Excel
     *
     * @param request
     * @param response
     */
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        log.info("导出解析开始，fileName:{}", fileName);
        try {
            //实例化HSSFWorkbook
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建一个Excel表单，参数为sheet的名字
            HSSFSheet sheet = workbook.createSheet(fileName);
            //设置表头
            setTitle(sheet);
            //设置单元格并赋值
            setData(sheet);
            //设置浏览器下载
            setBrowser(request, response, workbook);
            log.info("导出解析成功!");
        } catch (Exception e) {
            log.info("导出解析失败!", e);
        }
    }

    /**
     * 设置表头
     *
     * @param sheet
     */
    private void setTitle(HSSFSheet sheet) {
        try {
            //创建标题行
            Row row = sheet.createRow(0);
            for (int i = 0; i < this.title.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(title[i]);
            }
        } catch (Exception e) {
            log.info("导出时设置表头失败！", e);
        }
    }

    /**
     * 表格赋值
     *
     * @param sheet
     */
    private void setData(HSSFSheet sheet) {
        try {
            for (int i = 0; i < data.size(); i++) {
                Object obj = data.get(i);
                Row rows = sheet.createRow(i + 1);
                if (obj instanceof Map) {
                    Map map = (Map) obj;
                    for (int j = 0; j < filed.length; j++) {
                        Cell cells = rows.createCell(j);
                        Object o = map.get(filed[j]);
                        cells.setCellValue(o.toString());
                    }
                } else {
                    for (int j = 0; j < filed.length; j++) {
                        Cell cells = rows.createCell(j);
                        Object value = invokeMethod(obj, filed[j]);
                        cells.setCellValue(value.toString());
                    }
                }
            }
        } catch (Exception e) {
            log.info("表格赋值失败！", e);
        }
    }

    /**
     * 赋值
     *
     * @param cells
     * @param o
     */
    private void setCellVale(Cell cells, Object o) {
        String v = "";
        if (o != null && o instanceof Date) {
            Date d = (Date) o;
            v = CalendarUtils.DateToString(d, CalendarUtils.DATE_TIME_PATTERN);
        } else {
            v = o != null ? o.toString() : "";
        }
        cells.setCellValue(v);
    }

    /**
     * 获取属性值
     *
     * @param owner
     * @param fieldname
     * @return
     * @throws Exception
     */
    private Object invokeMethod(Object owner, String fieldname) throws Exception {
        String methodName = "get" + StringUtil.underlineToHumpUpperCase(fieldname);
        Class<?> ownerClass = owner.getClass();
        Object invoke = ownerClass.getMethod(methodName).invoke(owner);
        String value = "";
        if (invoke == null) {
            return value;
        }
        value = invoke.toString();

        if (invoke instanceof BigDecimal) {
            DecimalFormat d = FieldUtil.getClassFirstAnnotation(ownerClass, methodName, DecimalFormat.class);
            if (d != null) {
                BigDecimal b = (BigDecimal) invoke;
                BigDecimal bigDecimal = new BigDecimal(b.toString()).setScale(d.scale(), d.roundingMode());
                value = bigDecimal.toString();
            }

        } else if (invoke instanceof Date) {
            Date date = (Date) invoke;
            DateTimeFormat d = FieldUtil.getClassFirstAnnotation(ownerClass, methodName, DateTimeFormat.class);
            value = CalendarUtils.format(date, d != null ? d.pattern() : CalendarUtils.DATE_TIME_PATTERN);
        }

        return value;
    }

    /**
     * 设置浏览器参数
     *
     * @param request
     * @param response
     * @param workbook
     */
    private void setBrowser(HttpServletRequest request, HttpServletResponse response, HSSFWorkbook workbook) {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            final String USER_AGENT = request.getHeader("USER-AGENT");
            String finalFileName = null;
            if (StringUtil.contains(USER_AGENT, "MSIE")) {//IE浏览器
                finalFileName = URLEncoder.encode(fileName, "utf-8");
            } else if (StringUtil.contains(USER_AGENT, "Edge")) {//IE Edge 浏览器
                finalFileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
            } else if (StringUtil.contains(USER_AGENT, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "utf-8");
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + finalFileName + "-" + CalendarUtils.format(new Date(), "yyyy-MM-dd") + ".xlsx");
            Cookie cookie = new Cookie("fileDownload", "true");
            cookie.setComment(request.getRequestURI());
            cookie.setPath("/");
            response.addCookie(cookie);

            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！", e);
        }
    }


    /**
     * 导入
     *
     * @param fileName 文件名
     * @return
     */
    public static List<Object[]> importExcel(String fileName) {
        log.info("导入解析开始，fileName:{}", fileName);
        try {
            List<Object[]> list = new ArrayList<>();
            InputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[row.getPhysicalNumberOfCells()];
                int index = 0;
                for (Cell cell : row) {
                    if (cell.getCellType().equals(NUMERIC)) {
                        objects[index] = (int) cell.getNumericCellValue();
                    }
                    if (cell.getCellType().equals(STRING)) {
                        objects[index] = cell.getStringCellValue();
                    }
                    if (cell.getCellType().equals(BOOLEAN)) {
                        objects[index] = cell.getBooleanCellValue();
                    }
                    if (cell.getCellType().equals(ERROR)) {
                        objects[index] = cell.getErrorCellValue();
                    }
                    index++;
                }
                list.add(objects);
            }
            log.info("导入文件解析成功！");
            return list;
        } catch (Exception e) {
            log.info("导入文件解析失败！", e);
        }
        return null;
    }
}
