package com.ustglobal.qubz.authentication.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ustglobal.qubz.model.SSOSession;
import com.ustglobal.qubz.resttemplate.SSOSessionClient;

@Component("cookiesAuthenticationSuccessHandler")
public class CookiesAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	SSOSessionClient ssoSessionClient;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("onAuthenticationSuccess: username: " + authentication.getName());

		SSOSession ssosession = ssoSessionClient.createSSOSession("KYLIN", authentication.getPrincipal().toString());

		Cookie cookie = new Cookie("sso", ssosession.getId());
		cookie.setMaxAge(2592000);
//		cookie.setSecure(true);
//		cookie.setDomain("");
		cookie.setPath("/");

		response.addCookie(cookie);
		response.sendRedirect("/qubz-sso/welcome");
//		super.onAuthenticationSuccess(request, response, authentication);

	}
}
