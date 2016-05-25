package it.smartcommunitylab.tataapp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.el.MethodNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Profile({ "mock" })
public class MockImageService implements ImageService {

	@Value("classpath:/mock-avatar/FA01.png")
	private Resource avatar;

	@Override
	public void store(byte[] content, String babysitterId) {
		throw new MethodNotFoundException("method not implemented");

	}

	@Override
	public byte[] retrieve(String babysitterId) throws IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int c = 0;
		while ((c = avatar.getInputStream().read(buffer)) != -1) {
			o.write(buffer, 0, c);
		}
		o.close();
		return o.toByteArray();
	}

	@Override
	public void store(InputStream in, String babysitterId) {
		// do nothing
	}

	@Override
	public InputStream retrieveInputStream(String babysitterId) throws IOException {
		return avatar.getInputStream();
	}

}
