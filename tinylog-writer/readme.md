# Mock writer for tinylog

[Tinylog][tinylog] writer with a mock instance backed by [Mockito][].

> [!WARNING]
> This library does not support _parallel test execution_.

[![Java Version][java-version]][jdk-download]
![Tinylog Version][tinylog-version]
![Mockito Version][mockito-version]  
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]
[![Javadoc][javadoc]][javadoc-link]

## Foreword

Unlike the traditional approach where each class or even instance has its own named logger,
tinylog uses a singleton logger. Keep this in mind when testing logger calls.

## How to use

Just put a test dependency to your POM:
```xml
<dependency>
    <artifactId>mock-loggers-tinylog-writer</artifactId>
    <groupId>io.github.vitalijr2.logging</groupId>
    <scope>test</scope>
    <version>1.1.0</version>
</dependency>
```

Use the `MockTinylogWriter` annotation to access the mock writer. The simplest usage example looks like this:
```java
@MockTinylogWriter
private static Writer writer;

@Test
void helloWorld() {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(writer).write(isA(LogEntry.class));
}
```
See more details at [HelloServiceBasicTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceBasicTest.java)

> [!IMPORTANT]
> Keep in mind that the writer are initialized only once during the test run.

Therefore, a more complex example cleans the writer after (or before) each test:
```java
// the static logger instance
@Captor
private ArgumentCaptor<LogEntry> logEntryCaptor;
@MockTinylogWriter
private static Writer writer;

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

    verify(writer).write(logEntryCaptor.capture());

    assertEquals("Hello " + name + "!", logEntryCaptor.getValue().getMessage());
}
```
See more details at [HelloServiceFullTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceFullTest.java)

To avoid manual cleaning of mock writer you can use the [jUnit extension][junit-extension] for automation:
```java
@ExtendWith({MockitoExtension.class, MockLoggerExtension.class})
class HelloServiceExtensionTest {

    @Captor
    private ArgumentCaptor<LogEntry> logEntryCaptor;
    @MockTinylogWriter
    private static Writer writer;

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        verify(writer).write(logEntryCaptor.capture());

        assertEquals("Hello " + name + "!", logEntryCaptor.getValue().getMessage());
    }

}
```
See more details at [HelloServiceExtensionTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceExtensionTest.java)

Also you can use the annotation for automation:
```java
@ExtendWith(MockitoExtension.class)
@MockLoggers
class HelloServiceAnnotationTest {

    @Captor
    private ArgumentCaptor<LogEntry> logEntryCaptor;
    @MockTinylogWriter
    private static Writer writer;

    @DisplayName("Names")
    @ParameterizedTest(name = "<{0}>")
    @ValueSource(strings = {"John", "Jane"})
    void names(String name) {
        when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

        var helloService = new HelloService();

        assertDoesNotThrow(() -> helloService.sayHello(name));

        verify(writer).write(logEntryCaptor.capture());

        assertEquals("Hello " + name + "!", logEntryCaptor.getValue().getMessage());
    }

}
```
See more details at [HelloServiceAnnotationTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceAnnotationTest.java)

### Writer as a parameter

This library can also inject a mock writer instance as a parameter of a test method:
```java
@ExtendWith({MockLoggerExtension.class,MockTinylogWriterExtension.class})
class HelloServiceParameterTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld(Writer writer) {
    when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(writer).write(isA(LogEntry.class));
  }

}
```
See more details ad [HelloServiceParameterTest.java](src/it/hello-tinylog-world/src/test/java/example/hello/HelloServiceParameterTest.java)

### Configuration

> [!IMPORTANT]
> It is necessary to specify the use of this writer in the configuration.

See [tinylog.properties](src/it/hello-tinylog-world/src/test/resources/tinylog.properties).

[tinylog]: https://tinylog.org/v2/

[Mockito]: https://site.mockito.org

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[tinylog-version]: https://img.shields.io/static/v1?label=tinylog&message=2.7.0&color=blue&logoColor=E23D28

[mockito-version]: https://img.shields.io/static/v1?label=Mockito&message=5.15.2&color=blue&logoColor=E23D28

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers-tinylog-writer

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers-tinylog-writer

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers-tinylog-writer?smo=true

[javadoc]: https://javadoc.io/badge2/io.github.vitalijr2.logging/mock-loggers-tinylog-writer/javadoc.svg

[javadoc-link]: https://javadoc.io/doc/io.github.vitalijr2.logging/mock-loggers-tinylog-writer

[junit-extension]: ../core/
