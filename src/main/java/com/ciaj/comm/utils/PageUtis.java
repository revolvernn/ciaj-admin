package com.ciaj.comm.utils;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/5 10:25
 * @Description:
 */
public class PageUtis {

    private static final String THREAD_LOCAL_PAGE_KEY = "thread_local_page_key";
    private static String THREAD_LOCAL_PAGE_ENABLE_KEY = "thread_local_page_enable_key";
    private static String ORDER_BY_DESC = "desc";
    private static String ORDER_BY_ASC = "asc";

    /**
     * 从request获取page
     *
     * @param request
     *
     * @return
     */
    public static Page getPageFromRequest(HttpServletRequest request) {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String pageEnabled = request.getParameter("pageEnabled");
        String orderBy = request.getParameter("orderBy");

        return getPage(pageNo, pageSize, pageEnabled, orderBy, request);
    }

    private static Page getPage(String pageNo, String pageSize, String pageEnabled, String orderBy, HttpServletRequest request) {
        Page page = new Page();
        if (StringUtils.isNotBlank(pageEnabled)) {
            page.setPageEnabled(BooleanUtils.toBoolean(pageEnabled));
            if (!StringUtils.isEmpty(pageNo)) {
                page.setCurrPage(Integer.parseInt(pageNo));
            }
            if (!StringUtils.isEmpty(pageSize)) {
                page.setPageSize(Integer.parseInt(pageSize));
            }
        }

        if (!StringUtils.isEmpty(orderBy)) {
            page.setOrderByEnabled(true);
            page.setOrderBy(getOrderBy(orderBy));
        } else {
            //其他table 的排序参数：
            //jqGrid sidx: mobile order: asc

            String sidx = request.getParameter("sidx");
            String order = request.getParameter("order");
            if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
                page.setOrderByEnabled(true);
                page.setOrderBy(sidx + " " + order);
            }
        }
        return page;
    }

    /**
     * 处理获取order By
     *
     * @param orderBy
     *
     * @return
     */
    private static String getOrderBy(String orderBy) {
        final String[] split = orderBy.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            if (StringUtils.isBlank(s)) continue;
            if (s.contains("-")) {
                sb = getOrderByJoin(sb, s.trim(), "-");
            } else {
                sb = getOrderByJoin(sb, s.trim(), " ");
            }
            sb.append(" ,");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    private static StringBuilder getOrderByJoin(StringBuilder sb, String source, String regex) {
        final String[] split = source.split(regex);
        sb.append(split[0]).append(" ");
        if (split.length == 1) {
            //default asc
            sb.append(ORDER_BY_ASC);
        } else {
            if (ORDER_BY_ASC.equalsIgnoreCase(split[split.length - 1])) {
                sb.append(ORDER_BY_ASC);
            } else if (ORDER_BY_DESC.equalsIgnoreCase(split[split.length - 1])) {
                sb.append(ORDER_BY_DESC);
            } else {
                //default asc
                sb.append(ORDER_BY_ASC);
            }
        }
        return sb;
    }

    public static void main(String[] args) {
        System.out.println(getOrderBy("m.id,m.name,m.del-ASC,m.d DESC"));

        String s = "id  asc,name--descd";

        System.out.println(getOrderBy(s));

    }

    /**
     * 将page放到threadlocal中
     *
     * @param page
     */
    public static void putPageToThreadLocal(Page page) {
        ThreadContext.put(THREAD_LOCAL_PAGE_KEY, page);
    }

    /**
     * 从threadlocal中获取page
     */
    public static Page getPageFromThreadLocal() {
        return (Page) ThreadContext.get(THREAD_LOCAL_PAGE_KEY);
    }

    /**
     * 将page放到threadlocal中,并返回
     */
    public static Page getPageFromThreadLocal(Page page) {
        putPageToThreadLocal(page);
        return getPageFromThreadLocal();
    }

    /**
     * 从threadlocal中销毁
     */
    private static void destroyPageFromThreadLocal() {
        ThreadContext.remove(THREAD_LOCAL_PAGE_KEY);

    }

    public static void pageStart() {
        ThreadContext.put(THREAD_LOCAL_PAGE_ENABLE_KEY, true);
    }

    /**
     * 开启分页
     *
     * @return
     */
    public static com.github.pagehelper.Page startPage() {
        com.github.pagehelper.Page p = null;
        Page page = getPageFromThreadLocal();
        //开启分页
        if (page != null && page.getPageEnabled()) {
            p = PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        }
        return p;
    }

    /**
     * 开启分页,排序
     *
     * @return
     */
    public static com.github.pagehelper.Page startPageAndOrderBy() {
        com.github.pagehelper.Page p = null;
        Page page = getPageFromThreadLocal();
        //开启分页
        if (page != null && page.getPageEnabled()) {
            p = PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        }
        //开启排序
        if (page != null && page.getOrderByEnabled() && StringUtils.isNotBlank(page.getOrderBy())) {
            PageHelper.orderBy(page.getOrderBy());
        }
        return p;
    }

    /**
     * 开启排序
     *
     * @return
     */
    public static void startOrderBy() {
        Page page = getPageFromThreadLocal();
        //开启排序
        if (page != null && page.getOrderByEnabled() && StringUtils.isNotBlank(page.getOrderBy())) {
            PageHelper.orderBy(page.getOrderBy());
        }
    }

    /**
     * 是否开启排序
     *
     * @return
     */
    public static boolean isOrderBy() {
        Page page = getPageFromThreadLocal();
        //开启排序
        if (page != null && page.getOrderByEnabled() && StringUtils.isNotBlank(page.getOrderBy())) {
           return true;
        }else {
            return false;
        }
    }

    public static void pageEnd() {
        destroyPageFromThreadLocal();
    }

    public static boolean isPageStarted() {
        return ThreadContext.get(THREAD_LOCAL_PAGE_ENABLE_KEY) != null;
    }

}
