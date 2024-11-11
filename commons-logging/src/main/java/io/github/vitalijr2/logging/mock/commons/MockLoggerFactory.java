/*
 * Copyright 2024 Vitalij Berdinskih
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.vitalijr2.logging.mock.commons;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import io.github.vitalijr2.logging.mock.MockLoggerCleaner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.VisibleForTesting;

/**
 * Uses {@link org.mockito.Mockito#mock(Class, String)} to get a mock that is adapted for {@link System.Logger}.
 * <p>
 * Example:
 * <pre><code class="language-java">
 *   {@literal @}Test
 *   void helloWorld() {
 *     var helloService = new HelloService();
 *
 *     assertDoesNotThrow(helloService::sayHelloWorld);
 *
 *     verify(LogFactory.getLog(helloService.getClass())).info("Hello World!");
 *   }
 * </code></pre>
 *
 * @since 1.0.0
 */
public class MockLoggerFactory extends LogFactory implements MockLoggerCleaner {

  private final Map<String, Log> loggers;

  /**
   * Create a map-based logger finder. The finder uses a concurrent map: a logger name is a key.
   */
  public MockLoggerFactory() {
    this(new ConcurrentHashMap<>());
  }

  @VisibleForTesting
  MockLoggerFactory(Map<String, Log> loggers) {
    this.loggers = loggers;
    subscribeToNotifications();
  }

  @Override
  public List<String> cleanAndReset() {
    var processedLoggers = new ArrayList<String>();

    loggers.forEach((loggerName, logger) -> {
      clearInvocations(logger);
      reset(logger);
      processedLoggers.add(loggerName);
    });

    return processedLoggers;
  }

  @Override
  public Object getAttribute(String name) {
    return null;
  }

  @Override
  public String[] getAttributeNames() {
    return new String[0];
  }

  @Override
  public Log getInstance(Class<?> clazz) throws LogConfigurationException {
    return getInstance(clazz.getName());
  }

  /**
   * Returns an instance of Logger for the given name.
   *
   * @param name logging name
   * @return mock logger
   * @throws LogConfigurationException this is never thrown
   */
  @Override
  public Log getInstance(String name) throws LogConfigurationException {
    return loggers.computeIfAbsent(name, key -> mock(Log.class, "Mock for logger " + key));
  }

  @Override
  public void release() {
    loggers.clear();
  }

  @Override
  public void removeAttribute(String s) {
    // do nothing
  }

  @Override
  public void setAttribute(String s, Object o) {
    // do nothing
  }

}
