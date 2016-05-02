package it.smartcommunitylab.tataapp.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import it.smartcommunitylab.tataapp.model.Child;

@Component
public class TemplateMailService {

	@Autowired
	private TemplateEngine templateEngine;

	public String generate(Map<String, Object> parameters, String templateName) {
		final Context ctx = new Context();

		// add builtin calendar to work with dates
		parameters.put("cal-utils", Calendar.getInstance());
		ctx.setVariables(parameters);
		return templateEngine.process(templateName, ctx);
	}

	/**
	 * Extended object Child to easily formatting birthDate in e-mail
	 * notification
	 *
	 */
	public class EmailChild extends Child {
		private Date birthDateObj;

		public EmailChild(Child c) {
			super(c.getBirthDate(), c.isDisability());
			birthDateObj = new Date(c.getBirthDate());
		}

		public Date getBirthDateObj() {
			return birthDateObj;
		}

		public void setBirthDateObj(Date birthDateObj) {
			this.birthDateObj = birthDateObj;
		}
	}
}
