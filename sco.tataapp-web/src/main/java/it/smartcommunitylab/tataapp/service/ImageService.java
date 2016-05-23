package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
	public void store(byte[] content, String babysitterId) throws IOException;

	public byte[] retrieve(String babysitterId) throws IOException;

	public void store(InputStream in, String babysitterId) throws IOException;

	public InputStream retrieveInputStream(String babysitterId) throws IOException;
}
