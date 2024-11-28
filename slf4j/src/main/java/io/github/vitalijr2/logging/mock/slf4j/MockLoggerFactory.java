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
package io.github.vitalijr2.logging.mock.slf4j;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import io.github.vitalijr2.logging.mock.MockLoggerCleaner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.VisibleForTesting;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Uses {@link org.mockito.Mockito#mock(Class, String)} to get a mock that is adapted for {@link Logger}.
 * <p>
 * Example:
 * <pre><code class="language-java">
 *   {@literal @}Test
 *   void helloWorld() {
 *     var helloService = new HelloService();
 *
 *     assertDoesNotThrow(helloService::sayHelloWorld);
 *
 *     verify(LoggerFactory.getLogger("HelloService")).log(Level.INFO, "Hello World!");
 *   }
 * </code></pre>
 *
 * @since 1.0.0
 */
public class MockLoggerFactory implements ILoggerFactory, MockLoggerCleaner {

  private final Map<String, Logger> loggers;

  /**
   * Create a map-based logger finder. The finder uses a concurrent map: a logger name is a key.
   */
  public MockLoggerFactory() {
    this(new ConcurrentHashMap<>());
  }

  @VisibleForTesting
  public MockLoggerFactory(Map<String, Logger> loggers) {
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

  /**
   * Returns an instance of Logger for the given name.
   *
   * @param name logging name
   * @return mock logger
   */
  @Override
  public Logger getLogger(String name) {
    return loggers.computeIfAbsent(name, key -> mock(Logger.class, "Mock for logger " + key));
  }

}
