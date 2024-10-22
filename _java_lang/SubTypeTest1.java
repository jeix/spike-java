
import java.util.ArrayList;
import java.util.List;

public class SubTypeTest1 {

	public static void main(String[] args) throws Exception {
		
		demo_1();
		// {
		// 	Foo foo = build(Foo.class);
		// 	Bar bar = build(Bar.class);
		// 	Baz baz = build(Baz.class);
		// 	Qux qux = build(Qux.class);
		// 	bar.setQux(qux);
		// 	foo.setBar(bar);
		// 	foo.setBaz(baz);
		// 	System.out.println(foo.stringify());
		// }

		demo_2();
		// {
		// 	Foo2 foo = build2(Foo2.class, "foo");
		// 	List<Bar2> bars = build2(Bar2.class, "bar", 1);
		// 	List<Baz2> bazs = build2(Baz2.class, "baz", 1);
		// 	List<Qux2> quxs = build2(Qux2.class, "qux", 1);
		// 	bars.get(0).setQuxs(quxs);
		// 	foo.setBars(bars);
		// 	foo.setBazs(bazs);
		// 	System.out.println(foo.stringify());
		// }

		demo_3();
		// {
		// 	Foo3 foo = build3(Foo3.class, "foo");
		// 	for (Class<? extends FooBar3> klass : foo.klasses()) {
		// 		foo.setChild(klass, build3(klass, klass.getSimpleName().toLowerCase(), 1));
		// 	}
		// 	List<Bar3> bars = foo.getBars();
		// 	for (Class<? extends FooBar3> klass : bars.get(0).klasses()) {
		// 		bars.get(0).setChild(klass, build3(klass, klass.getSimpleName().toLowerCase(), 1));
		// 	}
		// 	System.out.println(foo.stringify());
		// }
	}

	////////////////////////////////////////

	private static abstract class FooBar {
		private String x;
		public String getX() { return this.x; }
		public void setX(String x) { this.x = x; }
		//public FooBar() {}
	}

	private static <E extends FooBar> E build(Class<? extends FooBar> klass) throws Exception {
		E e = (E) klass.getConstructor().newInstance();
		return e;
	}

	private static class Foo1 extends FooBar {
		private Bar1 bar;
		public Bar1 getBar() { return this.bar; }
		public void setBar(Bar1 bar) { this.bar = bar; }
		private Baz1 baz;
		public Baz1 getBaz() { return this.baz; }
		public void setBaz(Baz1 baz) { this.baz = baz; }
		public String stringify() {
			return "<Foo1> with " + bar.stringify() + " and " + baz.stringify();
		}
		public Foo1() {}
	}
	private static class Bar1 extends FooBar {
		private Qux1 qux;
		public Qux1 getQux() { return this.qux; }
		public void setQux(Qux1 qux) { this.qux = qux; }
		public String stringify() {
			return "<Bar1> with " + qux.stringify();
		}
		public Bar1() {}
	}
	private static class Baz1 extends FooBar {
		public String stringify() { return "<Baz1>"; }
		public Baz1() {}
	}
	private static class Qux1 extends FooBar {
		public String stringify() { return "<Qux1>"; }
		public Qux1() {}
	}

	private static void demo_1() throws Exception {
		Foo1 foo = build(Foo1.class);
		Bar1 bar = build(Bar1.class);
		Baz1 baz = build(Baz1.class);
		Qux1 qux = build(Qux1.class);
		bar.setQux(qux);
		foo.setBar(bar);
		foo.setBaz(baz);
		System.out.println(foo.stringify());
			//-> <Foo1> with <Bar1> with <Qux1> and <Baz1>
	}

	////////////////////////////////////////

	private static abstract class FooBar2 {
		protected String x;
		public String getX() { return this.x; }
		public void setX(String x) { this.x = x; }
		public FooBar2(String x) { this.x = x; }
	}

	private static <E extends FooBar2> E build2(Class<? extends FooBar2> klass, String x) throws Exception {
		E e = (E) klass.getConstructor(String.class).newInstance(x);
		return e;
	}
	private static <E extends FooBar2> List<E> build2(Class<? extends FooBar2> klass, String x, int size) throws Exception {
		List<E> list = new ArrayList<E>();
		for (int i = 0; i < size; i++) {
			E e = build2(klass, x+(i+1));
			list.add(e);
		}
		return list;
	}

	private static class Foo2 extends FooBar2 {
		private List<Bar2> bars;
		public List<Bar2> getBars() { return this.bars; }
		public void setBars(List<Bar2> bars) { this.bars = bars; }
		private List<Baz2> bazs;
		public List<Baz2> getBazs() { return this.bazs; }
		public void setBazs(List<Baz2> bazs) { this.bazs = bazs; }
		public String stringify() {
			return "<Foo2 "+this.x+"> with " + bars.get(0).stringify() + " and " + bazs.get(0).stringify();
		}
		public Foo2(String x) { super(x); }
	}
	private static class Bar2 extends FooBar2 {
		private List<Qux2> quxs;
		public List<Qux2> getQuxs() { return this.quxs; }
		public void setQuxs(List<Qux2> quxs) { this.quxs = quxs; }
		public String stringify() {
			return "<Bar2 "+this.x+"> with " + quxs.get(0).stringify();
		}
		public Bar2(String x) { super(x); }
	}
	private static class Baz2 extends FooBar2 {
		public String stringify() { return "<Baz2 "+this.x+">"; }
		public Baz2(String x) { super(x); }
	}
	private static class Qux2 extends FooBar2 {
		public String stringify() { return "<Qux2 "+this.x+">"; }
		public Qux2(String x) { super(x); }
	}

