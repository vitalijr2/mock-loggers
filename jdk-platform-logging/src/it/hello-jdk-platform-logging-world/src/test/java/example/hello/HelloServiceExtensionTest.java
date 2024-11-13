package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.github.vitalijr2.logging.mock.MockLoggerExtension;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@ExtendWith(MockLoggerExtension.class)
class HelloServiceExtensionTest {

  private static Logger logger;

  @BeforeAll
  static void setUpClass() {
    logger = System.getLogger("HelloService");
  }

  @BeforeEach
  void setUp() {
    when(logger.isLoggable(Level.INFO)).thenReturn(true);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    var logger = System.getLogger("HelloService");

    verify(logger).isLoggable(Level.INFO);
    verify(logger).log(Level.INFO, "Hello " + name + "!");
    verifyNoMoreInteractions(logger);
  }

}
