package io.github.vitalijr2.logging.mock.slf4j;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("slow")
class MockLoggerFactorySlowTest {

  private static Logger logger;

  @BeforeAll
  static void beforeAll() {
    logger = LoggerFactory.getLogger("test");
  }

  @AfterEach
  void tearDown() {
    clearInvocations(logger);
  }

  @DisplayName("Test")
  @ParameterizedTest
  @CsvFileSource(resources = "slf4j.csv", numLinesToSkip = 1)
  void test(String level, String message, int traceCount, int debugCount, int infoCount, int warningCount,
      int errorCount) {
    // given
    verifyNoInteractions(logger);

    // when
    switch (level) {
      case "TRACE":
        LoggerFactory.getLogger("test").trace(message);
        break;
      case "DEBUG":
        LoggerFactory.getLogger("test").debug(message);
        break;
      case "INFO":
        LoggerFactory.getLogger("test").info(message);
        break;
      case "WARNING":
        LoggerFactory.getLogger("test").warn(message);
        break;
      case "ERROR":
        LoggerFactory.getLogger("test").error(message);
        break;
      default:
        fail("Unknown level");
    }

    // then
    verify(logger, times(traceCount)).trace("test trace message");
    verify(logger, times(debugCount)).debug("test debug message");
    verify(logger, times(infoCount)).info("test info message");
    verify(logger, times(warningCount)).warn("test warning message");
    verify(logger, times(errorCount)).error("test error message");
  }

}
