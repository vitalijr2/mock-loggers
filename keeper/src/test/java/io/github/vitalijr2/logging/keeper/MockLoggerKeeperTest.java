package io.github.vitalijr2.logging.keeper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("fast")
@ExtendWith(MockitoExtension.class)
class MockLoggerKeeperTest {

  @Mock
  private MockLoggerCleaner first;
  @Mock
  private MockLoggerCleaner second;

  @DisplayName("Instance")
  @Test
  void instance() {
    // when and them
    assertNotNull(MockLoggerKeeper.getInstance());
  }

  @DisplayName("Happy path")
  @Test
  void happyPath() {
    // given
    var keeper = new MockLoggerKeeper();

    // when
    assertDoesNotThrow(() -> {
      keeper.addCleaner(first);
      keeper.addCleaner(second);
      keeper.cleanAndReset();
    });

    // then
    verify(first).cleanAndReset();
    verify(second).cleanAndReset();
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);
  }

}