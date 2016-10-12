package com.pctrade.price.tags;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.pctrade.price.validation.FormValidator;

public class ErrorTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String errorCode;

	@Override
	public int doStartTag() throws JspException {
		pageContext.removeAttribute("fieldErrorText");

		HttpSession session = pageContext.getSession();
		Map<String, String> errorMap = FormValidator.getErrorMap(session);
		String error = errorMap.get(errorCode);

		if (StringUtils.isNotBlank(error)) {
			pageContext.setAttribute("fieldErrorText", error);
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}
	
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
