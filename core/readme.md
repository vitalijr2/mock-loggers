# Logger Keeper and jUnit Extension

The observer pattern is implemented here: cleaners subscribe to notifications
from a keeper, who sends alerts when mock loggers need to be cleaned and reset.
Logger factories should implement the cleaner interface and register
themselves with the keeper. The jUnit extension manages the keeper
and sends alerts before and after tests.

[![Java Version][java-version]][jdk-download]
![jUnit Version][junit-version]  
![Maven Central Last Update][maven-central-last-update]
[![Maven Central][maven-central]][maven-central-link]
[![Javadoc][javadoc]][javadoc-link]  

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[junit-version]: https://img.shields.io/static/v1?label=jUnit&message=5.13.0&color=blue&logo=junit5&logoColor=E23D28

[maven-central-last-update]: https://img.shields.io/maven-central/last-update/io.github.vitalijr2.logging/mock-loggers-core

[maven-central]: https://img.shields.io/maven-central/v/io.github.vitalijr2.logging/mock-loggers-core

[maven-central-link]: https://central.sonatype.com/artifact/io.github.vitalijr2.logging/mock-loggers-core?smo=true

[javadoc]: https://javadoc.io/badge2/io.github.vitalijr2.logging/mock-loggers-core/javadoc.svg

[javadoc-link]: https://javadoc.io/doc/io.github.vitalijr2.logging/mock-loggers-core
