# Repository Guidelines

## Project Structure & Module Organization

This repository is a collection of small Java 8 experiments rather than one application. Standalone examples live under `_java_lang/` and `_java_util/`; keep related notes beside them, such as `_java_lang/stream/Java-Stream.md`. The `mvn/`, `json/`, and `zip/` directories are independent Maven projects with their own `pom.xml` and `src/main/java` trees. `json/` demonstrates Gson, while `zip/` contains ZIP and filesystem examples. Do not treat the repository root as a Maven reactor. Generated `target/`, `.class`, IDE, and build files must remain untracked.

## Build, Test, and Development Commands

Run Maven commands against the module being changed:

- `mvn -f mvn/pom.xml clean compile` compiles the general Java examples.
- `mvn -f json/pom.xml clean package` resolves Gson and packages the JSON examples.
- `mvn -f zip/pom.xml clean package` builds the archive utilities.
- `mvn -f mvn/pom.xml exec:java` runs the configured `Try` entry point.
- `mvn -f json/pom.xml exec:java -Dexec.mainClass=org.simple.jsontest.Try` runs a selected JSON example.

See `json/x-how-to.txt` and `zip/x-how-to.txt` for direct `javac` and `java` alternatives.

## Coding Style & Naming Conventions

Target Java 8, as declared by each Maven module. Use four-space indentation for new Java code, one public top-level class per file, `PascalCase` class names, `camelCase` methods and variables, and `UPPER_SNAKE_CASE` constants. Keep package paths aligned with declarations (for example, `org/simple/jsontest`). No formatter or linter is configured, so preserve surrounding style and keep imports explicit and organized.

## Testing Guidelines

No automated test framework or coverage threshold is currently configured. Existing `*Test.java` files are executable experiments, not JUnit tests. Validate changes by compiling every affected module and running the relevant `main` method. If adding repeatable tests, place them under `src/test/java`, name them `*Test.java`, add the test dependency to that module, and run `mvn -f <module>/pom.xml test`.

## Commit & Pull Request Guidelines

History favors short, imperative summaries such as `Update CollectTest.java`; use a concise subject describing the changed example. Keep commits focused and exclude generated artifacts or unrelated edits. Pull requests should identify affected modules, explain the behavior demonstrated or fixed, list validation commands and results, and link relevant issues. Include screenshots only when output is visual; otherwise provide a short console-output sample when useful.
