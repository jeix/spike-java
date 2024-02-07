public class CloneTest {

	public static void main(String[] args) {

		{
			Bar bar = new Bar();
			bar.setX("qaz");
			bar.setY("wsx");
			try {
				Bar rab = (Bar) bar.clone();
				System.out.println(
					bar.getX().equals(rab.getX()) && bar.getY().equals(rab.getY())
				);
			} catch (CloneNotSupportedException cnse) {
				cnse.printStackTrace();
			}
		}
	}

	private static class Foo {
		
		private String x;
		public String getX() { return this.x; }
		public void setX(String x) { this.x = x; }
	}

	private static class Bar extends Foo implements Cloneable {
		
		private String y;
		public String getY() { return this.y; }
		public void setY(String y) { this.y = y; }

		@Override
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}
}