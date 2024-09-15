# Java Stream

## 케이스

```java
// Java 7

List<Dish> lowCaloricDishes = new ArrayList<>();
for (Dish dish: menu) {
  if (dish.getCalories() < 400) {
    lowCaloricDishes.add(dish);
  }
}
Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
  public int compare(Dish dish1, Dish dish2) {
    return Integer.compare(dish1.getCalories(), dish2.getCalories());
  }
});
List<String> lowCaloricDishesName = new ArrayList<>();
for (Dish dish: lowCaloricDishes) {
  lowCaloricDishesName.add(dish.getName());
}

// Java 8

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

List<String> lowCaloricDishesName =
    menu.stream()
        .filter(d -> d.getCalories() < 400)
        .sorted(comparing(Dish::getCalories))
        .map(Dish::getName)
        .collect(toList());
```

## 생성

- - `list.stream() : Stream<T>`
  - `Arrays.stream(T[]) : Stream<T>`

- - `Stream.of(T...) : Stream<T>`
  - `Stream.ofNullable(...)`
  - `Stream.empty() : Stream<T>`

- `Stream.iterate(seed : T, f : UnaryOperator<T>) : Stream<T>`
  - `Stream.iterate(0, n -> n + 2).limit(5)`
  - ```
    // Fibonacci
    Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] })
      .limit(10)
      .map(t -> t[0])
    ```
- `IntStream.iterate(seed : int, hasNext : IntPredicate, next : IntUnaryOperator) : IntStream`
  - `IntStream.iterate(0, n -> n < 10, n -> n + 2)`
- `IntStream.iterate(seed : int, f : IntUnaryOperator) : IntStream`
  - `IntStream.iterate(0, n -> n + 2).takeWhile(n -> n < 10)`

- `Stream.generate(Supplier<T>) : Stream<T>`
  - `Stream.generate(Math::random).limit(5)`
  - `IntStream.generate(() -> 1).limit(5)`
  - ```
    IntStream.generate(new IntSupplier() {
      @Override
      public int getAsInt() {
        return 2;
      }
    }).limit(5)
    ```

## 중간 연산

- `.filter(Predicate<T>) : Stream<T>`
  - `.filter(item -> item.getX() > 100)`
  - `.filter(Item::isX)`

- `.sorted(Comparator<T>) : Stream<T>`
  - `.sorted(comparing(Item::getX))`
- `.sorted()` -- natural order for `Comparable`

- `.map(mapper : Function<T,R>) : Stream<R>`
  - `.map(Item::getX)`
- `.flatMap(mapper : Function<T,Stream<R>>) : Stream<R>`
  - `.flatMap(Arrays::stream)`

- `.distinct() : Stream<T>`
- `.takeWhile(Predicate<T>) : Stream<T>`
- `.dropWhile(Predicate<T>) : Stream<T>`
- `.limit(size : long) : Stream<T>`
  - `.limit(3)` 
- `.skip(long) : Stream<T>`

## 최종 연산

- `.collect(Collector<T,A,R>) : R`
  - `.collect(toList())`
  - `.collect(groupingBy(Item::getX))`
- `.forEach(Consumer<T>) : void`
  - `.forEach(System.out::println)`

- `.anyMatch(Predicate<T>) : boolean`
- `.allMatch(Predicate<T>) : boolean`
- `.noneMatch(Predicate<T>) : boolean`

- `.findAny() : Optional<T>`
- `.findFirst() : Optional<T>`

- `.count() : long`

- `.max(Comparator<T>) : Optional<T>`
- `.min(Comparator<T>) : Optional<T>`
  - `.min(comparing(Item::getX))`

- `reduce(identity : T, accumulator : BinaryOperator<T>) : T`
  - `.reduce(0, (a, b) -> a + b) : int`
  - `.reduce(0, Integer::sum) : int`
  - `.reduce(0, (a, b) -> Integer.max(a, b)`
  - `.reduce(Integer::min)`
- `reduce(accumulator : BinaryOperator<T>) : Optional<T>`
  - `.reduce(Integer::max) : Optional<Integer>`

## primitive

- `.mapToInt(Item::getX) : IntStream`

- `Arrays.stream(int[]) : IntStream`

- `IntStream.range(0, 10) : IntStream`
- `IntStream.rangeClosed(0, 9) : IntStream`

- `IntStream`
  - `.boxed() : Stream<Integer>`
  
  - `.mapToObj(mapper : IntFunction<U>) : Stream<U>`
  
  - `.max() : OptionalInt`
  
  - `.sum() : int`
  
  - `.toArray() : int[]`

- `DoubleStream`
  - `.mapToInt(mapper : DoubleToIntFunction) : IntStream`

## collect

- `.collect(Collector<T,A,R>) : R`
  - `.collect(counting()) : long`
  - `.collect(maxBy(comparingInt(Item::getX))) : Optional<T>`
  
  - `.collect(summingInt(Item::getX)) : int`
  - `.collect(averagingInt(Item::getX)) : double`
  - `.collect(summarizingInt(Item::getX)) : IntSummaryStatistics`
  
  - `.collect(joining()) : String`
  - `.collect(joining(", ")) : String`
  
  - `.collect(reducing(0, Item::getX, (a, b) -> a + b)) : int`
  - `.collect(reducing(0, Item::getX, Integer::sum)) : int`

- ```
  .collect(summingInt(Item::getX))
  
  .collect(reducing(0, Item::getX, (i, j) -> i + j))
  .collect(reducing(0, Item::getX, Integer::sum))
  
  .map(Item::getX).reduce(Integer::sum).get()
  .mapToInt(Item::getX).sum()
  ```

