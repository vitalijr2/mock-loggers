package io.github.vitalijr2.logging.mock.commons;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.github.vitalijr2.logging.mock.MockLoggerKeeper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Tag("slow")
class MockLoggerFactorySlowTest {

  private static Log log;

  @BeforeAll
  static void setUpClass() {
    log = LogFactory.getLog(MockLoggerFactorySlowTest.class);
  }

  @AfterEach
  void tearDown() {
    clearInvocations(log);
  }

  @DisplayName("Test")
  @ParameterizedTest
  @CsvFileSource(resources = "commons-logging.csv", numLinesToSkip = 1)
  void test(String level, int traceCount, int debugCount, int infoCount, int warningCount,
      int errorCount, int fatalCount, String logName) {
    // given
    var message = "test " + level + " message";

    verifyNoInteractions(log);

    // when
    switch (level) {
      case "TRACE":
        LogFactory.getLog(logName).trace(message);
        break;
      case "DEBUG":
        LogFactory.getLog(logName).debug(message);
        break;
      case "INFO":
        LogFactory.getLog(logName).info(message);
        break;
      case "WARNING":
        LogFactory.getLog(logName).warn(message);
        break;
      case "ERROR":
        LogFactory.getLog(logName).error(message);
        break;
      case "FATAL":
        LogFactory.getLog(logName).fatal(message);
        break;
      default:
        fail("Unknown level");
    }

    // then
    verify(log, times(traceCount)).trace("test TRACE message");
    verify(log, times(debugCount)).debug("test DEBUG message");
    verify(log, times(infoCount)).info("test INFO message");
    verify(log, times(warningCount)).warn("test WARNING message");
    verify(log, times(errorCount)).error("test ERROR message");
    verify(log, times(fatalCount)).fatal("test FATAL message");
  }

  @DisplayName("Clean and reset mock loggers")
  @Test
  void cleanAndResetMockLoggers() {
    // when
    log.info("test message");

    var names = MockLoggerKeeper.getInstance().cleanAndReset();

    // then
    assertThat(names, contains(MockLoggerFactorySlowTest.class.getName()));

    verifyNoInteractions(log);
  }

}
