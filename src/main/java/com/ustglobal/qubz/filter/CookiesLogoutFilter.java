package com.ustglobal.qubz.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.Assert;

import com.ustglobal.qubz.resttemplate.SSOSessionClient;

public class CookiesLogoutFilter extends LogoutFilter {
	private final LogoutHandler handler;
	private final LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	SSOSessionClient ssoSessionClient;

	public CookiesLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
		super(logoutSuccessHandler, handlers);
		this.handler = new CompositeLogoutHandler(handlers);
		Assert.notNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
		this.logoutSuccessHandler = logoutSuccessHandler;
		setFilterProcessesUrl("/logout");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) res;
		// invalidate security context

		logger.info("<------------------LOGOUTFILTER------------------->");
		if (requiresLogout(httpRequest, httpResponse)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			logger.debug("Logging out user '" + auth + "' and transferring to logout destination");

			// logs the user out from SecurityContext
			this.handler.logout(httpRequest, httpResponse, auth);

			// remove cookie data
			removeSSOCookies(httpRequest, httpResponse);

			logoutSuccessHandler.onLogoutSuccess(httpRequest, httpResponse, auth);
			return;
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	private void removeSSOCookies(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		logger.info("removing cookies");
		Cookie[] cookies = httpRequest.getCookies();
		if (null != cookies) {
			logger.info("==================COOKIES=================");
			for (Cookie cookie : cookies) {

				if (cookie.getName().equals("JSESSIONID") || cookie.getName().equals("sso")) {

					if (cookie.getName().equals("JSESSIONID")) {
						cookie.setValue("");
						cookie.setMaxAge(0);
						cookie.setPath("/");
					}
					if (cookie.getName().equals("sso")) {
						ssoSessionClient.deleteSSOSession(cookie.getValue());
						cookie.setValue("");
						cookie.setMaxAge(0);
						cookie.setPath("/");
						cookie.setDomain("");
					}
					httpResponse.addCookie(cookie);
				}
			}
		}
	}

}
