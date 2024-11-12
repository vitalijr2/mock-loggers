# Mock loggers for JDK Platform Logging

[JDK Platform Logging][jdk-logging] Service with mock loggers backed by [Mockito][].

**WARNING:** this library does not support _parallel test execution_.

[![Java Version][java-version]][jdk-download]
![Mockito Version][mockito-version]  
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]
[![Javadoc][javadoc]][javadoc-link]  

## How to use

Just put a test dependency to your POM:
```xml
<dependency>
    <artifactId>mock-loggers-jdk-platform-logging</artifactId>
    <groupId>io.github.vitalijr2.logging</groupId>
    <scope>test</scope>
    <version>1.0.0</version>
</dependency>
```

The most basic usage example looks like this:
```java
@Test
void helloWorld() {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(System.getLogger("HelloService")).log(Level.INFO, "Hello World!");
}
```
See more details at [HelloServiceBasicTest.java](src/it/hello-jdk-platform-logging-world/src/test/java/example/hello/HelloServiceBasicTest.java)

It should be taken into account that all loggers are initialized only once during the run of tests.
Therefore, a more complex example cleans the loggers before (or after) each test:
```java
// the static logger instance
private static Logger logger;

// initialize the mock logger once
@BeforeAll
static void setUpClass() {
    logger = System.getLogger("HelloService");
}

// clean the mock logger after each test
@AfterEach
void tearDown() {
    clearInvocations(logger);
}

// use the mock logger in a test
@DisplayName("Names")
@ParameterizedTest(name = "<{0}>")
@ValueSource(strings = {"John", "Jane"})
void names(String name) {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    var logger = System.getLogger("HelloService");

    verify(logger).log(Level.INFO, "Hello " + name + "!");
    verifyNoMoreInteractions(logger);
}
```
See more details at [HelloServiceFullTest.java](src/it/hello-jdk-platform-logging-world/src/test/java/example/hello/HelloServiceFullTest.java)

To avoid manual cleaning of mock loggers you can use the [jUnit extension][junit-extension] for automation.
```java
@ExtendWith(MockLoggerExtension.class)
class HelloServiceExtensionTest {

    private static Logger logger;

    @BeforeAll
    static void setUpClass() {
        logger = System.getLogger("HelloService");
    }

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        var logger = System.getLogger("HelloService");

        verify(logger).log(Level.INFO, "Hello " + name + "!");
        verifyNoMoreInteractions(logger);
    }

}
```
See more details at [HelloServiceExtensionTest.java](src/it/hello-jdk-platform-logging-world/src/test/java/example/hello/HelloServiceExtensionTest.java)

Also you can use the annotation for automation.
```java
@MockLoggers
class HelloServiceAnnotationTest {

    private static Logger logger;

    @BeforeAll
    static void setUpClass() {
        logger = System.getLogger("HelloService");
    }

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        var logger = System.getLogger("HelloService");

        verify(logger).log(Level.INFO, "Hello " + name + "!");
        verifyNoMoreInteractions(logger);
    }

}
```
See more details at [HelloServiceAnnotationTest.java](src/it/hello-jdk-platform-logging-world/src/test/java/example/hello/HelloServiceAnnotationTest.java)

[Mockito]: https://site.mockito.org

[jdk-logging]: https://www.baeldung.com/java-9-logging-api "Java Platform Logging API"

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[mockito-version]: https://img.shields.io/static/v1?label=Mockito&message=5.14.2&color=blue&logoColor=E23D28

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers?smo=true

[javadoc]: https://javadoc.io/badge2/io.github.vitalijr2.logging/mock-loggers/javadoc.svg

[javadoc-link]: https://javadoc.io/doc/io.github.vitalijr2.logging/mock-loggers

[junit-extension]: ../core/