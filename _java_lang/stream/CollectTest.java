
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class CollectTest {

    public static void main(String[] args) {
        let_me_try();
    }

    static void let_me_try() {
        List<String> list = null;
        Set<String> set = null;
        Map<String,String> map = null;
        Map<String,List<String>> mapsl = null;

        ////////////////////////////////////////////////////////////
        // toList()

        list = _stream()
                .collect(
                        // collector
                        toList()
                );
        System.out.printf("%s\n", list);
            //-> [Foo, Bar, Qux]

        ////////////////////////////////////////////////////////////
        // toSet()

        set = _stream()
                .collect(
                        // collector
                        toSet()
                );
        System.out.printf("%s\n", set);
            //-> [Foo, Bar, Qux]

        ////////////////////////////////////////////////////////////
        // toMap()

        map = _stream()
                .collect(
                        // collector
                        toMap(
                                // keyMapper
                                s -> s.substring(0, 1),
                                // valueMapper
                                s -> s.substring(1)
                        )
                );
        System.out.printf("%s\n", map);
            //-> {Q=ux, B=ar, F=oo}

        //////////////////////////////

        map = _stream()
                .collect(
                        // collector
                        toMap(
                                // keyMapper
                                s -> s.substring(0, 1),
                                // valueMapper
                                Function.identity()
                        )
                );
        System.out.printf("%s\n", map);
            //-> {Q=Qux, B=Bar, F=Foo}

        ////////////////////////////////////////////////////////////
        // flatMap(list -> list.stream())

        list = _stream_list()
                .flatMap(
                        // mapper
                        Collection::stream
                )
                .collect(toList());
        System.out.printf("%s\n", list);
            //-> [Foo, Bar, Qux, Food, Bard, Quxa, Foot, Bark, Quxi]

        //////////////////////////////
        // flatMap(array -> Arrays.stream(array))

        list = _stream_array()
                .flatMap(Arrays::stream)
                .collect(toList());
        System.out.printf("%s\n", list);
            //-> [Foo, Bar, Qux, Food, Bard, Quxa, Foot, Bark, Quxi]

        ////////////////////////////////////////////////////////////
        // groupingBy(...)

        mapsl = _stream_list()
                .flatMap(Collection::stream)
                .collect(
                        // collector
                        groupingBy(
                                // classifier
                                s -> s.substring(0, 1)
                        )
                );
        System.out.printf("%s\n", mapsl);
            //-> {Q=[Qux, Quxa, Quxi], B=[Bar, Bard, Bark], F=[Foo, Food, Foot]}

        //////////////////////////////

        map = _stream_list()
                .flatMap(Collection::stream)
                .collect(
                        // collector
                        groupingBy(
                                // classifier
                                s -> s.substring(0, 1),
                                // downstream
                                joining(",")
                        )
                );
        System.out.printf("%s\n", map);
            //-> {Q=Qux,Quxa,Quxi, B=Bar,Bard,Bark, F=Foo,Food,Foot}

        //////////////////////////////

        map = _stream_list()
                .flatMap(Collection::stream)
                .collect(
                        // collector
                        groupingBy(
                                // classifier
                                s -> s.substring(0, 1),
                                // downstream
                                mapping(
                                        // mapper
                                        s -> s.substring(1),
                                        // downstream
                                        joining(",")
                                )
                        )
                );
        System.out.printf("%s\n", map);
            //-> {Q=ux,uxa,uxi, B=ar,ard,ark, F=oo,ood,oot}

        ////////////////////////////////////////////////////////////
        // flatMapping(...)

        set = _stream_list()
                .collect(
                        // collector
                        flatMapping(
                                // mapper
                                Collection::stream,
                                // downstream
                                toSet()
                        )
                );
        System.out.printf("%s\n", set);
            //-> [Bar, Quxi, Qux, Foo, Bark, Quxa, Bard, Food, Foot]

        //////////////////////////////

        mapsl = _stream_list()
                .collect(
                        // collector
                        flatMapping(
                                // mapper
                                Collection::stream,
                                // downstream
                                groupingBy(
                                        // classifier
                                        s -> s.substring(0, 1)
                                )
                        )
                );
        System.out.printf("%s\n", mapsl);
            //-> {Q=[Qux, Quxa, Quxi], B=[Bar, Bard, Bark], F=[Foo, Food, Foot]}

        //////////////////////////////

        map = _stream_list()
                .collect(
                        // collector
                        flatMapping(
                                // mapper
                                Collection::stream,
                                // downstream
                                groupingBy(
                                        // classifier
                                        s -> s.substring(0, 1),
                                        // downstream
                                        joining(",")
                                )
                        )
                );
        System.out.printf("%s\n", map);
            //-> {Q=Qux,Quxa,Quxi, B=Bar,Bard,Bark, F=Foo,Food,Foot}

        //////////////////////////////

        map = _stream_list()
                .collect(
                        // collector
                        flatMapping(
                                // mapper
                                Collection::stream,
                                // downstream
                                groupingBy(
                                        // classifier
                                        s -> s.substring(0, 1),
                                        // downstream
                                        mapping(
                                                // mapper
                                                s -> s.substring(1),
                                                // downstream
                                                joining(",")
                                        )
                                )
                        )
                );
        System.out.printf("%s\n", map);
            //-> {Q=ux,uxa,uxi, B=ar,ard,ark, F=oo,ood,oot}

        ////////////////////////////////////////////////////////////
        // reducing(identity, mapper, op)

        int maxLen = 0;
        maxLen= _stream()
                .collect(
                        reducing(
                                0,
                                String::length, // s -> s.length(),
                                Integer::max // (a, b) -> Integer.max(a, b)
                        )
                );
        System.out.printf("%d\n", maxLen);
            //-> 3

        //////////////////////////////
        // map(mapper)
        // reduce(identity, accumulator)

        maxLen = _stream()
                .map(String::length)
                .reduce(0, Integer::max);
        System.out.printf("%d\n", maxLen);
            //-> 3
    }

    private static Stream<String> _stream() {
        return Stream.of("Foo", "Bar", "Qux");
    }

    private static Stream<List<String>> _stream_list() {
        return Stream.of(
                List.of("Foo", "Bar", "Qux"),
                List.of("Food", "Bard", "Quxa"),
                List.of("Foot", "Bark", "Quxi")
        );
    }

    private static Stream<String[]> _stream_array() {
        return Stream.of(
                new String[] { "Foo", "Bar", "Qux" },
                new String[] { "Food", "Bard", "Quxa" },
                new String[] { "Foot", "Bark", "Quxi" }
        );
    }
}
