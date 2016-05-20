package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({ "default", "prod" })
public class ImageServiceImpl implements ImageService {

	@Override
	public void store(byte[] content, String filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] retrieve(String filename) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(InputStream in, String filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream retrieveInputStream(String filename) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
