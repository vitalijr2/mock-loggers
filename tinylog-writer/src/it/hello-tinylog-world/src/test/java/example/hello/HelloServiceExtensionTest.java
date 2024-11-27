package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import io.github.vitalijr2.logging.mock.MockLoggerExtension;
import io.github.vitalijr2.logging.mock.tinylog.MockTinylogWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.Writer;

@ExtendWith({MockitoExtension.class, MockLoggerExtension.class})
class HelloServiceExtensionTest {

  @Captor
  private ArgumentCaptor<LogEntry> logEntryCaptor;
  @MockTinylogWriter
  private static Writer writer;

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) throws Exception {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    verify(writer).write(logEntryCaptor.capture());

    assertEquals("Hello " + name + "!", logEntryCaptor.getValue().getMessage());
  }

}
