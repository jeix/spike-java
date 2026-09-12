# 템플릿 메서드와 메서드 재정의

`Foo.public_template_method()`는 처리 순서를 정의하는 템플릿 메서드다. `final`로
선언되어 하위 클래스가 이 순서 자체를 바꿀 수 없으며, 처리 중 일부를
`protected abstract String protected_method()`에 위임한다.

`protected_method()`는 하위 클래스에서 재정의할 수 있으므로 실제 객체가
`NaturalFoo`, `RealFoo`, `ComplexFoo` 중 무엇인지에 따라 해당 구현이 동적으로
호출된다. 템플릿 메서드의 확장 지점이 반드시 `protected`여야 하는 것은 아니다.
상속 관계와 패키지 구성에 따라 package-private 또는 `public`도 사용할 수 있지만,
외부에는 감추면서 하위 클래스에는 재정의를 허용하려면 `protected`가 적합하다.

반면 `private_method()`는 `private`이므로 하위 클래스에 상속되거나 재정의되지
않는다. 각 하위 클래스에 같은 이름과 시그니처의 메서드가 있어도 서로 별개의
메서드다. 따라서 호출 결과는 호출문이 작성된 클래스에 따라 달라진다.

| 호출 위치 | 호출되는 메서드 |
| --- | --- |
| `Foo.public_template_method()`의 `protected_method()` | 실제 객체에 해당하는 하위 클래스의 재정의 메서드 |
| 하위 클래스의 `protected_method()` 안에 있는 `private_method()` | 해당 하위 클래스가 선언한 `private_method()` |
| `Foo.public_template_method()`의 `private_method()` | 항상 `Foo.private_method()` |

실행 결과에서 각 하위 클래스의 `private_method()`와 `protected_method()`가 먼저
출력된 뒤 마지막에 항상 `Foo#private_method()`가 출력되는 이유가 여기에 있다.
즉, 템플릿 메서드에서 하위 클래스별 동작을 호출하려면 그 동작을 재정의 가능한
인스턴스 메서드로 선언해야 하며, 이 예제는 `protected abstract` 메서드를 확장
지점으로 사용한다.

## 컴파일 및 실행

```sh
javac -d out *.java
```

```sh
java -cp out OverrideWorkingMethodDemo
```
