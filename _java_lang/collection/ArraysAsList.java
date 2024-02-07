
import java.util.Arrays;
import java.util.List;

public class ArraysAsList {

  public static void main(String[] args) {

    List<String> list = Arrays.asList("foo", "bar", "baz");

    {
      for (String item : list) {
        System.out.println(item);
      }
    }

    {
      list.stream().forEach((item) -> {
        System.out.println(item);
      });
    }

    {
      System.out.println(list.contains("bar"));
    }

    {
      list.set(0, list.get(0).toUpperCase());
      System.out.println(list);
    }

    {
      try {
        list.add("goose");
      } catch (UnsupportedOperationException uoe) {
        uoe.printStackTrace();
      }
    }

    {
      try {
        list.remove(0);
      } catch (UnsupportedOperationException uoe) {
        uoe.printStackTrace();
      }
    }
  }
}
