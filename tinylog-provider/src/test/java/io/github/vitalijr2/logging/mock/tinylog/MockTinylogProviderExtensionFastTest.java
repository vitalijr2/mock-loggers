package io.github.vitalijr2.logging.mock.tinylog;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tinylog.provider.LoggingProvider;

@Tag("fast")
@ExtendWith(MockitoExtension.class)
class MockTinylogProviderExtensionFastTest {

  @Mock
  private ExtensionContext extensionContext;
  @Mock
  private Parameter parameter;
  @Mock
  private ParameterContext parameterContext;

  private MockTinylogProviderExtension extension;

  @BeforeEach
  void setUp() {
    extension = new MockTinylogProviderExtension();
  }

  @DisplayName("Unsupported parameter")
  @Test
  void unsupportedParameter() {
    // given
    when(parameter.getType()).thenAnswer(invocationOnMock -> String.class);
    when(parameterContext.getParameter()).thenReturn(parameter);

    // when and then
    assertFalse(extension.supportsParameter(parameterContext, extensionContext));
  }

  @DisplayName("Supported parameter")
  @Test
  void supportedParameter() {
    // given
    when(parameter.getType()).thenAnswer(invocationOnMock -> LoggingProvider.class);
    when(parameterContext.getParameter()).thenReturn(parameter);

    // when and then
    assertTrue(extension.supportsParameter(parameterContext, extensionContext));
  }

  @DisplayName("Resolve parameter")
  @Test
  void resolveParameter() {
    // when
    var value = extension.resolveParameter(parameterContext, extensionContext);

    // then
    assertAll("Resolved parameter", () -> assertNotNull(value),
        () -> assertThat(value, instanceOf(LoggingProvider.class)));
  }

  @DisplayName("Throw an exception on injection")
  @Test
  void throwExceptionOnInjection() {
    // when
    var exception = assertThrows(MockLoggerException.class,
        () -> MockTinylogProviderExtension.injectMockProvider(null, JustTest.class));

    // then
    assertEquals("Cannot inject a mock provider", exception.getMessage());
  }

  static class JustTest {

    @SuppressWarnings("unused")
    @MockTinylogProvider
    private LoggingProvider provider;

  }

}