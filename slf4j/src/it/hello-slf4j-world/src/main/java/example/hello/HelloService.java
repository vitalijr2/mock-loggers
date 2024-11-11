package example.hello;

import static java.util.Objects.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloService {

  private final Logger logger = LoggerFactory.getLogger(HelloService.class);

  public String sayHelloWorld() {
    return sayHello("World");
  }

  public String sayHello(String name) {
    if (requireNonNull(name, "Name is missed").isBlank()) {
      throw new IllegalArgumentException("Name is empty");
    }

    var greeting = "Hello " + name + "!";

    if (logger.isInfoEnabled()) {
      logger.info(greeting);
    }

    return greeting;
  }

}
