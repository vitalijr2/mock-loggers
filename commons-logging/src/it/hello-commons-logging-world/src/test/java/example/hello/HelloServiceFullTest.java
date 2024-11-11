package example.hello;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class HelloServiceFullTest {

  private static Log log;

  @BeforeAll
  static void setUpClass() {
    log = LogFactory.getLog(HelloService.class);
  }

  @BeforeEach
  void setUp() {
    clearInvocations(log);
    reset(log);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    var helloService = new HelloService();

    when(log.isInfoEnabled()).thenReturn(true);

    assertDoesNotThrow(() -> helloService.sayHello(name));

    var actualLog = LogFactory.getLog(helloService.getClass());

    verify(actualLog).isInfoEnabled();
    verify(actualLog).info("Hello " + name + "!");
    verifyNoMoreInteractions(actualLog);
  }

  @DisplayName("Null or empty name")
  @ParameterizedTest(name = "<{0}>")
  @NullAndEmptySource
  @ValueSource(strings = "   ")
  void nullOrEmptyName(String name) {
    var helloService = new HelloService();

    var exception = assertThrows(RuntimeException.class, () -> helloService.sayHello(name));

    verifyNoInteractions(LogFactory.getLog(helloService.getClass()));

    assertThat(exception.getMessage(), startsWith("Name is"));
  }

}
