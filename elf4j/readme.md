# Mock loggers for elf4j

[SLF4J][slf4j] provider with mock loggers backed by [Mockito][].

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
