import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class SerializeTest {

	public static void main(String[] args) {

		{
			String s = "str.foo.bar";
			System.out.println(serialize(s));

			Bar bar = new Bar();
			bar.setX("qaz");
			bar.setY("wsx");
			System.out.println(serialize(bar));
		}

		{
			String s = "str.foo.bar";
			String t = (String)deserialize(serialize(s));
			System.out.println(s.equals(t));
			
			Bar bar = new Bar();
			bar.setX("qaz");
			bar.setY("wsx");
			Bar rab = (Bar)deserialize(serialize(bar));
			System.out.println(
				bar.getX().equals(rab.getX()) && bar.getY().equals(rab.getY())
			);
		}
	}

	private static String serialize(Serializable s) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(s);
			bytes = baos.toByteArray();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(bytes);
	}

	private static Object deserialize(String s) {
		byte[] bytes = Base64.getDecoder().decode(s);
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return null;
	}

	private static class Foo implements Serializable {
		
		static final long serialVersionUID = 42L;
		
		private String x;
		public String getX() { return this.x; }
		public void setX(String x) { this.x = x; }
	}
	private static class Bar extends Foo {
		
		static final long serialVersionUID = 42L;
		
		private String y;
		public String getY() { return this.y; }
		public void setY(String y) { this.y = y; }
	}
}