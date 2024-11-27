package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.vitalijr2.logging.mock.MockLoggerExtension;
import io.github.vitalijr2.logging.mock.tinylog.MockTinylogProviderExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.tinylog.Level;
import org.tinylog.provider.LoggingProvider;

@ExtendWith({MockLoggerExtension.class,MockTinylogProviderExtension.class})
class HelloServiceParameterTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld(LoggingProvider logger) {
    when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(logger).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(), anyString(), isNull());
  }

}
