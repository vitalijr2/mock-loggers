package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import elf4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloServiceBasicTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld() {
    var helloService = new HelloService();
    var logger = Logger.instance();

    when(Logger.instance().atInfo()).thenReturn(logger);

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(Logger.instance()).atInfo();
    verify(Logger.instance()).log("Hello World!");
    verifyNoMoreInteractions(Logger.instance());
  }

}
