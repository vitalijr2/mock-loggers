package io.github.vitalijr2.logging.mock.elf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;

import elf4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("fast")
@ExtendWith(MockitoExtension.class)
class MockLoggerProviderFastTest {

  @Mock
  private Logger logger;

  @DisplayName("Create provider")
  @Test
  void createAndInitializeProvider() {
    // given
    var provider = new MockLoggerProvider(logger);

    // when and then
    assertEquals(logger, provider.logger());
  }

  @DisplayName("Clean and reset the logger")
  @Test
  void cleanAndResetTheLogger() {
    // given
    var provider = new MockLoggerProvider(logger);

    provider.logger().log("Test message");

    // when
    var loggerNames = provider.cleanAndReset();

    // then
    assertThat(loggerNames, contains("default"));
    verifyNoInteractions(logger);
  }

}
