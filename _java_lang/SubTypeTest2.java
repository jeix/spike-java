
import java.util.ArrayList;
import java.util.List;

public class SubTypeTest2 {

	public static void main(String[] args) throws Exception {

		demo_1();
		demo_2();

		demo_3();
		demo_4();

		demo_5();
	}

	////////////////////////////////////////

	private static abstract class FooBar {
		protected String x;
		public String getX() { return this.x; }
		public void setX(String x) { this.x = x; }
		public FooBar(String x) { this.x = x; }
	}

	private static <E extends FooBar> E build(Class<? extends FooBar> klass, String x) throws Exception {
		E e = (E) klass.getConstructor(String.class).newInstance(x);
		return e;
	}
	private static <E extends FooBar> List<E> build(Class<? extends FooBar> klass, String x, int size) throws Exception {
		List<E> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			E e = build(klass, x+(i+1));
			list.add(e);
		}
		return list;
	}

	////////////////////////////////////////

	private static class Tik1 extends FooBar {
		private List<TikTik1> exts;
		public List<TikTik1> getExts() {
			return this.exts;
		}
		public void setExts(List<TikTik1> exts) {
			this.exts = exts;
		}
		public String stringify() {
			return "<Tik1 "+this.x+"> with " + this.exts.get(0).stringify();
		}
		public Tik1(String x) { super(x); }
	}
	private static class TikTik1 extends FooBar {
		public String stringify() { return "<TikTik1 "+this.x+">"; }
		public TikTik1(String x) { super(x); }
	}

	private static void demo_1() throws Exception {
		Tik1 tik = build(Tik1.class, "tik");
		List<TikTik1> tiktiks = build(TikTik1.class, "tiktik", 1);
		tik.setExts(tiktiks);
		for (TikTik1 ext : tik.getExts()) {
			System.out.println(ext.stringify());
				//-> <TikTik1 tiktik1>
		}
	}

	private static class Tok1 extends Tik1 {
		//private List<TokTok1> exts;
		//public List<TokTok1> getExts() { return this.exts; }
		//public void setExts(List<TokTok1> exts) { this.exts = exts; }
		public List<TokTok1> getExtExts() {
			return super.getExts().stream().map(ext -> (TokTok1)ext).toList();
		}
		public void setExtExts(List<TokTok1> exts) {
			List<TikTik1> _exts = new ArrayList<>();
			exts.stream().forEach(ext -> _exts.add(ext));
			super.setExts(_exts);
		}
		public String stringify() {
			return "<Tok1 "+this.x+"> with " + super.getExts().get(0).stringify();
		}
		public Tok1(String x) { super(x); }
	}
	private static class TokTok1 extends TikTik1 {
		public String stringify() { return "<TokTok1 "+this.x+">"; }
		public TokTok1(String x) { super(x); }
	}

	private static void demo_2() throws Exception {
		Tok1 tok = build(Tok1.class, "tok");
		List<TokTok1> toktoks = build(TokTok1.class, "toktok", 1);
		tok.setExtExts(toktoks);
		for (TokTok1 ext : tok.getExtExts()) {
			System.out.println(ext.stringify());
				//-> <TokTok1 toktok1>
		}
	}

	////////////////////////////////////////

	private static class Tik2 extends FooBar {
		private List<? extends TikTik2> exts;
		public List<? extends TikTik2> getExts() {
			return this.exts;
		}
		public void setExts(List<? extends TikTik2> exts) {
			this.exts = exts;
		}
		public String stringify() {
			return "<Tik2 "+this.x+"> with " + this.exts.get(0).stringify();
		}
		public Tik2(String x) { super(x); }
	}
	private static class TikTik2 extends FooBar {
		public String stringify() { return "<TikTik2 "+this.x+">"; }
		public TikTik2(String x) { super(x); }
	}

	private static void demo_3() throws Exception {
		Tik2 tik = build(Tik2.class, "tik");
		List<TikTik2> tiktiks = build(TikTik2.class, "tiktik", 1);
		tik.setExts(tiktiks);
		for (TikTik2 ext : tik.getExts()) {
			System.out.println(ext.stringify());
				//-> <TikTik2 tiktik1>
		}
	}

	private static class Tok2 extends Tik2 {
		//private List<TokTok2> exts;
		//public List<TokTok2> getExts() { return this.exts; }
		//public void setExts(List<TokTok> exts) { this.exts = exts; }
		public List<TokTok2> getExts() {
			return super.getExts().stream().map(ext -> (TokTok2)ext).toList();
		}
		public String stringify() {
			return "<Tok2 "+this.x+"> with " + super.getExts().get(0).stringify();
		}
		public Tok2(String x) { super(x); }
	}
	private static class TokTok2 extends TikTik2 {
		public String stringify() { return "<TokTok2 "+this.x+">"; }
		public TokTok2(String x) { super(x); }
	}

	private static void demo_4() throws Exception {
		Tok2 tok = build(Tok2.class, "tok");
		List<TokTok2> toktoks = build(TokTok2.class, "toktok", 1);
		tok.setExts(toktoks);
		for (TokTok2 ext : tok.getExts()) {
			System.out.println(ext.stringify());
				//-> <TokTok2 toktok1>
		}
	}

	////////////////////////////////////////

	private static void swap(List<?> list, int i, int j) {
		//list.set(i, list.set(j, list.get(i))); // error
		swapHelper(list, i, j);
	}
	private static <E> void swapHelper(List<E> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}

	private static void demo_5() throws Exception {
		List<TikTik1> tiktiks = build(TokTok1.class, "toktok", 2);
		System.out.println(tiktiks.get(0).stringify()+" "+tiktiks.get(1).stringify());
			//-> <TokTok1 toktok1> <TokTok1 toktok2>
		swap(tiktiks, 0, 1);
		System.out.println(tiktiks.get(0).stringify()+" "+tiktiks.get(1).stringify());
			//-> <TokTok1 toktok2> <TokTok1 toktok1>
	}
}