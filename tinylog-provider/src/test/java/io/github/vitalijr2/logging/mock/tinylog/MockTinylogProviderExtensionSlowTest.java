package io.github.vitalijr2.logging.mock.tinylog;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tinylog.provider.LoggingProvider;

@Tag("slow")
@ExtendWith(MockitoExtension.class)
class MockTinylogProviderExtensionSlowTest {

  @Mock
  private ExtensionContext extensionContext;

  private MockTinylogProviderExtension extension;

  @BeforeEach
  void setUp() {
    extension = new MockTinylogProviderExtension();
  }

  @DisplayName("Happy path")
  @Test
  void happyPath() {
    // given
    var testInstance = new HappyTest();

    // when
    extension.postProcessTestInstance(testInstance, extensionContext);

    // then
    assertAll("Mock is injected", () -> assertNotNull(testInstance.loggingProvider),
        () -> assertThat(testInstance.loggingProvider, instanceOf(LoggingProvider.class)));
  }

  @DisplayName("Throw an exception on injection")
  @Test
  void throwExceptionOnInjection() {
    // when
    var exception = assertThrows(MockTinylogProviderException.class,
        () -> MockTinylogProviderExtension.injectMockProvider(null, HappyTest.class));

    // then
    assertEquals("Cannot inject a mock provider", exception.getMessage());
  }

  @DisplayName("Unannotated field")
  @Test
  void unannotatedField() {
    // given
    var testInstance = new UnannotatedFieldTest();

    // when
    extension.postProcessTestInstance(testInstance, extensionContext);

    // then
    assertNull(testInstance.loggingProvider);
  }

  @DisplayName("Wrong type")
  @Test
  void wrongType() {
    // given
    var testInstance = new WrongTypeTest();

    // when
    extension.postProcessTestInstance(testInstance, extensionContext);

    // then
    assertNull(testInstance.loggingProvider);
  }

  static class HappyTest {

    @SuppressWarnings("unused")
    @MockTinylogProvider
    private LoggingProvider loggingProvider;

  }

  static class UnannotatedFieldTest {

    @SuppressWarnings("unused")
    private LoggingProvider loggingProvider;

  }

  static class WrongTypeTest {

    @SuppressWarnings("unused")
    @MockTinylogProvider
    private String loggingProvider;

  }

}
