package io.github.vitalijr2.logging.mock.tinylog;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.tinylog.core.LogEntry;
import org.tinylog.core.LogEntryValue;

@Tag("fast")
class MockWriterFastTest {

  private MockWriter writer;

  @BeforeEach
  void setUp() {
    writer = new MockWriter();
  }

  @AfterEach
  void tearDown() {
    clearInvocations(MockWriter.MOCK_INSTANCE);
    reset(MockWriter.MOCK_INSTANCE);
  }

  @DisplayName("Close")
  @Test
  void close() throws Exception {
    // when
    writer.close();

    // then
    verify(MockWriter.MOCK_INSTANCE).close();
  }

  @DisplayName("Close with an exception")
  @Test
  void closeWithException() throws Exception {
    // given
    doThrow(new Exception("test")).when(MockWriter.MOCK_INSTANCE).close();

    // when and then
    var exception = assertThrows(Exception.class, writer::close);

    assertEquals("test", exception.getMessage());
  }

  @DisplayName("Flush")
  @Test
  void flush() throws Exception {
    // when
    writer.flush();

    // then
    verify(MockWriter.MOCK_INSTANCE).flush();
  }

  @DisplayName("Flush with an exception")
  @Test
  void flushWithException() throws Exception {
    // given
    doThrow(new Exception("test")).when(MockWriter.MOCK_INSTANCE).flush();

    // when and then
    var exception = assertThrows(Exception.class, writer::flush);

    assertEquals("test", exception.getMessage());
  }

  @DisplayName("Get required log entry values")
  @Test
  void getRequiredLogEntryValues() {
    // given
    when(MockWriter.MOCK_INSTANCE.getRequiredLogEntryValues()).thenReturn(
        EnumSet.of(LogEntryValue.LEVEL, LogEntryValue.MESSAGE));

    // when
    var logEntryValues = writer.getRequiredLogEntryValues();

    // then
    verify(MockWriter.MOCK_INSTANCE).getRequiredLogEntryValues();

    assertThat(logEntryValues, containsInAnyOrder(LogEntryValue.LEVEL, LogEntryValue.MESSAGE));
  }

  @DisplayName("Write")
  @Test
  void write() throws Exception {
    // given
    var logEntry = mock(LogEntry.class);

    // when
    writer.write(logEntry);

    // then
    verify(MockWriter.MOCK_INSTANCE).write(logEntry);
  }

  @DisplayName("Write with an exception")
  @Test
  void writeWithException() throws Exception {
    // given
    var logEntry = mock(LogEntry.class);
    doThrow(new Exception("test")).when(MockWriter.MOCK_INSTANCE).write(isA(LogEntry.class));

    // when
    var exception = assertThrows(Exception.class, () -> writer.write(logEntry));

    // then
    verify(MockWriter.MOCK_INSTANCE).write(logEntry);

    assertEquals("test", exception.getMessage());
  }

}
