
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {
	
	public static void main(String[] args) throws IOException {
		
		if (args.length < 2) {
			System.out.println("java Zipper a zipfile file1 file2 ...");
			System.out.println("java Zipper e zipfile dir");
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
		if ("ca".equals(mode)) {
			List<String> filenames = Arrays.asList(args);
			filenames = filenames.subList(2, filenames.size());
			archive(zipfilename, filenames);
		} else if ("ce".equals(mode)) {
			extract(zipfilename);
		}
	}

	public static void archive(String zipfilename, List<String> filenames) throws IOException {

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipfilename))) {
			for (String filename : filenames) {
				File file = new File(filename);
				archiveFile(zos, file, file.getName());
			}
		}
	}

	private static void archiveFile(ZipOutputStream zos, File file, String filename) throws IOException {
		System.out.println(filename);
		if (file.isDirectory()) {
			zos.putNextEntry(new ZipEntry(filename+"/"));
			zos.closeEntry();
			for (File f : file.listFiles()) {
				archiveFile(zos, f, filename+"/"+f.getName());
			}
		} else {
			zos.putNextEntry(new ZipEntry(filename));
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
				byte[] buf = new byte[4096];
				int read = 0;
				while ((read = bis.read(buf)) != -1) {
					zos.write(buf, 0, read);
				}
				//bis.close();
			}
			zos.closeEntry();
		}
	}

	public static void extract(String zipfilename) throws IOException {

		File dir = new File(zipfilename.substring(0, zipfilename.indexOf(".")));
		if (! dir.exists()) {
			dir.mkdir();
		}
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfilename))) {
			ZipEntry entry = zis.getNextEntry();
			while (entry != null) {
				extractFile(zis, dir, entry);
				entry = zis.getNextEntry();
			}
			zis.closeEntry();
			//zis.close();
		}
	}

	private static void extractFile(ZipInputStream zis, File dir, ZipEntry entry) throws IOException {
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