package io.github.vitalijr2.logging.mock.platform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.github.vitalijr2.logging.mock.MockLoggerKeeper;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("slow")
class MockLoggerFinderSlowTest {

  private static Logger logger;

  @BeforeAll
  static void setUpClass() {
    logger = System.getLogger("test");
  }

  @AfterEach
  void tearDown() {
    clearInvocations(logger);
  }

  @DisplayName("Test")
  @ParameterizedTest
  @ValueSource(strings = {"TRACE", "DEBUG", "INFO", "WARNING", "ERROR"})
  void test(Level level) {
    // given
    verifyNoInteractions(logger);

    // when
    System.getLogger("test").log(level, "test message");

    // then
    verify(logger).log(level, "test message");
  }

  @DisplayName("Clean and reset mock loggers")
  @Test
  void cleanAndResetMockLoggers() {
    // when
    logger.log(Level.INFO, "test message");

    var names = MockLoggerKeeper.getInstance().cleanAndReset();

    // then
    assertThat(names, contains("test"));

    verifyNoInteractions(logger);
  }

}
