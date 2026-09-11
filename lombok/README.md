# Lombok 예제

이 디렉터리는 Lombok으로 접근자, 빌더, 생성자와 로거를 생성하는 예제예요.
JDK 21이 필요하며, 별도 Maven 설치 없이 Maven Wrapper로 빌드할 수 있어요.
아래 명령은 `lombok/` 디렉터리에서 실행해요.

## 프로젝트 구조

Java 소스는 default package를 사용하며 `src/main/java/` 바로 아래에 있어요.
`Demo`는 접근자, 빌더와 생성자 예제를 차례로 실행하며, `ALog`는 Lombok의
Java Util Logging, SLF4J와 Commons Logging 어노테이션을 보여 줘요.

## 빌드

`_build.sh`는 `clean package`를 실행하고 실행 시 필요한 로깅 의존성을
`target/dependency/`에 복사해요.

```sh
./_build.sh
```

Maven Wrapper를 직접 실행해도 돼요.

```sh
./mvnw clean package
```

Windows에서는 `mvnw.cmd clean package`를 사용해요.

## 실행

빌드한 다음 Maven을 거치지 않고 `Demo`를 실행해요.

```sh
./_run.sh Demo
```

`_run.sh`를 인수 없이 실행하면 사용법을 출력해요. Maven으로 실행하려면 기본
실행 클래스가 설정되어 있으므로 다음 명령을 사용해요.

```sh
./mvnw exec:java
```

Lombok은 컴파일 시에만 필요한 `provided` 의존성이며, Maven Compiler Plugin의
annotation processor로 명시되어 있어요.
