# Mock loggers for Apache Commons Logging

[Apache Commons Logging][commons-logging] factory with mock loggers backed by [Mockito][].

> [!WARNING]
> This library does not support _parallel test execution_.

[![Java Version][java-version]][jdk-download]
![Mockito Version][mockito-version]  
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]
[![Javadoc][javadoc]][javadoc-link]

## How to use

Just put a test dependency to your POM:
```xml
<dependency>
    <artifactId>mock-loggers-commons</artifactId>
    <groupId>io.github.vitalijr2.logging</groupId>
    <scope>test</scope>
    <version>1.0.0</version>
</dependency>
```

The most basic usage example looks like this:

```java
import org.apache.commons.logging.LogFactory;

@Test
void helloWorld() {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(LogFactory.getLog(helloService.getClass())).info("Hello World!");
}
```
See more details at [HelloServiceBasicTest.java](src/it/hello-commons-logging-world/src/test/java/example/hello/HelloServiceBasicTest.java)

[commons-logging]: https://commons.apache.org/proper/commons-logging/

[Mockito]: https://site.mockito.org

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[mockito-version]: https://img.shields.io/static/v1?label=Mockito&message=5.14.2&color=blue&logoColor=E23D28

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers-commons

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers-commons

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers-commons?smo=true

[javadoc]: https://javadoc.io/badge2/io.github.vitalijr2.logging/mock-loggers-commons/javadoc.svg

[javadoc-link]: https://javadoc.io/doc/io.github.vitalijr2.logging/mock-loggers-commons

[junit-extension]: ../core/
