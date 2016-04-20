package it.smartcommunitylab.tataapp.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public class EchoController {

	@RequestMapping(value = "/api/echo")
	public String echo(@RequestBody String message) {
		return message;
	}
}
