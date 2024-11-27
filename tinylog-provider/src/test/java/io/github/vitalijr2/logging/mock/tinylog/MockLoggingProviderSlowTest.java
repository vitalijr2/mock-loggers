package io.github.vitalijr2.logging.mock.tinylog;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.tinylog.Level;
import org.tinylog.Logger;

@Tag("slow")
class MockLoggingProviderSlowTest {

  private MockLoggingProvider provider;

  @BeforeEach
  void setUp() {
    provider = new MockLoggingProvider();
  }

  @AfterEach
  void tearDown() {
    clearInvocations(MockLoggingProvider.MOCK_INSTANCE);
    reset(MockLoggingProvider.MOCK_INSTANCE);
  }

  @DisplayName("Clear and reset the mock instance")
  @Test
  void cleanAndReset() {
    // given
    when(MockLoggingProvider.MOCK_INSTANCE.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    // when
    Logger.info("test message");
    provider.cleanAndReset();

    // then
    verifyNoInteractions(MockLoggingProvider.MOCK_INSTANCE);
  }

  @DisplayName("Service provider")
  @Test
  void serviceProvider() {
    // given
    when(MockLoggingProvider.MOCK_INSTANCE.getMinimumLevel(isNull())).thenReturn(Level.INFO);

    // when
    Logger.info("test message");

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE, atLeastOnce()).getMinimumLevel(isNull());
    verify(MockLoggingProvider.MOCK_INSTANCE).log(eq(Level.INFO.ordinal()), isNull(), eq(Level.INFO), isNull(), isNull(),
        eq("test message"), isNull());
    verifyNoMoreInteractions(MockLoggingProvider.MOCK_INSTANCE);
  }

}
