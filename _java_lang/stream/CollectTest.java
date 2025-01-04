
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

    //@Test
    static void let_me_try() {
        List<Item> list = null;
        Set<Item> set = null;
        Map<String,String> k2v = null;
        Map<String,Item> k2i = null;
        Map<String,List<Item>> k2l = null;

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

        k2v = _stream()
                .collect(
                        // collector
                        toMap(
                                // keyMapper
                                item -> item.k(),
                                // valueMapper
                                item -> item.v()
                        )
                );
        System.out.printf("%s\n", k2v);
            //-> {Q=ux, B=ar, F=oo}

        //////////////////////////////

        k2i = _stream()
                .collect(
                        // collector
                        toMap(
                                // keyMapper
                                item -> item.k(),
                                // valueMapper
                                Function.identity()
                        )
                );
        System.out.printf("%s\n", k2i);
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

        k2l = _stream_list()
                .flatMap(Collection::stream)
                .collect(
                        // collector
                        groupingBy(
                                // classifier
                                Item::k
                        )
                );
        System.out.printf("%s\n", k2l);
            //-> {Q=[Qux, Quxa, Quxi], B=[Bar, Bard, Bark], F=[Foo, Food, Foot]}

        //////////////////////////////

        k2v = _stream_list()
                .flatMap(Collection::stream)
                .collect(
                        // collector
                        groupingBy(
                                // classifier
                                item -> item.k(),
                                // downstream
                                mapping(
                                        // mapper
                                        Item::toString,
                                        // downstream
                                        joining(","))
                        )
                );
        System.out.printf("%s\n", k2v);
            //-> {Q=Qux,Quxa,Quxi, B=Bar,Bard,Bark, F=Foo,Food,Foot}

        //////////////////////////////

        k2v = _stream_list()
                .flatMap(Collection::stream)
                .collect(
                        // collector
                        groupingBy(
                                // classifier
                                Item::k,
                                // downstream
                                mapping(
                                        // mapper
                                        Item::v,
                                        // downstream
                                        joining(",")
                                )
                        )
                );
        System.out.printf("%s\n", k2v);
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

        k2l = _stream_list()
                .collect(
                        // collector
                        flatMapping(
                                // mapper
                                Collection::stream,
                                // downstream
                                groupingBy(
                                        // classifier
                                        Item::k
                                )
                        )
                );
        System.out.printf("%s\n", k2l);
            //-> {Q=[Qux, Quxa, Quxi], B=[Bar, Bard, Bark], F=[Foo, Food, Foot]}

        //////////////////////////////

        k2v = _stream_list()
                .collect(
                        // collector
                        flatMapping(
                                // mapper
                                Collection::stream,
                                // downstream
                                groupingBy(
                                        // classifier
                                        Item::k,
                                        // downstream
                                        mapping(
                                                // mapper
                                                Item::toString,
                                                // downstream
                                                joining(",")
                                        )
                                )
                        )
                );
        System.out.printf("%s\n", k2v);
            //-> {Q=Qux,Quxa,Quxi, B=Bar,Bard,Bark, F=Foo,Food,Foot}

        //////////////////////////////

        k2v = _stream_list()
                .collect(
                        // collector
                        flatMapping(
                                // mapper
                                Collection::stream,
                                // downstream
                                groupingBy(
                                        // classifier
                                        Item::k,
                                        // downstream
                                        mapping(
                                                // mapper
                                                Item::v,
                                                // downstream
                                                joining(",")
                                        )
                                )
                        )
                );
        System.out.printf("%s\n", k2v);
            //-> {Q=ux,uxa,uxi, B=ar,ard,ark, F=oo,ood,oot}

        ////////////////////////////////////////////////////////////
        // reducing(identity, mapper, op)

        int maxLen = 0;
        maxLen= _stream()
                .map(Item::toString)
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
                .map(Item::toString)
                .map(String::length)
                .reduce(0, Integer::max);
        System.out.printf("%d\n", maxLen);
            //-> 3
    }

    private static Stream<Item> _stream() {
        return Stream.of(Item.of("Foo"), Item.of("Bar"), Item.of("Qux"));
    }

    private static Stream<List<Item>> _stream_list() {
        return Stream.of(
                List.of(Item.of("Foo"), Item.of("Bar"), Item.of("Qux")),
                List.of(Item.of("Food"), Item.of("Bard"), Item.of("Quxa")),
                List.of(Item.of("Foot"), Item.of("Bark"), Item.of("Quxi"))
        );
    }

    private static Stream<Item[]> _stream_array() {
        return Stream.of(
                new Item[] { Item.of("Foo"), Item.of("Bar"), Item.of("Qux") },
                new Item[] { Item.of("Food"), Item.of("Bard"), Item.of("Quxa") },
                new Item[] { Item.of("Foot"), Item.of("Bark"), Item.of("Quxi") }
        );
    }
    
    private static class Item {
        private final String k;
        private final String v;
        private Item(String s) {
            k = s.substring(0, 1);
            v = s.substring(1);
        }
        public static Item of(String s) {
            return new Item(s);
        }
        public String k() {
            return k;
        }
        public String v() {
            return v;
        }
        public String toString() {
            return String.format("%s%s", k, v);
        }
    }
}
