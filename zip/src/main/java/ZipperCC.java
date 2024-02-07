
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ZipperCC {
	
	public static void main(String[] args) throws IOException {
		
		if (args.length < 2) {
			System.out.println("java ZipperCC a zipfile file1 file2 ...");
			System.out.println("java ZipperCC e zipfile dir");
			return;
		}

		String mode = args[0];
		String zipfilename = args[1];
		if ("a".equals(mode)) {
			List<String> filenames = Arrays.asList(args);
			filenames = filenames.subList(2, filenames.size());
			archive(zipfilename, filenames);
		} else if ("e".equals(mode)) {
			extract(zipfilename);
		}
	}

	public static void archive(String zipfilename, List<String> filenames) throws IOException {
		try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(zipfilename))) {
			for (String filename : filenames) {
				File file = new File(filename);
				archiveFile(zos, file, file.getName());
			}
		}
	}

	private static void archiveFile(ZipArchiveOutputStream zos, File file, String filename) throws IOException {
		System.out.println(filename);
		if (file.isDirectory()) {
			zos.putArchiveEntry(new ZipArchiveEntry(filename+"/"));
			zos.closeArchiveEntry();
			for (File f : file.listFiles()) {
				archiveFile(zos, f, filename+"/"+f.getName());
			}
		} else {
			zos.putArchiveEntry(new ZipArchiveEntry(filename));
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
				byte[] buf = new byte[4096];
				int read = 0;
				while ((read = bis.read(buf)) != -1) {
					zos.write(buf, 0, read);
				}
				//bis.close();
			}
			zos.closeArchiveEntry();
		}
	}

	public static void extract(String zipfilename) throws IOException {
		File dir = new File(zipfilename.substring(0, zipfilename.indexOf(".")));
		if (! dir.exists()) {
			dir.mkdir();
		}
		try (ZipArchiveInputStream zis = new ZipArchiveInputStream(new FileInputStream(zipfilename))) {
			ZipArchiveEntry entry = zis.getNextZipEntry();
			while (entry != null) {
				extractFile(zis, dir, entry);
				try {
					entry = zis.getNextZipEntry();
				} catch (EOFException eofe) {
					entry = null;
				}
			}
			//zis.close();
		}
	}

	private static void extractFile(ZipArchiveInputStream zis, File dir, ZipArchiveEntry entry) throws IOException {
		System.out.println(entry.getName());
		File file = new File(dir, entry.getName());
		String dstDirPath = dir.getCanonicalPath();
		String dstFilePath = file.getCanonicalPath();
		if (! dstFilePath.startsWith(dstDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + entry.getName());
		}
		if (entry.isDirectory()) {
			if (! file.exists()) {
				file.mkdirs();
			}
		} else {
			byte[] buf = new byte[4096];
			int read = 0;
			try (FileOutputStream fos = new FileOutputStream(file)) {
				while ((read = zis.read(buf)) != -1) {
					fos.write(buf, 0, read);
				}
				//fos.close();
			}
		}
	}
}