package it.smartcommunitylab.tataapp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class TemplateMailService {

	@Autowired
	private TemplateEngine templateEngine;

	public String generate(Map<String, Object> parameters, String templateName) {
		final Context ctx = new Context();

		ctx.setVariables(parameters);
		return templateEngine.process(templateName, ctx);
	}
}
