package io.github.vitalijr2.logging.mock.tinylog;

import java.lang.reflect.Field;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.tinylog.provider.LoggingProvider;

public class MockTinylogProviderExtension implements TestInstancePostProcessor, ParameterResolver {

  @Override
  public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws IllegalAccessException {
    for (Field field : testInstance.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(MockTinylogProvider.class) && field.getType()
          .isAssignableFrom(LoggingProvider.class)) {
        field.setAccessible(true);
        field.set(testInstance, MockLoggerProvider.MOCK_INSTANCE);
      }
    }
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
