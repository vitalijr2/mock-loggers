package io.github.vitalijr2.logging.mock;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("slow")
@ExtendWith(MockitoExtension.class)
class MockLoggerCleanerTest {

  @Mock
  private MockLoggerCleaner cleaner;

  @DisplayName("Cleaner subscribes to keeper's notifications")
  @Test
  void cleanerSubscribesToNotifications() {
    // given
    doCallRealMethod().when(cleaner).subscribeToNotifications();

    // when
    cleaner.subscribeToNotifications();
    MockLoggerKeeper.getInstance().cleanAndReset();

    // then
    verify(cleaner).cleanAndReset();
  }

}
