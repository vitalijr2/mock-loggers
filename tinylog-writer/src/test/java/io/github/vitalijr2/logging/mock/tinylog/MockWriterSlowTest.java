package io.github.vitalijr2.logging.mock.tinylog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tinylog.Logger;
import org.tinylog.core.LogEntry;

@Tag("slow")
@ExtendWith(MockitoExtension.class)
class MockWriterSlowTest {

  @Captor
  private ArgumentCaptor<LogEntry> logEntryCaptor;

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

  @DisplayName("Clear and reset the mock instance")
  @Test
  void cleanAndReset() {
    // when
    Logger.info("test message");
    writer.cleanAndReset();

    // then
    verifyNoInteractions(MockWriter.MOCK_INSTANCE);
  }

  @DisplayName("Service provider")
  @Test
  void serviceProvider() throws Exception {
    // when
    Logger.info("test message");

    // then
    verify(MockWriter.MOCK_INSTANCE).write(logEntryCaptor.capture());

    assertEquals("test message", logEntryCaptor.getValue().getMessage());
  }

}
