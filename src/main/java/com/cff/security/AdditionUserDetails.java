package com.cff.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AdditionUserDetails extends WebAuthenticationDetails {
	/**
	* 
	*/
	private static final long serialVersionUID = 6975601077710753878L;
	private final String adminVal;

	public AdditionUserDetails(HttpServletRequest request) {
		super(request);
		adminVal = request.getParameter("adminVal");
	}

	public String getAdminVal() {
		return adminVal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("; adminVal: ").append(adminVal);
		return sb.toString();
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String ajaxFlag = request.getHeader("X-Requested-With");
		return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
	}

}
