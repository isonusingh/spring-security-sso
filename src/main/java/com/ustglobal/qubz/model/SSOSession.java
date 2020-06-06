package com.ustglobal.qubz.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SSOSession {
	@JsonProperty
	private String applicationName;
	@JsonProperty
	private String createdTimestamp;
	@JsonProperty
	private String id;
	@JsonProperty
	private int sessionDuration;
	@JsonProperty
	private String updatedTimestamp;
	@JsonProperty
	private String username;

	public SSOSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SSOSession(String applicationName, String createdTimestamp, String id, int sessionDuration,
			String updatedTimestamp, String username) {
		super();
		this.applicationName = applicationName;
		this.createdTimestamp = createdTimestamp;
		this.id = id;
		this.sessionDuration = sessionDuration;
		this.updatedTimestamp = updatedTimestamp;
		this.username = username;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSessionDuration() {
		return sessionDuration;
	}

	public void setSessionDuration(int sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Session [applicationName=" + applicationName + ", createdTimestamp=" + createdTimestamp + ", id=" + id
				+ ", sessionDuration=" + sessionDuration + ", updatedTimestamp=" + updatedTimestamp + ", username="
				+ username + "]";
	}

}
