package it.smartcommunitylab.tataapp.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
	public void store(byte[] content, String filename);

	public byte[] retrieve(String filename) throws IOException;

	public void store(InputStream in, String filename);

	public InputStream retrieveInputStream(String filename) throws IOException;
}
