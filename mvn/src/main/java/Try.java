import java.util.Arrays;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Try {
  public static void main(String[] args) {
    if (false) {
		String s = null;
		try {
			System.out.println(Integer.valueOf(s));
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		s = "141652569";
		System.out.println(Integer.valueOf(s).toString());
	}
	if (false) {
		String s = "[12345]";
		System.out.println(s.substring(1, s.length()-1));
	}
	{
		List<String> list = Arrays.asList("77332dc7-a617-4a63-83a8-89f2c195598f","eaf14bd8-56f0-4659-b79b-e2f12c37814c","cb433d09-0aad-4cb6-9180-c70e9a313081").subList(0, 3);
		System.out.println(list.size());
		System.out.println(String.join(",", list.toArray(new String[] {})));
	}
	{
		String bucketName = "sisto-bbox";
		String objectName = "111111/emggps_.dat";
		String downloadPath = "D:\\zzz\\s3test\\" + bucketName + "\\" + objectName.replaceAll("/", "\\\\");
		System.out.println(downloadPath);
	}
	if (false) {
		byte[] encoded = Base64.getEncoder().encode("foo bar".getBytes());
		byte[] decoded = Base64.getDecoder().decode(encoded);
		String t = new String(decoded);
		System.out.println("["+t+"]");
	}
  }
}