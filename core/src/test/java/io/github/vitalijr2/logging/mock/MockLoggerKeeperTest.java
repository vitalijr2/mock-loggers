package io.github.vitalijr2.logging.mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
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

    when(first.cleanAndReset()).thenReturn(List.of("a", "b", "c"));
    when(second.cleanAndReset()).thenReturn(List.of("x", "y", "z"));

    // when
    var loggerNames = assertDoesNotThrow(() -> {
      keeper.addCleaner(first);
      keeper.addCleaner(second);

      return keeper.cleanAndReset();
    });

    // then
    verify(first).cleanAndReset();
    verify(second).cleanAndReset();
    verifyNoMoreInteractions(first);
    verifyNoMoreInteractions(second);

    assertThat(loggerNames, contains("a", "b", "c", "x", "y", "z"));
  }

}