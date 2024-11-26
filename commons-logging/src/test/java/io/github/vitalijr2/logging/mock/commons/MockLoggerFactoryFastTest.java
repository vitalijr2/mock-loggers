package io.github.vitalijr2.logging.mock.commons;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("fast")
class MockLoggerFactoryFastTest {

  @DisplayName("Create and add logger")
  @Test
  void createLogger() {
    // given
    var loggers = new HashMap<String, Log>();
    var loggerFactory = new MockLoggerFactory(loggers);

    // when
    loggerFactory.getInstance("test");

    // then
    assertAll("Logger was created", () -> assertThat("size", loggers, aMapWithSize(1)),
        () -> assertThat("entry", loggers, hasEntry(equalTo("test"), isA(Log.class))),
        () -> assertThat(loggers.values().iterator().next(), hasToString("Mock for logger test")));
  }

  @DisplayName("Reuse existing logger")
  @Test
  void reuseExistingLogger() {
    // given
    var loggers = new HashMap<>(Map.of("test", mock(Log.class)));
    var loggerFactory = new MockLoggerFactory(loggers);

    // when
    loggerFactory.getInstance("test");

    // then
    assertAll("Logger was created", () -> assertThat("size", loggers, aMapWithSize(1)),
        () -> assertThat("entry", loggers, hasEntry(equalTo("test"), isA(Log.class))),
        () -> assertThat("Logger was created outside", loggers.values().iterator().next(),
            hasToString(startsWith("Mock for Log, hashCode:"))));
  }

  @DisplayName("Clean and reset loggers")
  @Test
  void cleanAndResetLoggers() {
    // given
    var loggers = new HashMap<>(Map.of("a", mock(Log.class), "b", mock(Log.class), "c", mock(Log.class)));
    var loggerFactory = new MockLoggerFactory(loggers);

    // when
    var loggerNames = loggerFactory.cleanAndReset();

    // then
    assertThat(loggerNames, contains("a", "b", "c"));
  }

  @DisplayName("Clear loggers")
  @Test
  void clearLoggers() {
    // given
    var loggers = new HashMap<>(Map.of("a", mock(Log.class), "b", mock(Log.class), "c", mock(Log.class)));
    var loggerFactory = new MockLoggerFactory(loggers);

    // when
    assertDoesNotThrow(() -> loggerFactory.release());

    // then
    assertThat(loggers.entrySet(), emptyIterable());
  }

  @DisplayName("getAttribute doesn't throw an exception")
  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" ", "test"})
  void getAttributes(String name) {
    // given
    var loggerFactory = new MockLoggerFactory(new HashMap<String, Log>());

    // when and then
    assertDoesNotThrow(() -> loggerFactory.getAttribute(name));
  }

  @DisplayName("getAttributeNames doesn't throw an exception")
  @Test
  void getAttributeNames() {
    // given
    var loggerFactory = new MockLoggerFactory(new HashMap<String, Log>());

    // when and then
    assertDoesNotThrow(loggerFactory::getAttributeNames);
  }

  @DisplayName("removeAttribute doesn't throw an exception")
  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" ", "test"})
  void removeAttribute(String name) {
    // given
    var loggerFactory = new MockLoggerFactory(new HashMap<String, Log>());

    // when and then
    assertDoesNotThrow(() -> loggerFactory.removeAttribute(name));
  }

}
