
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class FlatteningTest {

  public static void main(String[] args) {
    
    {
      List<String> words = Arrays.asList("Hello", "World");
      List<String[]> uniqueCharacters =
              words.stream()
                   .map(word -> word.split(""))
                   .distinct()
                   .collect(toList());
      System.out.println(uniqueCharacters);
        //-> [[Ljava.lang.String;@27c170f0, [Ljava.lang.String;@5451c3a8]
    }
    
    {
      List<String> words = Arrays.asList("Hello", "World");
      List<List<String>> uniqueCharacters =
              words.stream()
                   .map(word -> word.split(""))
                   .map(chars -> Arrays.asList(chars))
                   .distinct()
                   .collect(toList());
      System.out.println(uniqueCharacters);
        //-> [[H, e, l, l, o], [W, o, r, l, d]]
    }

    {
      List<String> words = Arrays.asList("Hello", "World");
      List<Stream<String>> uniqueCharacters =
              words.stream()
                   .map(word -> word.split(""))
                   .map(Arrays::stream)
                   .distinct()
                   .collect(toList());
      System.out.println(uniqueCharacters);
        //-> [java.util.stream.ReferencePipeline$Head@3fee733d, java.util.stream.ReferencePipeline$Head@5acf9800]
    }

    {
      List<String> words = Arrays.asList("Hello", "World");
      List<List<String>> uniqueCharacters =
              words.stream()
                   .map(word -> word.split(""))
                   .map(Arrays::stream)
                   .map(stream -> stream.collect(toList()))
                   .distinct()
                   .collect(toList());
      System.out.println(uniqueCharacters);
        //-> [[H, e, l, l, o], [W, o, r, l, d]]
    }

    {
      List<String> words = Arrays.asList("Hello", "World");
      List<String> uniqueCharacters =
              words.stream()
                   .map(word -> word.split(""))
                   .flatMap(Arrays::stream)
                   .distinct()
                   .collect(toList());
      System.out.println(uniqueCharacters);
        //-> [H, e, l, o, W, r, d]
    }

    {
      List<String> empty = new ArrayList<>();
      String csv = empty.stream().collect(joining(","));
      System.out.println("'"+csv+"'");
        //-> ''
    }
  }
}