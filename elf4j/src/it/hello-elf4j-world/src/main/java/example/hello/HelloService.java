package example.hello;

import static java.util.Objects.requireNonNull;

import elf4j.Logger;

public class HelloService {

  private final Logger log = Logger.instance();

  public String sayHelloWorld() {
    return sayHello("World");
  }

  public String sayHello(String name) {
    if (requireNonNull(name, "Name is missed").isBlank()) {
      throw new IllegalArgumentException("Name is empty");
    }

    var greeting = "Hello " + name + "!";

    log.atInfo().log(greeting);

    return greeting;
  }

}
