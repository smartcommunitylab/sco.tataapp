package it.smartcommunitylab.tataapp.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

@Component
public class UsersProvider {

	@Value("classpath:/users.yml")
	private Resource resource;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UsersProvider.class);

	@PostConstruct
	private void init() {
		Yaml yaml = new Yaml(new Constructor(YamlUsers.class));
		try {
			YamlUsers data = (YamlUsers) yaml.load(resource.getInputStream());
			this.users = data.getUsers();
		} catch (IOException e) {
			logger.error("exception loading auth users resource");
			users = new ArrayList<AuthUser>();
		}

	}

	private List<AuthUser> users;

	public List<AuthUser> getUsers() {
		return users;
	}

	public void setUsers(List<AuthUser> users) {
		this.users = users;
	}

}
