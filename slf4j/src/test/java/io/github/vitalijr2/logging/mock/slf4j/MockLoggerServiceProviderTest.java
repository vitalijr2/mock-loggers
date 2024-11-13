package io.github.vitalijr2.logging.mock.slf4j;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class MockLoggerServiceProviderTest {

  private MockLoggerServiceProvider provider;

  @BeforeEach
  void setUp() {
    provider = new MockLoggerServiceProvider();
  }

  @DisplayName("Initialize")
  @Test
  void initialize() {
    // when
    provider.initialize();

    // then
    assertAll("After initialization",
        () -> assertNotNull(provider.getLoggerFactory(), "logger factory"),
        () -> assertNotNull(provider.getMarkerFactory(), "marker factory"),
        () -> assertNotNull(provider.getMDCAdapter(), "context map adapter"));
  }

}
