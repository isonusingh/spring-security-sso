package com.ustglobal.qubz.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

import com.ustglobal.qubz.model.SSOSession;
import com.ustglobal.qubz.resttemplate.SSOSessionClient;

public class QubzPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Autowired
	SSOSessionClient ssoSessionClient;

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sso") && !StringUtils.isEmpty(cookie.getValue())
						&& !cookie.getValue().equalsIgnoreCase("anonymousUser:")) {
					SSOSession ssoSession = ssoSessionClient.getSSOSession(cookie.getValue());
					if (null != ssoSession.getUsername()) {
						return ssoSession.getUsername();
					}
				}
			}
		}
		return null;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sso") && !StringUtils.isEmpty(cookie.getValue())) {
					if (!StringUtils.isEmpty(cookie.getValue())) {
						return cookie.getValue();
					}
				}
			}
		}
		return null;
	}

}
