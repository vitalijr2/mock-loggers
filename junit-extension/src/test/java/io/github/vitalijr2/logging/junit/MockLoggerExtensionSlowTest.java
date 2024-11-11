package io.github.vitalijr2.logging.junit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("slow")
class MockLoggerExtensionSlowTest {

  @DisplayName("Initialize a logger keeper on \"before all\" step")
  @Test
  void initLoggerFinderOnBeforeAll() {
    // when and then
    assertDoesNotThrow(() -> new MockLoggerExtension());
  }

}
