import java.io.File;

public class MkDir {

	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("java MkDir m dirname");
			System.out.println("java MkDir r dirname");
			return;
		}
		
		String mode = args[0];
		String end = args[1];
		File dir = new File("/mnt/d/zzz", end);
		if ("m".equals(mode)) {
			if (! dir.exists()) {
				dir.mkdir();
			}
		} else if ("r".equals(mode)) {
			delete(dir);
		}
	}
	
	private static void delete(File dir) {
		if (dir.isDirectory()) {
			for (File f : dir.listFiles()) {
				delete(f);
			}
		}
		dir.delete();
	}
}
