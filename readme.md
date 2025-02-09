# Mock Loggers

Different logging services can be tested using mock loggers backed by [Mockito][].

> [!WARNING]
> This library does not support _parallel test execution_.

[![Java Version][java-version]][jdk-download]
![jUnit Version][junit-version]
![Mockito Version][mockito-version]
[![License][license-badge]][license-link]  
[![GitHub master check runs][github-master-check-runs]][github-master-check-runs-link]
[![Codacy Badge][codacy-badge]][codacy-badge-link]
[![Codacy Coverage][codacy-coverage]][codacy-coverage-link]
![GitHub commit activity][github-commit-activity]
[![Today's hits][today-hits]][today-hits-link]

## Table of Contents

<!--ts-->
* [How to use](#how-to-use)
* [Other logging libraries and frameworks](#other-logging-libraries-and-frameworks)
* [Credits](#credits)
* [Contributing](#contributing)
* [History](#history)
* [License](#license)

<!-- Created by https://github.com/ekalinin/github-markdown-toc -->
<!-- Added by: r2, at: Sun Feb  9 09:27:54 PM EET 2025 -->

<!--te-->

## How to use

The simplest usage example looks like this:

```java
@Test
void helloWorld() {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(System.getLogger("HelloService")).log(Level.INFO, "Hello World!");
}
```

Now this library implements services for [JDK Platform Logging][jdk-logging],
[Apache Commons Logging][commons-logging], [SLF4J][slf4j] and [tinylog][].

See more examples in the relevant modules of this project:

- for Apache Commons Logging in [mock-loggers-commons-logging](commons-logging)
- for JDK Platform Logging in [mock-loggers-jdk-platform-logging](jdk-platform-logging)
- for SLF4J in [mock-loggers-slf4j](slf4j)
- for tinylog in [mock-loggers-tinylog-writer](tinylog-writer) and [mock-loggers-tinylog-provider](tinylog-provider)

## Other logging libraries and frameworks

- [Apache Log4j: Unit Testing in Maven][log4j-unit-testing-in-maven]
- [Unit Test logback Using JUnit][logback-unit-test-using-junit], it's old but gold

## Credits

There are two projects which inspired me to make this library:

- [s4u/slf4j-mock][slf4j-mock]
- [ocarlsen/mock-slf4j-impl][mock-slf4j-impl]

## Contributing

Please read [Contributing](contributing.md).

## History

See [Changelog](changelog.md)

## License

```text
Copyright (C) 2024 Vitalij Berdinskih

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

See full text [here](LICENSE "the LICENSE file").

[Mockito]: https://site.mockito.org

[jdk-logging]: https://www.baeldung.com/java-9-logging-api "Java Platform Logging API"

[commons-logging]: https://commons.apache.org/proper/commons-logging/

[slf4j]: https://www.slf4j.org/

[tinylog]: https://tinylog.org/v2/

[java-version]: https://img.shields.io/static/v1?label=Java&message=11&color=blue&logoColor=E23D28

[jdk-download]: https://www.oracle.com/java/technologies/downloads/#java11

[junit-version]: https://img.shields.io/static/v1?label=jUnit&message=5.11.3&color=blue&logo=junit5&logoColor=E23D28

[mockito-version]: https://img.shields.io/static/v1?label=Mockito&message=5.15.2&color=blue&logoColor=E23D28

[github-master-check-runs]: https://img.shields.io/github/check-runs/vitalijr2/mock-loggers/master

[github-master-check-runs-link]: https://github.com/vitalijr2/mock-loggers/actions?query=branch%3Amaster

[codacy-badge]: https://app.codacy.com/project/badge/Grade/3c0345d6db684e388deb3357362526c0

[codacy-badge-link]: https://app.codacy.com/gh/vitalijr2/mock-loggers/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade

[codacy-coverage]: https://app.codacy.com/project/badge/Coverage/3c0345d6db684e388deb3357362526c0

[codacy-coverage-link]: https://app.codacy.com/gh/vitalijr2/mock-loggers/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage

[github-commit-activity]: https://img.shields.io/github/commit-activity/y/vitalijr2/mock-loggers

[today-hits]: https://hits.sh/github.com/vitalijr2/mock-loggers.svg?view=today-total&label=today's%20hits

[today-hits-link]: https://hits.sh/github.com/vitalijr2/mock-loggers/

[log4j-unit-testing-in-maven]: https://logging.apache.org/log4j/2.3.x/manual/configuration.html#UnitTestingInMaven

[logback-unit-test-using-junit]: https://www.iamninad.com/posts/unit-test-logback-using-junit/

[slf4j-mock]: https://github.com/s4u/slf4j-mock

[mock-slf4j-impl]: https://github.com/ocarlsen/mock-slf4j-impl

[license-badge]: https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat

[license-link]: https://www.apache.org/licenses/LICENSE-2.0.html
