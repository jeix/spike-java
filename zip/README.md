# ZIP 예제

다음 명령은 `zip/` 디렉터리에서 실행해요. 예제로 사용할 `foo/` 디렉터리를
만들고, 두 구현으로 압축과 해제를 수행한 다음 생성된 파일을 삭제해요.
컴파일과 실행에는 JDK 21이 필요해요.

## JDK로 직접 실행

```sh
javac -d out src/main/java/MkDir.java
javac -d out src/main/java/Zipper.java
javac -d out -cp out:commons-compress-1.20.jar src/main/java/ZipperCC.java

java -cp out MkDir m foo

touch foo/bar.txt

java -cp out Zipper a test1.zip foo
java -cp out Zipper e test1.zip

java -cp out:commons-compress-1.20.jar ZipperCC a test2.zip foo
java -cp out:commons-compress-1.20.jar ZipperCC e test2.zip

java -cp out MkDir r foo

rm -fr test1 test2 test1.zip test2.zip
```

## Maven Wrapper로 실행

Wrapper를 처음 실행하면 프로젝트에 지정된 Maven 버전을 내려받아요.

```sh
./mvnw clean compile

./mvnw exec:java -Dexec.mainClass=MkDir -Dexec.args="m foo"

touch foo/bar.txt

./mvnw exec:java -Dexec.mainClass=Zipper -Dexec.args="a test1.zip foo"
./mvnw exec:java -Dexec.mainClass=Zipper -Dexec.args="e test1.zip"

./mvnw exec:java -Dexec.mainClass=ZipperCC -Dexec.args="a test2.zip foo"
./mvnw exec:java -Dexec.mainClass=ZipperCC -Dexec.args="e test2.zip"

./mvnw exec:java -Dexec.mainClass=MkDir -Dexec.args="r foo"

rm -fr test1 test2 test1.zip test2.zip
```
