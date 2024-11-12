package io.github.vitalijr2.logging.mock.slf4j;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * Responsible for binding the {@link MockLoggerFactory}, the {@link BasicMarkerFactory} and the {@link NOPMDCAdapter}.
 * This is used by the SLF4J API.
 *
 * @since 1.0.0
 */
public class MockLoggerServiceProvider implements SLF4JServiceProvider {

  private static final ILoggerFactory loggerFactory = new MockLoggerFactory();
  private static final IMarkerFactory markerFactory = new BasicMarkerFactory();
  private static final MDCAdapter contextMapAdapter = new NOPMDCAdapter();

  /**
   * Declare the version of the SLF4J API this implementation is compiled against. The value of this field is modified
   * with each major release.
   */
  // to avoid constant folding by the compiler, this field must *not* be final
  public static String REQUESTED_API_VERSION = "2.0.99"; // !final

  @Override
  public ILoggerFactory getLoggerFactory() {
    return loggerFactory;
  }

  @Override
  public IMarkerFactory getMarkerFactory() {
    return markerFactory;
  }

  @Override
  public MDCAdapter getMDCAdapter() {
    return contextMapAdapter;
  }

  @Override
  public String getRequestedApiVersion() {
    return REQUESTED_API_VERSION;
  }

  @Override
  public void initialize() {
    // initialized by static fields
  }

}
