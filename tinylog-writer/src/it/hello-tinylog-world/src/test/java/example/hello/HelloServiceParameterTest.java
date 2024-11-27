package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

import io.github.vitalijr2.logging.mock.MockLoggerExtension;
import io.github.vitalijr2.logging.mock.tinylog.MockTinylogWriterExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.Writer;

@ExtendWith({MockLoggerExtension.class, MockTinylogWriterExtension.class})
class HelloServiceParameterTest {

  @DisplayName("Hello world")
  @Test
  void helloWorld(Writer writer) throws Exception {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(writer).write(isA(LogEntry.class));
  }

}
