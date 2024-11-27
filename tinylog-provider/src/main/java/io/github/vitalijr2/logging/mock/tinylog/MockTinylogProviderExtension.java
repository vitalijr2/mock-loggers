/*-
 * ---------------LICENSE_START-----------------
 * Mock Loggers
 * ---------------------------------------------
 * Copyright (C) 2024 Vitalij Berdinskih
 * ---------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---------------LICENSE_END-------------------
 */
package io.github.vitalijr2.logging.mock.tinylog;

import java.util.stream.Stream;
import org.jetbrains.annotations.VisibleForTesting;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.tinylog.provider.LoggingProvider;

/**
 * A JUnit extension for injecting an instance of a mock {@link LoggingProvider} instance.
 * <p>
 * This extension is used alongside an annotation. A field of type {@link LoggingProvider} marked with the
 * {@link MockTinylogProvider} annotation will be assigned a mock instance. This mock can then be used to test logging
 * behavior.
 *
 * @since 1.1.0
 */
public class MockTinylogProviderExtension implements TestInstancePostProcessor, ParameterResolver {

  @VisibleForTesting
  static void injectMockProvider(Object testInstance, Class<?> testClass) {
    Stream.of(testClass.getDeclaredFields()).filter(
        field -> field.isAnnotationPresent(MockTinylogProvider.class) && field.getType()
            .isAssignableFrom(LoggingProvider.class)).forEach(field -> {
      field.setAccessible(true);
      try {
        field.set(testInstance, MockLoggingProvider.MOCK_INSTANCE);
      } catch (IllegalAccessException | RuntimeException exception) {
        throw new MockLoggerException("Cannot inject a mock provider", exception);
      }
    });
  }

  @Override
  public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
    injectMockProvider(testInstance, testInstance.getClass());
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return MockLoggingProvider.MOCK_INSTANCE;
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return LoggingProvider.class.isAssignableFrom(parameterContext.getParameter().getType());
  }

}
