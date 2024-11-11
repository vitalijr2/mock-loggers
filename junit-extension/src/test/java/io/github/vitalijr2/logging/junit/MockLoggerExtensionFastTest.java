package io.github.vitalijr2.logging.junit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.github.vitalijr2.logging.keeper.MockLoggerKeeper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("fast")
class MockLoggerExtensionFastTest {

  @Mock
  private MockLoggerKeeper loggerKeeper;
  @Mock
  private ExtensionContext extensionContext;

  private MockLoggerExtension extension;

  @BeforeEach
  void setUp() {
    extension = new MockLoggerExtension(loggerKeeper);
  }

  @DisplayName("Clean and reset loggers after each test")
  @Test
  void resetLoggersAfterEachTest() {
    // when
    assertDoesNotThrow(() -> extension.afterEach(extensionContext));

    // then
    verifyNoInteractions(extensionContext);
    verify(loggerKeeper).cleanAndReset();
  }

  @DisplayName("Clean and reset loggers before each test")
  @Test
  void resetLoggersBeforeEachTest() {
    // when
    assertDoesNotThrow(() -> extension.beforeEach(extensionContext));

    // then
    verifyNoInteractions(extensionContext);
    verify(loggerKeeper).cleanAndReset();
  }

}
