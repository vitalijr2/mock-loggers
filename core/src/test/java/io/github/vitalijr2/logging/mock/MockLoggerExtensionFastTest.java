package io.github.vitalijr2.logging.mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class MockLoggerExtensionFastTest {

  @Captor
  private ArgumentCaptor<Supplier<String>> messageCaptor;
  @Mock
  private MockLoggerKeeper loggerKeeper;
  @Mock
  private ExtensionContext extensionContext;
  @Mock
  private System.Logger extensionLogger;


  private MockLoggerExtension extension;

  @BeforeEach
  void setUp() {
    extension = new MockLoggerExtension(loggerKeeper, extensionLogger);
  }

  @DisplayName("Clean and reset loggers after each test")
  @Test
  void resetLoggersAfterEachTest() {
    // given
    when(loggerKeeper.cleanAndReset()).thenReturn(List.of("a", "b", "c"));

    // when
    assertDoesNotThrow(() -> extension.afterEach(extensionContext));

    // then
    verifyNoInteractions(extensionContext);
    verify(extensionLogger).log(eq(Level.DEBUG), messageCaptor.capture());
    verify(loggerKeeper).cleanAndReset();

    assertEquals("Clean and reset the loggers: a, b, c", messageCaptor.getValue().get(),
        "logging message");
  }

  @DisplayName("Clean and reset loggers before each test")
  @Test
  void resetLoggersBeforeEachTest() {
    // given
    when(loggerKeeper.cleanAndReset()).thenReturn(List.of("a", "b", "c"));

    // when
    assertDoesNotThrow(() -> extension.beforeEach(extensionContext));

    // then
    verifyNoInteractions(extensionContext);
    verify(extensionLogger).log(eq(Level.DEBUG), messageCaptor.capture());
    verify(loggerKeeper).cleanAndReset();

    assertEquals("Clean and reset the loggers: a, b, c", messageCaptor.getValue().get(),
        "logging message");
  }

}
