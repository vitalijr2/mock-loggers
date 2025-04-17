package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.System.Logger.Level;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloServiceBasicTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld() {
    var actualLog = System.getLogger("HelloService");
    var helloService = new HelloService();

    when(actualLog.isLoggable(isA(Level.class))).thenReturn(true);

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(actualLog).isLoggable(Level.INFO);
    verify(actualLog).log(Level.INFO, "Hello World!");
    verifyNoMoreInteractions(actualLog);
  }

}
