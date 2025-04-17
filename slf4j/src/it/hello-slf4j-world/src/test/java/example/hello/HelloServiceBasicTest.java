package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class HelloServiceBasicTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld() {
    var mockLog = LoggerFactory.getLogger(HelloService.class);
    var helloService = new HelloService();

    reset(mockLog);
    clearInvocations(mockLog);
    when(mockLog.isInfoEnabled()).thenReturn(true);

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(mockLog).isInfoEnabled();
    verify(mockLog).info("Hello World!");
    verifyNoMoreInteractions(mockLog);
  }

}
