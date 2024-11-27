package io.github.vitalijr2.logging.mock.tinylog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.tinylog.Level;
import org.tinylog.format.MessageFormatter;
import org.tinylog.provider.NopContextProvider;


@Tag("fast")
class MockLoggingProviderFastTest {

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

  @DisplayName("Get a context provider")
  @Test
  void getContextProvider() {
    // given
    when(MockLoggingProvider.MOCK_INSTANCE.getContextProvider()).thenReturn(new NopContextProvider());

    // when and then
    assertNotNull(provider.getContextProvider());

    verify(MockLoggingProvider.MOCK_INSTANCE).getContextProvider();
  }

  @DisplayName("Get a minimum level")
  @ParameterizedTest
  @ValueSource(strings = {"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF"})
  void getMinimumLevel(Level expectedlevel) {
    // given
    when(MockLoggingProvider.MOCK_INSTANCE.getMinimumLevel()).thenReturn(expectedlevel);

    // when
    var actualLevel = provider.getMinimumLevel();

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE).getMinimumLevel();

    assertEquals(expectedlevel, actualLevel);
  }

  @DisplayName("Get a minimum level for a classname")
  @ParameterizedTest
  @ValueSource(strings = {"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF"})
  void getMinimumLevelByTagWithCustomLevel(Level expectedlevel) {
    // given
    when(MockLoggingProvider.MOCK_INSTANCE.getMinimumLevel(anyString())).thenReturn(expectedlevel);

    // when
    var actualLevel = provider.getMinimumLevel("test tag");

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE).getMinimumLevel("test tag");

    assertEquals(expectedlevel, actualLevel);
  }

  @DisplayName("Is it enabled?")
  @ParameterizedTest
  @CsvSource(value = {"TRACE,true", "TRACE,false", "DEBUG,true", "DEBUG,false", "INFO,true", "INFO,false", "WARN,true",
      "WARN,false", "ERROR,true", "ERROR,false", "OFF,true", "OFF,false"})
  void isEnabled(Level level, boolean expectedResult) {
    // given
    when(MockLoggingProvider.MOCK_INSTANCE.isEnabled(anyInt(), anyString(), isA(Level.class))).thenReturn(
        expectedResult);

    // when
    var actualResult = provider.isEnabled(123, "test tag", level);

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE).isEnabled(123, "test tag", level);

    assertEquals(expectedResult, actualResult);
  }

  @DisplayName("Is it enabled for a classname?")
  @ParameterizedTest
  @CsvSource(value = {"TRACE,true", "TRACE,false", "DEBUG,true", "DEBUG,false", "INFO,true", "INFO,false", "WARN,true",
      "WARN,false", "ERROR,true", "ERROR,false", "OFF,true", "OFF,false"}, nullValues = "N/A")
  void isEnabledForClassname(Level level, boolean expectedResult) {
    // given
    when(MockLoggingProvider.MOCK_INSTANCE.isEnabled(anyString(), anyString(), isA(Level.class))).thenReturn(
        expectedResult);

    // when
    var actualResult = provider.isEnabled("test.class", "test tag", level);

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE).isEnabled("test.class", "test tag", level);

    assertEquals(expectedResult, actualResult);
  }

  @DisplayName("Log")
  @Test
  void log() {
    // when
    provider.log(123, "test tag", Level.TRACE, new Throwable("test throwable"), mock(MessageFormatter.class), this, "a",
        "b", "c");

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE).log(eq(123), eq("test tag"), eq(Level.TRACE), isA(Throwable.class),
        isA(MessageFormatter.class), notNull(), eq("a"), eq("b"), eq("c"));
  }

  @DisplayName("Log for a classname")
  @Test
  void logForClassname() {
    // when
    provider.log("test.class", "test tag", Level.TRACE, new Throwable("test throwable"), mock(MessageFormatter.class),
        this, "a", "b", "c");

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE).log(eq("test.class"), eq("test tag"), eq(Level.TRACE),
        isA(Throwable.class), isA(MessageFormatter.class), notNull(), eq("a"), eq("b"), eq("c"));
  }

  @DisplayName("Shutdown")
  @Test
  void shutdown() throws InterruptedException {
    // when
    provider.shutdown();

    // then
    verify(MockLoggingProvider.MOCK_INSTANCE).shutdown();
  }

  @DisplayName("Shutdown with an exception")
  @Test
  void shutdownWithException() throws InterruptedException {
    // given
    doThrow(new InterruptedException("test")).when(MockLoggingProvider.MOCK_INSTANCE).shutdown();

    // when and then
    var exception = assertThrows(InterruptedException.class, () -> provider.shutdown());

    assertEquals("test", exception.getMessage());
  }

}
