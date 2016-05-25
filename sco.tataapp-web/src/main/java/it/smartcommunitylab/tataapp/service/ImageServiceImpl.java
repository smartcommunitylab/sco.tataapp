package it.smartcommunitylab.tataapp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.smartcommunitylab.tataapp.model.Babysitter;

@Component
@Profile({ "default", "prod" })
public class ImageServiceImpl implements ImageService {

	private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	@Value("${image.profile.rootFolder}")
	private String rootFolder;

	@Autowired
	private BabysitterService babysitterSrv;

	private File rootFolderObj;

	@PostConstruct
	private void init() {
		rootFolderObj = new File(rootFolder);
		if (!rootFolderObj.exists()) {
			logger.info("Folder {} not exist...creating...", rootFolder);
			if (rootFolderObj.mkdirs()) {
				logger.info("Created folder {}", rootFolder);
			} else {
				logger.error("Impossibile to create folder {}", rootFolder);
			}
		}
	}

	@Override
	public void store(byte[] content, String babysitterId) throws IOException {
		Babysitter b = babysitterSrv.load(babysitterId);
		if (b != null) {
			File agencyFolder = getAgencyFolder(b.getAgencyId());
			if (agencyFolder != null) {
				FileOutputStream fout = null;
				String picturePath = generatePicturePath(agencyFolder, babysitterId);
				try {
					fout = new FileOutputStream(picturePath);
					fout.write(content);
					fout.close();
				} catch (FileNotFoundException e) {
					logger.error("Exception creating file {} ", picturePath);
				}
			}

		} else {
			logger.error("Babysitter {} not exist..profile image not stored!");
		}

	}

	@Override
	public byte[] retrieve(String babysitterId) throws IOException {
		InputStream in = retrieveInputStream(babysitterId);
		if (in != null) {
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int c = 0;
			while ((c = in.read(buffer)) != -1) {
				o.write(buffer, 0, c);
			}
			o.close();
			return o.toByteArray();
		}
		return null;
	}

	@Override
	public void store(InputStream in, String babysitterId) throws IOException {
		if (babysitterId != null && in != null) {
			Babysitter b = babysitterSrv.load(babysitterId);
			File agencyFolder = getAgencyFolder(b.getAgencyId());
			if (agencyFolder != null) {
				String picturePath = generatePicturePath(agencyFolder, babysitterId);
				try {
					OutputStream fout = new FileOutputStream(picturePath);
					byte[] buffer = new byte[1024];
					int c = 0;
					while ((c = in.read(buffer)) != -1) {
						fout.write(buffer, 0, c);
					}
					fout.flush();
					fout.close();
				} catch (FileNotFoundException e) {
					logger.error("Exception creating file {} ", picturePath);
				}
			}
		}

	}

	@Override
	public InputStream retrieveInputStream(String babysitterId) throws IOException {
		Babysitter b = babysitterSrv.load(babysitterId);
		if (b != null) {
			File agencyFolder = getAgencyFolder(b.getAgencyId());
			if (agencyFolder != null) {
				return new FileInputStream(generatePicturePath(agencyFolder, babysitterId));
			}
		} else {
			logger.error("Babysitter {} not exist..profile image not exist!", babysitterId);
		}
		return null;
	}

	private File getAgencyFolder(String agencyId) {
		File agencyFolder = new File(rootFolderObj, agencyId);
		if (!agencyFolder.exists()) {
			if (agencyFolder.mkdir()) {
				return agencyFolder;
			} else {
				return null;
			}
		} else {
			return agencyFolder;
		}
	}

	private String generatePicturePath(File folder, String babysitterId) {
		if (folder != null && folder.isDirectory() && babysitterId != null) {
			return folder.getAbsolutePath() + "/" + babysitterId + ".png";
		}

		return null;
	}

}
