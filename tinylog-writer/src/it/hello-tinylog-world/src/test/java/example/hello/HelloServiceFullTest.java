package example.hello;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.github.vitalijr2.logging.mock.tinylog.MockTinylogWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.Writer;

@ExtendWith(MockitoExtension.class)
class HelloServiceFullTest {

  @Captor
  private ArgumentCaptor<LogEntry> logEntryCaptor;
  @MockTinylogWriter
  private static Writer writer;

  @BeforeEach
  void setUp() {
    clearInvocations(writer);
    reset(writer);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) throws Exception {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    verify(writer).write(logEntryCaptor.capture());

    assertEquals("Hello " + name + "!", logEntryCaptor.getValue().getMessage());
  }

  @DisplayName("Null or empty name")
  @ParameterizedTest(name = "<{0}>")
  @NullAndEmptySource
  @ValueSource(strings = "   ")
  void nullOrEmptyName(String name) {
    var helloService = new HelloService();

    var exception = assertThrows(RuntimeException.class, () -> helloService.sayHello(name));

    verifyNoInteractions(writer);

    assertThat(exception.getMessage(), startsWith("Name is"));
  }

}
