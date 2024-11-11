package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.github.vitalijr2.logging.mock.MockLoggers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MockLoggers
class HelloServiceAnnotationTest {

  private static Logger logger;

  @BeforeAll
  static void setUpClass() {
    logger = LoggerFactory.getLogger(HelloService.class);
  }

  @BeforeEach
  void setUp() {
    when(logger.isInfoEnabled()).thenReturn(true);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    var actualLogger = LoggerFactory.getLogger(helloService.getClass());

    verify(actualLogger).isInfoEnabled();
    verify(actualLogger).info("Hello " + name + "!");
    verifyNoMoreInteractions(actualLogger);
  }

}
