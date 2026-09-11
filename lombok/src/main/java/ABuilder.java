import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
public class ABuilder {
    @Builder.Default private long created = System.currentTimeMillis();
    @Setter
    @Getter
    @Accessors(chain = true, fluent = true)
    private String name;
    @Setter
    @Getter
    @Accessors(chain = true, fluent = true)
    private int age;
    @Singular
    private List<String> books;
}
