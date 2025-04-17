package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
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
    var mockLog = System.getLogger("HelloService");
    var helloService = new HelloService();

    reset(mockLog);
    clearInvocations(mockLog);
    when(mockLog.isLoggable(isA(Level.class))).thenReturn(true);

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(mockLog).isLoggable(Level.INFO);
    verify(mockLog).log(Level.INFO, "Hello World!");
    verifyNoMoreInteractions(mockLog);
  }

}
