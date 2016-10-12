package com.pctrade.price.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public final class HttpUtils {

	private HttpUtils() {
		throw new InstantiationError("No need instances for static content!");
	}
	
	public static void Encode(HttpServletRequest request, HttpServletResponse response, String characterEncoding) throws UnsupportedEncodingException{
		request.setCharacterEncoding(characterEncoding);
		response.setCharacterEncoding(characterEncoding);
	}		

	public static void forward(String url, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	public static void redirect(String url, HttpServletResponse response) throws ServletException, IOException {
		String encodedUrl = response.encodeRedirectURL(url);
		response.sendRedirect(encodedUrl);
	}

	public static Integer getIntParam(HttpServletRequest request, String paramName) {
		if (StringUtils.isBlank(paramName)) {
			throw new IllegalArgumentException("Parameter name can not be empty!");
		}

		try {
			String intStr = request.getParameter(paramName);
			Integer intValue = new Integer(intStr);

			return intValue;

		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isIntNotNull(HttpServletRequest request, String paramName) {
		Integer value = getIntParam(request, paramName);
		return (value != null);
	}

	public static boolean isIntNull(HttpServletRequest request, String paramName) {
		return !isIntNotNull(request, paramName);
	}

	public static boolean isPositiveInt(HttpServletRequest request, String paramName) {
		if (isIntNull(request, paramName)) {
			return false;
		}
		Integer value = getIntParam(request, paramName);
		return (value > 0);
	}

	public static boolean isNegativeInt(HttpServletRequest request, String paramName) {
		return !isPositiveInt(request, paramName);
	}

	public static boolean isFromBiggerThanTill(HttpServletRequest request, String paramNameFrom, String paramNameTill) {
		if (isIntNotNull(request, paramNameFrom) && isIntNotNull(request, paramNameTill)) {

			return getIntParam(request, paramNameFrom) > getIntParam(request, paramNameTill);

		} else {
			return false;
		}
	}
}
