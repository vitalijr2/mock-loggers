package io.github.vitalijr2.logging.mock.platform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import java.lang.System.Logger;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class MockLoggerFinderFastTest {

  @DisplayName("Create and add logger")
  @Test
  void createLogger() {
    // given
    var loggers = new HashMap<String, Logger>();
    var loggerFinder = new MockLoggerFinder(loggers);

    // when
    loggerFinder.getLogger("test", getClass().getModule());

    // then
    assertAll("Logger was created", () -> assertThat("size", loggers, aMapWithSize(1)),
        () -> assertThat("entry", loggers, hasEntry(equalTo("test"), isA(Logger.class))),
        () -> assertThat(loggers.values().iterator().next(), hasToString("Mock for logger test")));
  }

  @DisplayName("Reuse existing logger")
  @Test
  void reuseExistingLogger() {
    // given
    var loggers = new HashMap<>(Map.of("test", mock(Logger.class)));
    var loggerFinder = new MockLoggerFinder(loggers);

    // when
    loggerFinder.getLogger("test", getClass().getModule());

    // then
    assertAll("Logger was reused", () -> assertThat("size", loggers, aMapWithSize(1)),
        () -> assertThat("entry", loggers, hasEntry(equalTo("test"), isA(Logger.class))),
        () -> assertThat("Logger was created outside", loggers.values().iterator().next(),
            hasToString(startsWith("Mock for Logger, hashCode:"))));
  }

  @DisplayName("Clean and reset loggers")
  @Test
  void cleanAndResetLoggers() {
    // given
    var loggers = new HashMap<>(Map.of("a", mock(Logger.class), "b", mock(Logger.class), "c", mock(Logger.class)));
    var loggerFinder = new MockLoggerFinder(loggers);

    // when
    var loggerNames = loggerFinder.cleanAndReset();

    // then
    assertThat(loggerNames, contains("a", "b", "c"));
  }

}
