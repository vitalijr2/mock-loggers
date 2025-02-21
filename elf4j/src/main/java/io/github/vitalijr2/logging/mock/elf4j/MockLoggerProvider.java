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
package io.github.vitalijr2.logging.mock.elf4j;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import elf4j.Logger;
import elf4j.spi.LogServiceProvider;
import io.github.vitalijr2.logging.mock.MockLoggerCleaner;
import java.util.List;
import org.jetbrains.annotations.VisibleForTesting;

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
 *     verify(Logger.instance()).atInfo().log("Hello World!");
 *   }
 * </code></pre>
 *
 * @since 1.2.0
 */
public class MockLoggerProvider implements LogServiceProvider, MockLoggerCleaner {

  private final Logger logger;

  /**
   * Create a logger provider with a default mock logger.
   */
  public MockLoggerProvider() {
    this(mock(Logger.class, "Mock logger"));
  }

  @VisibleForTesting
  MockLoggerProvider(Logger logger) {
    this.logger = logger;
    subscribeToNotifications();
  }

  @Override
  public List<String> cleanAndReset() {
    clearInvocations(logger);
    reset(logger);
    return List.of("default");
  }

  /**
   * Returns an instance of Logger.
   *
   * @return mock logger
   */
  @Override
  public Logger logger() {
    return logger;
  }

}
