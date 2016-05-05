package it.smartcommunitylab.tataapp.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateMailService templateGenerator;

	/*
	 * Send HTML mail (simple)
	 */
	public void sendSimpleMail(String fromAddress, final String recipientEmail, String mailSubject,
			final Map<String, Object> parameters, String templateName) throws MessagingException {

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setSubject(mailSubject);
		message.setFrom(fromAddress);
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = templateGenerator.generate(parameters, templateName);
		message.setText(htmlContent, true);

		// Send email
		mailSender.send(mimeMessage);

	}
}
