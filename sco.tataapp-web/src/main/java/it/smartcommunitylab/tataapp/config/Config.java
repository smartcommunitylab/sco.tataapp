package it.smartcommunitylab.tataapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import it.smartcommunitylab.tataapp.web.GoogleAuthHelper;

@Configuration
public class Config {

	@Value("classpath:/client_secret.json")
	private Resource googleKeys;

	@Value("${google.callback.url}")
	private String googleCallbackUrl;

	@Bean
	public GoogleAuthHelper googleAuthHelper() {
		try {
			return new GoogleAuthHelper(googleKeys.getInputStream(), googleCallbackUrl);
		} catch (IOException e) {
			return null;
		}
	}
}
