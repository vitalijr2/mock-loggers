# Mock provider for tinylog

[Tinylog][tinylog] provider with a mock instance backed by [Mockito][].

> [!WARNING]
> This library does not support _parallel test execution_.

[![Java Version][java-version]][jdk-download]
![Tinylog Version][tinylog-version]
![Mockito Version][mockito-version]  
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]
[![Javadoc][javadoc]][javadoc-link]

## Foreword

Unlike the traditional approach where each class or even instance has
its own named logger, tinylog uses a singleton logger.
And the provider implementation is a bit complicated
to check access to the logger.
Therefore, I recommend using the [Writer](../tinylog-writer) for testing.

## How to use

Just put a test dependency to your POM:

```xml
<dependency>
    <artifactId>mock-loggers-tinylog-provider</artifactId>
    <groupId>io.github.vitalijr2.logging</groupId>
    <scope>test</scope>
    <version>1.1.1</version>
</dependency>
```

Use the `MockTinylogProvider` annotation to access the mock provider.
The simplest usage example looks like this:

```java
@MockTinylogProvider
private static LoggingProvider logger;

@Test
void helloWorld() {
    when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(logger).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(), anyString(), isNull());
}
```

See more details at [HelloServiceBasicTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceBasicTest.java)

> [!IMPORTANT]
> Keep in mind that the logger is initialized only once during the test run.

Therefore, a more complex example cleans the logger after (or before)
each test:

```java
// the static logger instance
@MockTinylogProvider
private static LoggingProvider logger;

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
    when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    verify(logger).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(),
            eq("Hello " + name + "!"), isNull());
}
```

See more details at [HelloServiceFullTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceFullTest.java)

To avoid manual cleaning of the mock logger you can use
the [jUnit extension][junit-extension] for automation:

```java
@ExtendWith(MockLoggerExtension.class)
class HelloServiceExtensionTest {

    @MockTinylogProvider
    private static LoggingProvider logger;

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        verify(logger).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(),
                eq("Hello " + name + "!"), isNull());
    }

}
```

See more details at [HelloServiceExtensionTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceExtensionTest.java)

Also you can use the annotation for automation:

```java
@MockLoggers
class HelloServiceAnnotationTest {

    @MockTinylogProvider
    private static LoggingProvider logger;

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        verify(logger).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(),
                eq("Hello " + name + "!"), isNull());
    }

}
```

See more details at [HelloServiceAnnotationTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceAnnotationTest.java)

### LoggingProvider as a parameter

This library can also inject a mock provider instance
as a parameter of a test method:

```java
@ExtendWith({MockLoggerExtension.class,MockTinylogProviderExtension.class})
class HelloServiceParameterTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld(LoggingProvider logger) {
    when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(logger).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(), anyString(), isNull());
  }

}
```

See more details ad [HelloServiceParameterTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceParameterTest.java)

### Configuration

If your application is bundled with another tinylog provider,
and it is present on the test classpath, use the configuration
to specify the use of the mock provider.
See [tinylog.properties](src/it/hello-custom-tinylog-world/src/test/resources/tinylog.properties).

[tinylog]: https://tinylog.org/v2/

[Mockito]: https://site.mockito.org

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[tinylog-version]: https://img.shields.io/static/v1?label=tinylog&message=2.7.0&color=blue&logoColor=E23D28

[mockito-version]: https://img.shields.io/static/v1?label=Mockito&message=5.15.2&color=blue&logoColor=E23D28

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers-tinylog-provider

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers-tinylog-provider

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers-tinylog-provider?smo=true

[javadoc]: https://javadoc.io/badge2/io.github.vitalijr2.logging/mock-loggers-tinylog-provider/javadoc.svg

[javadoc-link]: https://javadoc.io/doc/io.github.vitalijr2.logging/mock-loggers-tinylog-provider

[junit-extension]: ../core/
