package io.github.vitalijr2.logging.mock.tinylog;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.tinylog.provider.LoggingProvider;

public class MockTinylogProviderExtension implements TestInstancePostProcessor, ParameterResolver {

  @Override
  public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
    Stream.of(testInstance.getClass().getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(MockTinylogProvider.class) && field.getType()
            .isAssignableFrom(LoggingProvider.class)).forEach(field -> {
          field.setAccessible(true);
          try {
            field.set(testInstance, MockLoggerProvider.MOCK_INSTANCE);
          } catch (IllegalAccessException exception) {
            throw new RuntimeException(exception);
          }
        });
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return MockLoggerProvider.MOCK_INSTANCE;
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return LoggingProvider.class.isAssignableFrom(parameterContext.getParameter().getType());
  }

}
