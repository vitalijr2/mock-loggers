package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.vitalijr2.logging.mock.tinylog.MockTinylogProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tinylog.Level;
import org.tinylog.provider.LoggingProvider;

class HelloServiceBasicTest {

  @MockTinylogProvider
  private static LoggingProvider mockLog;

  @DisplayName("Hello world")
  @Test
  void helloWorld() {
    reset(mockLog);
    clearInvocations(mockLog);
    when(mockLog.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(mockLog).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(), anyString(), isNull());
  }

}
