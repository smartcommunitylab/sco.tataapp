package it.smartcommunitylab.tataapp.sec;

import org.springframework.stereotype.Component;

@Component
public class DefaultIdentityLookup implements IdentityLookupService {

	public static final String DEFAULT_USER = "coop_default";

	@Override
	public String getName() {
		return DEFAULT_USER;
	}

}
