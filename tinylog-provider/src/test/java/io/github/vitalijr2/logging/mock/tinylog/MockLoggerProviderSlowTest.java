package io.github.vitalijr2.logging.mock.tinylog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("slow")
class MockLoggerProviderSlowTest {

  private MockLoggerProvider provider;

  @BeforeEach
  void setUp() {
    provider = new MockLoggerProvider();
  }

  @AfterEach
  void tearDown() {
    clearInvocations(MockLoggerProvider.MOCK_INSTANCE);
    reset(MockLoggerProvider.MOCK_INSTANCE);
  }

  // Move to slow
  @DisplayName("Clear and reset the mock instance")
  @Test
  void cleanAndReset() throws InterruptedException {
    // when
    provider.shutdown();
    provider.cleanAndReset();

    // then
    verifyNoInteractions(MockLoggerProvider.MOCK_INSTANCE);
  }

}