- `Collectors`
  - `counting() : Collector<T,?,Long>`
  - `maxBy(Comparator<T>) : Collector<T,?,Optional<T>>`
  
  - `summingInt(mapper : ToIntFunction<T>) : Collector<T,?,Integer>`
  - `averagingInt(mapper : ToIntFunction<T>) : Collector<T,?,Double>`
  - `summarizingInt(mapper : ToIntFunction<T>) : Collector<T,?,IntSummaryStatistics>`
  
  - `joining() : Collector<CharSequence,?,String>`
  - `joining(delimiter : CharSequence) : Collector<CharSequence,?,String>`
  
  - `reducing(identity : U, mapper : Function<T,U>, op : BinaryOperator<U>) : Collector<T,?,U>`
  - `reducing(op : BinaryOperator<T>) : Collector<T,?,Optional<T>>`

- `Comparator<T>`
  - `comparing(keyExtractor : Function<T,U>) : Comparator<T>`
  - `comparingInt(keyExtractor : ToIntFunction<T>) : Comparator<T>`
    - `.comparingInt(Item::getX) : Comparator<T>`

### 그룹화

- `Collectors`
  - `groupingBy(classifier : Function<T,K>) : Collector<T, ?, Map<K,List<T>>>`
    - ```
      .collect(groupingBy(Item::getX))
      ```
  - `groupingBy(classifier : Function<T,K>, downstream : Collector<T,A,D>) : Collector<T, ?, Map<K,D>>`
    - ```
      .collect(groupingBy(Item::getX,
          filtering(Item::isX, toList())
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          mapping(Item::getY, toList())
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          mapping(Item::getY, toSet())
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          mapping(Item::getY, toCollection(HashSet::new))
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          flatMapping(item -> item->getY().stream(), toSet())
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          groupingBy(Item::getY)
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          counting()
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          maxBy(comparingInt(Item::getY))
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          collectingAndThen(
              maxBy(comparingInt(Item::getY)),
              Optional::get
          )
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          summingInt(Item::getY)
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          reducing((Item a, Item b) -> a.getY() > b.getY() ? a : b)
      ))
      ```
    - ```
      .collect(groupingBy(Item::getX,
          collectingAndThen(
              reducing((a, b) -> a.getY() > b.getY() ? a : b)
              Optional::get
          )
      ))
      ```

- `Collectors`
  - `filtering(Predicate<T>, downstream : Collector<T,A,R>) : Collector<T,?,R>`
  - `mapping(mapper : Function<T,U>, downstream : Collector<U,A,R>) : Collector<T,?,R>`
  - `flatMapping(mapper : Function<T,Stream<U>>, downstream : Collector<U,A,R>) : Collector<T,?,R>`
  - `groupingBy(classifier : Function<T,K>) : Collector<T, ?, Map<K,List<T>>>`
  - `counting() : Collector<T,?,Long>`
  - `maxBy(Comparator<T>) : Collector<T,?,Optional<T>>`
  - `collectingAndThen(downstream : Collector<T,A,R>, finisher : Function<R,RR>) : Collector<T,A,RR>`
  - `summingInt(mapper : ToIntFunction<T>) : Collector<T,?,Integer>`
  - `toCollection(collectionFactory : Supplier<C>) : Collector<T,?,C>`
  - `reducing(op : BinaryOperator<T>) : Collector<T,?,Optional<T>>`

### 분할

- `Collectors`
  - `partitioningBy(Predicate<T>) : Collector<T, ?, Map<Boolean,List<T>>>`
    - ```
      .collect(partitioningBy(Item::isX))
      ```
  - `partitioningBy(Predicate<T>, downstream : Collector<T,A,D>) : Collector<T, ?, Map<Boolean,D>>`
    - ```
      .collect(partitioningBy(Item::isX,
          groupingBy(Item::getY)
      ))
      ```
    - ```
      .collect(partitioningBy(Item::isX,
          collectingAndThen(
              maxBy(comparingInt(Item::getY)),
              Optional::get
          )
      ))
      ```
    - ```
      .collect(partitioningBy(Item::isX,
          partitioningBy(Item::isY)
      ))
      ```
    - ```
      .collect(partitioningBy(Item::isX,
          counting()
      ))
      ```

## Collector<T,A,R> 인터페이스

```
List<Dish> dishes = menu.stream().collect(
    ArrayList::new, // supplier : Supplier<List<T>>
    List::add, // accumulator : BiConsumer<List<T>, T>
    List::addAll // combiner : BinaryOperator<List<T>>
) // Collector<T, List<T>, List<T>>
```

## Optional<V>

- 팩토리
  - `Optional.empty() : Optional<T>`
  - `Optional.of(T) : Optional<T>`
  - `Optional.ofNullable(T) : Optional<T>`

- `.filter(Predicate<T>)`

- `.map(mapper : Function<T,U>) : Optional<U>`
  - `.map(String::valueOf)`
- `.flatMap(mapper : Function<T,Optional<U>>) : Optional<U>`

- `.isPresent() : boolean`
- `.isEmpty() : boolean`

- `.ifPresent(action : Consumer<T>) : void`
- `.ifPresentOrElse(action : Consumer<T>, emptyAction : Runnable)`

- `.get() : T` or `NoSuchElementException`
- `.orElse(T) : T`
- `.orElseGet(Supplier<T>) : T`

- `.orElseThrow() : T`
- `.orElseThrow(Supplier<Throwable>) : T` or `X extends Throwable`

- `.or(Supplier<Optional<T>>) : Optional<T>`

- `.stream() : Stream<T>`

- `OptionalInt`
  - `.getAsInt() : int`

:wq