	private static void demo_2() throws Exception {
		Foo2 foo = build2(Foo2.class, "foo");
		List<Bar2> bars = build2(Bar2.class, "bar", 1);
		List<Baz2> bazs = build2(Baz2.class, "baz", 1);
		List<Qux2> quxs = build2(Qux2.class, "qux", 1);
		bars.get(0).setQuxs(quxs);
		foo.setBars(bars);
		foo.setBazs(bazs);
		System.out.println(foo.stringify());
			//-> <Foo2 foo> with <Bar2 bar1> with <Qux2 qux1> and <Baz2 baz1>
	}

	////////////////////////////////////////

	private static abstract class FooBar3 {
		protected String x;
		public String getX() { return this.x; }
		public void setX(String x) { this.x = x; }
		public FooBar3(String x) { this.x = x; }
	}

	private static <E extends FooBar3> E build3(Class<? extends FooBar3> klass, String x) throws Exception {
		E e = (E) klass.getConstructor(String.class).newInstance(x);
		return e;
	}
	private static <E extends FooBar3> List<E> build3(Class<? extends FooBar3> klass, String x, int size) throws Exception {
		List<E> list = new ArrayList<E>();
		for (int i = 0; i < size; i++) {
			E e = build3(klass, x+(i+1));
			list.add(e);
		}
		return list;
	}

	private static class Foo3 extends FooBar3 {
		private List<Bar3> bars;
		public List<Bar3> getBars() { return this.bars; }
		public void setBars(List<Bar3> bars) { this.bars = bars; }
		private List<Baz3> bazs;
		public List<Baz3> getBazs() { return this.bazs; }
		public void setBazs(List<Baz3> bazs) { this.bazs = bazs; }
		public String stringify() {
			return "<Foo3 "+this.x+"> with " + bars.get(0).stringify() + " and " + bazs.get(0).stringify();
		}
		public Foo3(String x) { super(x); }
		public List<Class<? extends FooBar3>> klasses() {
			List<Class<? extends FooBar3>> list = new ArrayList<Class<? extends FooBar3>>();
			list.add(Bar3.class);
			list.add(Baz3.class);
			return list;
		}
		public <E extends FooBar3> void setChild(Class<? extends FooBar3> klass, List<E> list) {
			if ("Bar3".equals(klass.getSimpleName())) {
				setBars((List<Bar3>)list);
			} else if ("Baz3".equals(klass.getSimpleName())) {
				setBazs((List<Baz3>)list);
			}
		}
	}
	private static class Bar3 extends FooBar3 {
		private List<Qux3> quxs;
		public List<Qux3> getQuxs() { return this.quxs; }
		public void setQuxs(List<Qux3> quxs) { this.quxs = quxs; }
		public String stringify() {
			return "<Bar3 "+this.x+"> with " + quxs.get(0).stringify();
		}
		public Bar3(String x) { super(x); }
		public List<Class<? extends FooBar3>> klasses() {
			List<Class<? extends FooBar3>> list = new ArrayList<Class<? extends FooBar3>>();
			list.add(Qux3.class);
			return list;
		}
		public <E extends FooBar3> void setChild(Class<? extends FooBar3> klass, List<E> list) {
			if ("Qux3".equals(klass.getSimpleName())) {
				setQuxs((List<Qux3>)list);
			}
		}
	}
	private static class Baz3 extends FooBar3 {
		public String stringify() { return "<Baz3 "+this.x+">"; }
		public Baz3(String x) { super(x); }
		public List<Class<? extends FooBar3>> klasses() {
			List<Class<? extends FooBar3>> list = new ArrayList<Class<? extends FooBar3>>();
			return list;
		}
	}
	private static class Qux3 extends FooBar3 {
		public String stringify() { return "<Qux3 "+this.x+">"; }
		public Qux3(String x) { super(x); }
		public List<Class<? extends FooBar3>> klasses() {
			List<Class<? extends FooBar3>> list = new ArrayList<Class<? extends FooBar3>>();
			return list;
		}
	}

	private static void demo_3() throws Exception {
		Foo3 foo = build3(Foo3.class, "foo");
		for (Class<? extends FooBar3> klass : foo.klasses()) {
			foo.setChild(klass, build3(klass, klass.getSimpleName().toLowerCase(), 1));
		}
		List<Bar3> bars = foo.getBars();
		for (Class<? extends FooBar3> klass : bars.get(0).klasses()) {
			bars.get(0).setChild(klass, build3(klass, klass.getSimpleName().toLowerCase(), 1));
		}
		System.out.println(foo.stringify());
			//-> <Foo3 foo> with <Bar3 bar31> with <Qux3 qux31> and <Baz3 baz31>
	}
}