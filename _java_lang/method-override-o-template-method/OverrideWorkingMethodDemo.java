
public class OverrideWorkingMethodDemo {

	abstract class Foo {

		public final void public_template_method() {
			System.out.println("Foo#public_template_method()");
			System.out.println(protected_method());
			System.out.println(private_method());
		}

		protected abstract String protected_method();

		private String private_method() {
			return "Foo#private_method()";
		}
	}

	class NaturalFoo extends Foo {

		protected String protected_method() {
			System.out.println(private_method());
			return "NaturalFoo#protected_method()";
		}

		private String private_method() {
			return "NaturalFoo#private_method()";
		}
	}

	class RealFoo extends Foo {

		protected String protected_method() {
			System.out.println(private_method());
			return "RealFoo#protected_method()";
		}

		private String private_method() {
			return "RealFoo#private_method()";
		}
	}

	class ComplexFoo extends Foo {

		protected String protected_method() {
			System.out.println(private_method());
			return "ComplexFoo#protected_method()";
		}

		private String private_method() {
			return "ComplexFoo#private_method()";
		}
	}

	private void test_Something() {
		System.out.println("========================");
		Foo natural = new NaturalFoo();
		natural.public_template_method();
			// -> Foo#public_template_method()
			// -> NaturalFoo#private_method()
			// -> NaturalFoo#protected_method()
			// -> Foo#private_method()
		System.out.println("========================");
		Foo real = new RealFoo();
		real.public_template_method();
			// -> Foo#public_template_method()
			// -> RealFoo#private_method()
			// -> RealFoo#protected_method()
			// -> Foo#private_method()
		System.out.println("========================");
		Foo complex = new ComplexFoo();
		complex.public_template_method();
			// -> Foo#public_template_method()
			// -> ComplexFoo#private_method()
			// -> ComplexFoo#protected_method()
			// -> Foo#private_method()
		System.out.println("========================");
	}

	private void test_nothing() {
		System.out.println(":wq");
	}

	public void test() {
		test_Something();
		test_nothing();
	}

	public static void main(String[] args) {
		OverrideWorkingMethodDemo worker = new OverrideWorkingMethodDemo();
		worker.test();
	}
}
