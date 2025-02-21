package io.github.vitalijr2.logging.mock.slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.verifyNoInteractions;

import io.github.vitalijr2.logging.mock.MockLoggerKeeper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("slow")
class MockLoggerServiceProviderSlowTest {

  @DisplayName("Clean and reset mock loggers")
  @Test
  void cleanAndResetMockLoggers() {
    // given
    var loggerProvider = new MockLoggerServiceProvider();

    // when
    loggerProvider.getLoggerFactory().getLogger("test").info("test message");

    var names = MockLoggerKeeper.getInstance().cleanAndReset();

    // then
    assertThat(names, contains("test"));

    verifyNoInteractions(loggerProvider.getLoggerFactory().getLogger("test"));
  }

}
