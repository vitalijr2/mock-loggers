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

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import io.github.vitalijr2.logging.mock.MockLoggerCleaner;
import java.util.List;
import org.tinylog.Level;
import org.tinylog.format.MessageFormatter;
import org.tinylog.provider.ContextProvider;
import org.tinylog.provider.LoggingProvider;

/**
 * Wraps a mock logger provider.
 * <p>
 * Use {@link MockTinylogProvider} to get access to a mock {@link LoggingProvider} instance.
 *
 * @since 1.1.0
 */
public class MockLoggingProvider implements LoggingProvider, MockLoggerCleaner {

  static final LoggingProvider MOCK_INSTANCE;

  static {
    MOCK_INSTANCE = mock(LoggingProvider.class);
    prepareMockInstance();
  }

  public MockLoggingProvider() {
    subscribeToNotifications();
  }

  private static void prepareMockInstance() {
    clearInvocations(MOCK_INSTANCE);
    reset(MOCK_INSTANCE);
  }

  @Override
  public List<String> cleanAndReset() {
    prepareMockInstance();
    return List.of("tinylog");
  }

  @Override
  public ContextProvider getContextProvider() {
    return MOCK_INSTANCE.getContextProvider();
  }

  @Override
  public Level getMinimumLevel() {
    return MOCK_INSTANCE.getMinimumLevel();
  }

  @Override
  public Level getMinimumLevel(String tag) {
    return MOCK_INSTANCE.getMinimumLevel(tag);
  }

  @Override
  public boolean isEnabled(int depth, String tag, Level level) {
    return MOCK_INSTANCE.isEnabled(depth, tag, level);
  }

  @Override
  public boolean isEnabled(String loggerClassName, String tag, Level level) {
    return MOCK_INSTANCE.isEnabled(loggerClassName, tag, level);
  }

  @Override
  public void log(int depth, String tag, Level level, Throwable exception, MessageFormatter formatter, Object obj,
      Object... arguments) {
    MOCK_INSTANCE.log(depth, tag, level, exception, formatter, obj, arguments);
  }

  @Override
  public void log(String loggerClassName, String tag, Level level, Throwable exception, MessageFormatter formatter,
      Object obj, Object... arguments) {
    MOCK_INSTANCE.log(loggerClassName, tag, level, exception, formatter, obj, arguments);
  }

  @Override
  public void shutdown() throws InterruptedException {
    MOCK_INSTANCE.shutdown();
  }

}
