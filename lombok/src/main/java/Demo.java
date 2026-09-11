import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) {
        demoAccessors();
        demoBuilder();
        demoConstructor();
    }

    public static void demoAccessors() {
        AAccessors x = AAccessors.init().age(42).name("고구마");
        System.out.format("%d%s\n", x.age(), x.name());
    }

    public static void demoBuilder() {
        ABuilder x = ABuilder.builder()
                //.created(System.currentTimeMillis())
                .name("고구마")
                .age(42)
                .books(new ArrayList<>() {{
                    add("고사리");
                    add("고라니");
                }})
                .clearBooks()
                .book("고도리")
                .build();
        System.out.format("%s\n", x);
    }

    public static void demoConstructor() {
        AConstructor<String> x = AConstructor.of("이러쿵저렁쿵");
        System.out.format("%s\n", x);
        // 'AConstructor(@lombok. NonNull T)' has private access
        //AConstructor<String> y = new AConstructor<>("궁시렁궁시렁");
        AConstructor<String> y = new AConstructor<>(123, 45, "궁시렁궁시렁");
        System.out.format("%s\n", y);
        AConstructor.NoArgsExample z = new AConstructor.NoArgsExample();
        System.out.format("%s\n", z);
    }
}
