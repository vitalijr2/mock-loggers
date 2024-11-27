package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.vitalijr2.logging.mock.MockLoggerExtension;
import io.github.vitalijr2.logging.mock.tinylog.MockTinylogProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.tinylog.Level;
import org.tinylog.provider.LoggingProvider;

@ExtendWith(MockLoggerExtension.class)
class HelloServiceExtensionTest {

  @MockTinylogProvider
  private static LoggingProvider logger;

  @BeforeEach
  void setUp() {
    when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    verify(logger).log(anyInt(), isNull(), eq(Level.INFO), isNull(), isNull(),
        eq("Hello " + name + "!"), isNull());
  }

}
