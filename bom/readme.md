# Bill of Materials

The Bill Of Material is a special POM file that groups dependency versions that
are known to be valid and tested to work together.[>>][using-bom]

[![Java Version][java-version]][jdk-download]
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]

## Table of Contents

<!--ts-->
* [How to use](#how-to-use)

<!-- Created by https://github.com/ekalinin/github-markdown-toc -->
<!-- Added by: r2, at: Fri Feb 21 08:07:43 PM EET 2025 -->

<!--te-->

## How to use

Just put a test dependency to your POM:

```xml
<dependencies>
    <dependency>
        <artifactId>mock-loggers-*****</artifactId>
        <groupId>io.github.vitalijr2.logging</groupId>
        <scope>test</scope>
    </dependency>
</dependencies>
<dependencyManagement>
    <dependencies>
        <dependency>
            <artifactId>mock-loggers-bom</artifactId>
            <groupId>io.github.vitalijr2.logging</groupId>
            <scope>import</scope>
            <type>pom</type>
            <version>1.2.0</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

[using-bom]: https://reflectoring.io/maven-bom/#introducing-mavens-bill-of-material-bom "Using Maven’s Bill of Materials (BOM), Abdelbaki BEN ELHAJ SLIMENE"

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers-bom

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers-bom

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers-bom?smo=true
