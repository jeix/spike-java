# Gson JSON 예제

이 디렉터리는 Gson을 사용한 JSON 직렬화와 역직렬화 예제예요. JDK 21이
필요하며, 별도 Maven 설치 없이 Maven Wrapper로 빌드할 수 있어요. Wrapper는
처음 실행할 때 지정된 Maven 배포본을 내려받아요. 아래 명령은 `json/`
디렉터리에서 실행해요.

## 빌드

`_build.sh`는 `clean package`를 실행하고 Gson을 `target/dependency/`에 복사해요.

```sh
./_build.sh
```

Maven Wrapper를 직접 실행해도 돼요.

```sh
./mvnw clean package
```

Windows에서는 `mvnw.cmd clean package`를 사용해요.

## Java로 실행

먼저 빌드한 다음 `_run.sh`에 실행할 클래스 이름을 전달해요. 스크립트는
`target/classes`와 `target/dependency/*`를 classpath로 사용하며, 추가 인수는
Java 프로그램에 그대로 전달해요.

```sh
./_run.sh org.simple.jsontest.Try
./_run.sh org.simple.jsontest.Try2
```

인수 없이 실행하면 사용법을 출력해요.

## Maven으로 실행

Exec Maven Plugin의 기본 실행 클래스는 `org.simple.jsontest.Try`예요.

```sh
./mvnw exec:java
./mvnw exec:java -Dexec.mainClass=org.simple.jsontest.Try2
```

일반 실행에는 Maven을 거치지 않는 `_run.sh`를 사용해요.

## 기존 직접 컴파일 예제

`gson-2.8.6.jar`와 `x-how-to.txt`는 `javac`와 `java`를 직접 사용하던 예제로
남겨 두었어요. Maven 빌드는 `pom.xml`에 선언된 Gson 2.14.0을 사용하므로 해당
JAR 파일에 의존하지 않아요.
