package example.hello;

import static java.util.Objects.requireNonNull;

import org.tinylog.Logger;

public class HelloService {

  public String sayHelloWorld() {
    return sayHello("World");
  }

  public String sayHello(String name) {
    if (requireNonNull(name, "Name is missed").isBlank()) {
      throw new IllegalArgumentException("Name is empty");
    }

    var greeting = "Hello " + name + "!";

    Logger.info(greeting);

    return greeting;
  }

}
