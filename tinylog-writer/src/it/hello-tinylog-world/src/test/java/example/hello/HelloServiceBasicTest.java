package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import io.github.vitalijr2.logging.mock.tinylog.MockTinylogWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.Writer;

class HelloServiceBasicTest {

  @MockTinylogWriter
  private static Writer mockWriter;

  @DisplayName("Hello world")
  @Test
  void helloWorld() throws Exception {
    reset(mockWriter);
    clearInvocations(mockWriter);

    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(mockWriter).write(isA(LogEntry.class));
  }

}
