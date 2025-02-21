package io.github.vitalijr2.logging.mock.elf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import elf4j.Logger;
import io.github.vitalijr2.logging.mock.MockLoggerKeeper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Tag("slow")
class MockLoggerProviderSlowTest {

  private static Logger logger;

  @BeforeAll
  static void beforeAll() {
    logger = Logger.instance();
  }

  @AfterEach
  void afterEach() {
    clearInvocations(logger);
  }

  @DisplayName("Test")
  @ParameterizedTest
  @CsvFileSource(resources = "elf4j.csv", numLinesToSkip = 1)
  void test(String level, String message, int traceCount, int debugCount, int infoCount, int warningCount,
      int errorCount) {
    // given
    verifyNoInteractions(logger);
    when(logger.atTrace()).thenReturn(logger);
    when(logger.atDebug()).thenReturn(logger);
    when(logger.atInfo()).thenReturn(logger);
    when(logger.atWarn()).thenReturn(logger);
    when(logger.atError()).thenReturn(logger);

    // when
    switch (level) {
      case "TRACE":
        logger.atTrace().log(message);
        break;
      case "DEBUG":
        logger.atDebug().log(message);
        break;
      case "INFO":
        logger.atInfo().log(message);
        break;
      case "WARNING":
        logger.atWarn().log(message);
        break;
      case "ERROR":
        logger.atError().log(message);
        break;
      default:
        fail("Unknown level");
    }

    verify(logger, times(traceCount)).log("test trace message");
    verify(logger, times(debugCount)).log("test debug message");
    verify(logger, times(infoCount)).log("test info message");
    verify(logger, times(warningCount)).log("test warning message");
    verify(logger, times(errorCount)).log("test error message");
  }

  @DisplayName("Clean and reset mock loggers")
  @Test
  void cleanAndResetMockLoggers() {
    // given
    when(logger.atInfo()).thenReturn(logger);

    // when
    logger.atInfo().log("test message");

    var names = MockLoggerKeeper.getInstance().cleanAndReset();

    // then
    assertThat(names, contains("default"));

    verifyNoInteractions(logger);
  }

}
