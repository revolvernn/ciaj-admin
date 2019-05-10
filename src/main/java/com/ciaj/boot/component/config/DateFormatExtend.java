package com.ciaj.boot.component.config;

import com.ciaj.comm.utils.CalendarUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;

import java.text.*;
import java.util.Date;

/**
 * @Description 日期格式处理
 * @Author Ciaj.
 * @Date 2019/5/8 16:42
 * @Version 1.0
 */
@Log4j2
public class DateFormatExtend extends DateFormat {
	private DateFormat dateFormat;

	public DateFormatExtend() {
		this.dateFormat = new SimpleDateFormat(CalendarUtils.DATE_TIME_PATTERN);
	}


	public DateFormatExtend(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * 序列化时会执行这个方法
	 *
	 * @param date
	 * @param toAppendTo
	 * @param fieldPosition
	 * @return
	 */
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		return dateFormat.format(date, toAppendTo, fieldPosition);
	}

	/**
	 * 反序列化时执行此方法，我们先让他执行我们自己的format，如果异常则执执行他的
	 *
	 * @param source
	 * @param pos
	 * @return
	 */
	@Override
	public Date parse(String source, ParsePosition pos) {
		return getParseDate(source);
	}

	/**
	 * 反序列化时执行此方法，我们先让他执行我们自己的format，如果异常则执执行他的
	 *
	 * @param source
	 * @return
	 */
	@Override
	public Date parse(String source) {
		return getParseDate(source);
	}

	private Date getParseDate(String source) {
		try {
			return DateUtils.parseDate(source, CalendarUtils.DATE_TIME_PATTERN, CalendarUtils.DATE_PATTERN);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 此方法在objectmapper 默认的dateformat里边用到，这里也要重写
	 *
	 * @return
	 */
	@Override
	public Object clone() {
		return new DateFormatExtend();
	}
}
