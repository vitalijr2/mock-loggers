package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloServiceBasicTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld() {
    var actualLog = LogFactory.getLog(HelloService.class);
    var helloService = new HelloService();

    when(actualLog.isInfoEnabled()).thenReturn(true);

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(actualLog).isInfoEnabled();
    verify(actualLog).info("Hello World!");
    verifyNoMoreInteractions(actualLog);
  }

}
