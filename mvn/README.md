# Maven Java 예제

이 디렉터리는 별도 Maven 설치 없이 Java 코드를 빌드하고 실행하는 예제예요.
JDK 21이 필요하며, Maven Wrapper는 처음 실행할 때 지정된 Maven 배포본을
내려받아요. 모든 명령은 `mvn/` 디렉터리에서 실행해요.

## 빌드

다음 스크립트는 `clean package`를 실행해 소스를 컴파일하고 JAR을 만들어요.
활성화된 런타임 의존성이 있으면 `target/dependency/`에도 복사해요.

```sh
./_build.sh
```

Maven 명령을 직접 사용해도 돼요.

```sh
./mvnw clean package
```

Windows에서는 `mvnw.cmd clean package`를 사용해요. 기존 `mvn.cmd`는 완전한
Maven 설치 디렉터리를 전제로 하는 시작 스크립트이므로 이 프로젝트의 Wrapper가
아니에요.

## Java로 클래스 실행

`_run.sh`의 첫 번째 인수로 `main` 메서드가 있는 클래스 이름을 전달해요.
나머지 인수는 해당 클래스에 그대로 전달돼요.

```sh
./_run.sh Try
./_run.sh GroupingTest
./_run.sh LocalDateTimeTest
./_run.sh Try argument1 argument2
```

인수 없이 실행하면 사용법을 출력해요. 빌드 결과인 `target/classes`와
`target/dependency/*`를 classpath로 사용하므로 먼저 `./_build.sh`를 실행해야 해요.

## Maven으로 Try 실행

`pom.xml`의 Exec Maven Plugin 기본 실행 클래스는 `Try`예요.

```sh
./mvnw exec:java
```

다른 클래스를 Maven으로 실행하려면 기본값을 덮어쓸 수 있어요.

```sh
./mvnw exec:java -Dexec.mainClass=GroupingTest
```

일반 실행에는 Maven을 거치지 않는 `./_run.sh`를 사용해요.

## 선택적 의존성과 플러그인

`pom.xml`에는 Spring Boot와 QueryDSL 예제가 주석으로 남아 있어요. 실제로
필요할 때 관련 주석을 해제하고 설정을 검토해요. 현재 예제 소스에는 외부
의존성이 필요하지 않아요.
