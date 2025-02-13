# Mock loggers for elf4j

[elf4j][] provider with mock loggers backed by [Mockito][].

> [!WARNING]
> This library does not support _parallel test execution_.

[![Java Version][java-version]][jdk-download]
![elf4j Version][elf4j-version]
![Mockito Version][mockito-version]  
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]
[![Javadoc][javadoc]][javadoc-link]

## Table of Contents

<!--ts-->

## How to use

Just put a test dependency to your POM:

```xml
<dependency>
    <artifactId>mock-loggers-elf4j</artifactId>
    <groupId>io.github.vitalijr2.logging</groupId>
    <scope>test</scope>
    <version>1.1.1</version>
</dependency>
```

The simplest usage example looks like this:

```java
@Test
void helloWorld() {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(Logger.instance()).atInfo().log("Hello World!");
}
```

[elf4j]: https://github.com/elf4j/elf4j

[Mockito]: https://site.mockito.org

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[elf4j-version]: https://img.shields.io/static/v1?label=elf4j&message=4.1.0&color=blue&logoColor=E23D28

[mockito-version]: https://img.shields.io/static/v1?label=Mockito&message=5.15.2&color=blue&logoColor=E23D28

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers-elf4j

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers-elf4j

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers-elf4j?smo=true

[javadoc]: https://javadoc.io/badge2/io.github.vitalijr2.logging/mock-loggers-elf4j/javadoc.svg

[javadoc-link]: https://javadoc.io/doc/io.github.vitalijr2.logging/mock-loggers-elf4j
