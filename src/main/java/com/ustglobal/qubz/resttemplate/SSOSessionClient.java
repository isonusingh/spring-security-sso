package com.ustglobal.qubz.resttemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ustglobal.qubz.model.SSOSession;

@Component
public class SSOSessionClient {

	private String url = "http://hostname:port/ssosession";
	private RestTemplate restTemplate = new RestTemplate();

	public SSOSession createSSOSession(String applicationName, String username) {
		HttpHeaders requestSSOSessiontHeader = null;
		MultiValueMap<String, String> requestSSOSessionMap = null;
		HttpEntity<MultiValueMap<String, String>> ssoSessionRequest = null;
		ResponseEntity<SSOSession> ssoSessionResponse = null;
		try {
			requestSSOSessiontHeader = new HttpHeaders();
			requestSSOSessiontHeader.setContentType(MediaType.MULTIPART_FORM_DATA);

			requestSSOSessionMap = new LinkedMultiValueMap<String, String>();
			requestSSOSessionMap.add("applicationName", applicationName);
			requestSSOSessionMap.add("username", username);

			ssoSessionRequest = new HttpEntity<MultiValueMap<String, String>>(requestSSOSessionMap,
					requestSSOSessiontHeader);

			ssoSessionResponse = restTemplate.postForEntity(url, ssoSessionRequest, SSOSession.class);
			return ssoSessionResponse.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			requestSSOSessiontHeader = null;
			requestSSOSessionMap = null;
			ssoSessionRequest = null;
		}
		return null;
	}

	public SSOSession getSSOSession(String ssoSessionId) {
		try {
			return restTemplate.getForObject(url + "/{ssoSessionId}", SSOSession.class, ssoSessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteSSOSession(String ssoSessionId) {
		try {
			restTemplate.delete(url + "/{ssoSessionId}", ssoSessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
