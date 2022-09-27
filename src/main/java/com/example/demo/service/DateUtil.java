package com.example.demo.service;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static Date toDate(LocalDate date) {
		return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static LocalDate getDate(PortletRequest request, String name) {
		String tmp = ParamUtil.getString(request, name);
		return parse(tmp);
	}

	public static LocalDate parse(String tmp) {
		if (tmp.isEmpty()) {
			return null;
		} else {
			return LocalDate.parse(tmp, formatter);
		}
	}

}
