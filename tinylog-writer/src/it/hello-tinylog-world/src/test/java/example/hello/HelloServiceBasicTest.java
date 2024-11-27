package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

import io.github.vitalijr2.logging.mock.tinylog.MockTinylogWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.Writer;

class HelloServiceBasicTest {

  @MockTinylogWriter
  private static Writer writer;

  @DisplayName("Hello world")
  @Test
  void helloWorld() throws Exception {
    var helloService = new HelloService();

    assertDoesNotThrow(helloService::sayHelloWorld);

    verify(writer).write(isA(LogEntry.class));
  }

}
