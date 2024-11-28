package io.github.vitalijr2.logging.mock.tinylog;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.tinylog.writers.Writer;

@Tag("slow")
class MockTinylogWriterExtensionSlowTest {

  @Mock
  private ExtensionContext extensionContext;

  private MockTinylogWriterExtension extension;

  @BeforeEach
  void setUp() {
    extension = new MockTinylogWriterExtension();
  }

  @DisplayName("Happy path")
  @Test
  void happyPath() {
    // given
    var testInstance = new HappyTest();

    // when
    extension.postProcessTestInstance(testInstance, extensionContext);

    // then
    assertAll("Mock is injected", () -> assertNotNull(testInstance.writer),
        () -> assertThat(testInstance.writer, instanceOf(Writer.class)));
  }

  @DisplayName("Throw an exception on injection")
  @Test
  void throwExceptionOnInjection() {
    // when
    var exception = assertThrows(MockTinylogWriterException.class,
        () -> MockTinylogWriterExtension.injectMockWriter(null, HappyTest.class));

    // then
    assertEquals("Cannot inject a mock writer", exception.getMessage());
  }

  @DisplayName("Unannotated field")
  @Test
  void unannotatedField() {
    // given
    var testInstance = new UnannotatedFieldTest();

    // when
    extension.postProcessTestInstance(testInstance, extensionContext);

    // then
    assertNull(testInstance.writer);
  }

  @DisplayName("Wrong type")
  @Test
  void wrongType() {
    // given
    var testInstance = new WrongTypeTest();

    // when
    extension.postProcessTestInstance(testInstance, extensionContext);

    // then
    assertNull(testInstance.writer);
  }

  static class HappyTest {

    @SuppressWarnings("unused")
    @MockTinylogWriter
    private Writer writer;

  }

  static class UnannotatedFieldTest {

    @SuppressWarnings("unused")
    private Writer writer;

  }

  static class WrongTypeTest {

    @SuppressWarnings("unused")
    @MockTinylogWriter
    private String writer;

  }

}
