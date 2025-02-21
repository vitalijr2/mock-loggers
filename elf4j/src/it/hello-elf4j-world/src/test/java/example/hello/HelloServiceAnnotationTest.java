package example.hello;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import elf4j.Logger;
import io.github.vitalijr2.logging.mock.MockLoggers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@MockLoggers
class HelloServiceAnnotationTest {

  @BeforeEach
  void setUp() {
    var logger = Logger.instance();

    when(Logger.instance().atInfo()).thenReturn(logger);
  }

  @DisplayName("Names")
  @ParameterizedTest(name = "<{0}>")
  @ValueSource(strings = {"John", "Jane"})
  void names(String name) {
    var helloService = new HelloService();

    assertDoesNotThrow(() -> helloService.sayHello(name));

    verify(Logger.instance()).atInfo();
    verify(Logger.instance()).log("Hello " + name + "!");
    verifyNoMoreInteractions(Logger.instance());
  }

}
