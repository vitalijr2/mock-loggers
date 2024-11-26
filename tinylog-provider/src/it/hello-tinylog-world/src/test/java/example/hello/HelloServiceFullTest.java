package example.hello;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.github.vitalijr2.logging.mock.tinylog.MockTinylogProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.tinylog.Level;
import org.tinylog.provider.LoggingProvider;

class HelloServiceFullTest {

  @MockTinylogProvider
  private static LoggingProvider logger;

  @BeforeEach
  void setUp() {
    clearInvocations(logger);
    reset(logger);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    verify(logger).log(eq(Level.INFO.ordinal()), isNull(), eq(Level.INFO), isNull(), isNull(),
        eq("Hello " + name + "!"), isNull());
  }

  @DisplayName("Null or empty name")
  @ParameterizedTest(name = "<{0}>")
  @NullAndEmptySource
  @ValueSource(strings = "   ")
  void nullOrEmptyName(String name) {
    var helloService = new HelloService();

    var exception = assertThrows(RuntimeException.class, () -> helloService.sayHello(name));

    verifyNoInteractions(logger);

    assertThat(exception.getMessage(), startsWith("Name is"));
  }

}
