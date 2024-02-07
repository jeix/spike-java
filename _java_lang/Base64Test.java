import java.util.Base64;

public class Base64Test {

	public static void main(String[] args) {

		String s = "foo bar";
		
		byte[] encoded = Base64.getEncoder().encode(s.getBytes());
		String t = new String(encoded);
		System.out.println("["+t+"]");
		
		byte[] decoded = Base64.getDecoder().decode(encoded);
		String u = new String(decoded);
		System.out.println("["+u+"]");
	}
}