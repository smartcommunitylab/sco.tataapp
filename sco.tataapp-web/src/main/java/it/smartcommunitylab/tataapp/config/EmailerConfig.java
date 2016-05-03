package it.smartcommunitylab.tataapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Configuration
public class EmailerConfig {

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		// sender.setPort(14444);
		// sender.setHost("127.0.0.1");
		//
		// Properties props = new Properties();

		// mail.smtps.localhost=mattia.smartcommunitylab.it
		// mail.smtps.auth=true
		// mail.smtp.quitwait=false
		// #mail.smtp.ssl.enable=true
		// mail.debug=false
		// props.put("mail.transport.protocol", "smtps");
		// props.put("mail.smtps.auth", false);
		// props.put("mail.debug", true);
		// sender.setJavaMailProperties(props);
		return sender;
	}
}
