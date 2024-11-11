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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HelloServiceFullTest {

  private static Logger logger;

  @BeforeAll
  static void setUpClass() {
    logger = LoggerFactory.getLogger(HelloService.class);
  }

  @BeforeEach
  void setUp() {
    clearInvocations(logger);
    reset(logger);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    var helloService = new HelloService();

    when(logger.isInfoEnabled()).thenReturn(true);

    assertDoesNotThrow(() -> helloService.sayHello(name));

    var actualLogger = LoggerFactory.getLogger(helloService.getClass());

    verify(actualLogger).isInfoEnabled();
    verify(actualLogger).info("Hello " + name + "!");
    verifyNoMoreInteractions(actualLogger);
  }

  @DisplayName("Null or empty name")
  @ParameterizedTest(name = "<{0}>")
  @NullAndEmptySource
  @ValueSource(strings = "   ")
  void nullOrEmptyName(String name) {
    var helloService = new HelloService();

    var exception = assertThrows(RuntimeException.class, () -> helloService.sayHello(name));

    verifyNoInteractions(LoggerFactory.getLogger(helloService.getClass()));

    assertThat(exception.getMessage(), startsWith("Name is"));
  }

}
