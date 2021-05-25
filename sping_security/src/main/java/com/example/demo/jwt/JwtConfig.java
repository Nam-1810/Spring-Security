package com.example.demo.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

	private String secretKey;
	
	private String tokenPrefix;
	
	private Integer expirationDay;

	
	
	public JwtConfig() {
		super();
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public Integer getExpirationDay() {
		return expirationDay;
	}

	public void setExpirationDay(Integer expirationDay) {
		this.expirationDay = expirationDay;
	}
	
	
	
}
