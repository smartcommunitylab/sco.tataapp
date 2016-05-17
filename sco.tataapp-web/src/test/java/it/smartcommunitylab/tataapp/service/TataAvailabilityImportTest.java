package it.smartcommunitylab.tataapp.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.smartcommunitylab.tataapp.WebApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebApplication.class)
public class TataAvailabilityImportTest {

	@Autowired
	private GoogleCalendarService gcalSrv;

	@Test
	public void importing() throws IOException {
		gcalSrv.importTataAvailability("tataApp");
	}

}
