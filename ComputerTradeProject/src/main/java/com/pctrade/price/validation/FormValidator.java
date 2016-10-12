package com.pctrade.price.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pctrade.price.utils.HttpUtils;

public class FormValidator {
	public static final String FROM_TEXT_ERROR_CODE = "idFrom.text.error";
	public static final String FROM_NEGATIVE_ERROR_CODE = "idFrom.negative.error";
	public static final String TILL_TEXT_ERROR_CODE = "idTill.text.error";
	public static final String TILL_NEGATIVE_ERROR_CODE = "idTill.negative.error";
	public static final String FROM_LESS_ERROR_CODE = "idFrom.less.error";
	public static final String TILL_BIGGER_ERROR_CODE = "idTill.bigger.error";

	private static final String FROM_NEGATIVE_ERROR_TEXT = "ID 'From' must be greater than 0";
	private static final String TILL_NEGATIVE_ERROR_TEXT = "ID 'Till' must be greater than 0";
	private static final String FROM_TEXT_ERROR_TEXT = "Please input correct value";
	private static final String TILL_TEXT_ERROR_TEXT = "Please input correct value";
	private static final String FROM_LESS_ERROR_TEXT = "ID 'From' must be less than 'Till'";
	private static final String TILL_BIGGER_ERROR_TEXT = "ID 'Till' must be bigger than 'From'";

	public static final String FORM_ERRORS_MAP = "form.errors";

	public static Map<String, String> getErrorMap(HttpSession session) {
		@SuppressWarnings("unchecked")
		Map<String, String> errorMap = (Map<String, String>) session.getAttribute(FormValidator.FORM_ERRORS_MAP);
		if (errorMap == null) {
			errorMap = new HashMap<String, String>();
		}
		return errorMap;
	}
	
	public static boolean validate(HttpServletRequest request) {
		Map<String, String> errorMap = new HashMap<String, String>();

		if (HttpUtils.isIntNull(request, "idFrom")) {
			errorMap.put(FROM_TEXT_ERROR_CODE, FROM_TEXT_ERROR_TEXT);
		} else {
			if (HttpUtils.isNegativeInt(request, "idFrom")) {
				errorMap.put(FROM_NEGATIVE_ERROR_CODE, FROM_NEGATIVE_ERROR_TEXT);
			}
		}

		if (HttpUtils.isIntNull(request, "idTill")) {
			errorMap.put(TILL_TEXT_ERROR_CODE, TILL_TEXT_ERROR_TEXT);
		} else {
			if (HttpUtils.isNegativeInt(request, "idTill")) {
				errorMap.put(TILL_NEGATIVE_ERROR_CODE, TILL_NEGATIVE_ERROR_TEXT);
			}
		}

		if (HttpUtils.isIntNull(request, "poolCapacity")) {
			errorMap.put(TILL_TEXT_ERROR_CODE, TILL_TEXT_ERROR_TEXT);
		} else {
			if (HttpUtils.isNegativeInt(request, "poolCapacity")) {
				errorMap.put(TILL_NEGATIVE_ERROR_CODE, TILL_NEGATIVE_ERROR_TEXT);
			}
		}

		if (HttpUtils.isFromBiggerThanTill(request, "idFrom", "idTill")) {
			errorMap.put(FROM_LESS_ERROR_CODE, FROM_LESS_ERROR_TEXT);
			errorMap.put(TILL_BIGGER_ERROR_CODE, TILL_BIGGER_ERROR_TEXT);
		}

		if (!errorMap.isEmpty()) {
			HttpSession session = request.getSession();
			session.setAttribute(FormValidator.FORM_ERRORS_MAP, errorMap);
			return false;
		}
		return true;
	}	
}
