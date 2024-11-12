package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import io.github.vitalijr2.logging.mock.MockLoggers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@MockLoggers
class HelloServiceAnnotationTest {

  private static Log log;

  @BeforeAll
  static void setUpClass() {
    log = LogFactory.getLog(HelloService.class);
  }

  @BeforeEach
  void setUp() {
    when(log.isInfoEnabled()).thenReturn(true);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    var actualLog = LogFactory.getLog(helloService.getClass());

    verify(actualLog).isInfoEnabled();
    verify(actualLog).info("Hello " + name + "!");
    verifyNoMoreInteractions(actualLog);
  }

}
