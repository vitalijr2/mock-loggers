# Mock loggers for Apache Commons Logging

[Apache Commons Logging][commons-logging] factory with mock loggers
backed by [Mockito][].

> [!WARNING]
> This library does not support _parallel test execution_.

[![Java Version][java-version]][jdk-download]
![Commons Logging Version][commons-logging-version]
![Mockito Version][mockito-version]  
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]
[![Javadoc][javadoc]][javadoc-link]

## Table of Contents

<!--ts-->
* [How to use](#how-to-use)

<!-- Created by https://github.com/ekalinin/github-markdown-toc -->
<!-- Added by: r2, at: Fri Feb 21 08:07:43 PM EET 2025 -->

<!--te-->

## How to use

Just put a test dependency to your POM:

```xml
<dependency>
    <artifactId>mock-loggers-commons-logging</artifactId>
    <groupId>io.github.vitalijr2.logging</groupId>
    <scope>test</scope>
    <version>1.3.0</version>
</dependency>
```

The simplest usage example looks like this:

```java
@Test
void helloWorld() {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(LogFactory.getLog(helloService.getClass())).info("Hello World!");
}
```

See more details at [HelloServiceBasicTest.java](src/it/hello-commons-logging-world/src/test/java/example/hello/HelloServiceBasicTest.java)

> [!IMPORTANT]
> Keep in mind that all loggers are initialized only once during the test run.

Therefore, a more complex example cleans the loggers after (or before)
each test:

```java
// the static logger instance
private static Log log;

// initialize the mock logger once
@BeforeAll
static void setUpClass() {
    log = LogFactory.getLog(HelloService.class);
}

// clean the mock logger after each test
@AfterEach
void tearDown() {
    clearInvocations(log);
}

// use the mock logger in a test
@DisplayName("Names")
@ParameterizedTest(name = "<{0}>")
@ValueSource(strings = {"John", "Jane"})
void names(String name) {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    var actualLog = LogFactory.getLog(helloService.getClass());

    verify(actualLog).info("Hello " + name + "!");
    verifyNoMoreInteractions(actualLog);
}
```

See more details at [HelloServiceFullTest.java](src/it/hello-commons-logging-world/src/test/java/example/hello/HelloServiceFullTest.java)

To avoid manual cleaning of mock loggers you can use
the [jUnit extension][junit-extension] for automation:

```java
@ExtendWith(MockLoggerExtension.class)
class HelloServiceExtensionTest {

    private static Log log;

    @BeforeAll
    static void setUpClass() {
        log = LogFactory.getLog(HelloService.class);
    }

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        var actualLog = LogFactory.getLog(helloService.getClass());

        verify(actualLog).info("Hello " + name + "!");
        verifyNoMoreInteractions(actualLog);
    }

}
```

See more details at [HelloServiceExtensionTest.java](src/it/hello-commons-logging-world/src/test/java/example/hello/HelloServiceExtensionTest.java)

Also you can use the annotation for automation:

```java
@MockLoggers
class HelloServiceAnnotationTest {

    private static Log log;

    @BeforeAll
    static void setUpClass() {
        log = LogFactory.getLog(HelloService.class);
    }

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        var actualLog = LogFactory.getLog(helloService.getClass());

        verify(actualLog).info("Hello " + name + "!");
        verifyNoMoreInteractions(actualLog);
    }

}
```

See more details at [HelloServiceAnnotationTest.java](src/it/hello-commons-logging-world/src/test/java/example/hello/HelloServiceAnnotationTest.java)

[commons-logging]: https://commons.apache.org/proper/commons-logging/

[Mockito]: https://site.mockito.org

[java-version]: https://img.shields.io/static/v1?label=Java&message=17&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java17

[commons-logging-version]: https://img.shields.io/static/v1?label=commons-logging&message=1.3.5&color=blue&logoColor=E23D28

[mockito-version]: https://img.shields.io/static/v1?label=Mockito&message=5.20.0&color=blue&logoColor=E23D28

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers-commons-logging

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers-commons-logging

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers-commons-logging?smo=true

[javadoc]: https://javadoc.io/badge2/io.github.vitalijr2.logging/mock-loggers-commons-logging/javadoc.svg

[javadoc-link]: https://javadoc.io/doc/io.github.vitalijr2.logging/mock-loggers-commons-logging

[junit-extension]: ../core/
