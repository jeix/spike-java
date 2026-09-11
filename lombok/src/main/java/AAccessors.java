import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor(staticName = "init")
public class AAccessors {
    @Setter
    @Getter
    @Accessors(chain = true, fluent = true)
    private String name;
    @Setter
    @Getter
    @Accessors(chain = true, fluent = true)
    private int age;
}
