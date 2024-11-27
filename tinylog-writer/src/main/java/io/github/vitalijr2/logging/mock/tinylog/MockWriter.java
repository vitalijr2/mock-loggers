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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.VisibleForTesting;
import org.tinylog.core.LogEntry;
import org.tinylog.core.LogEntryValue;
import org.tinylog.writers.Writer;

/**
 * Wraps a mock logging writer.
 * <p>
 * Use {@link MockWriter} to get access to a mock {@link Writer} instance.
 *
 * @since 1.1.0
 */
public class MockWriter implements Writer, MockLoggerCleaner {

  static final Writer MOCK_INSTANCE;

  static {
    MOCK_INSTANCE = mock(Writer.class);
    prepareMockInstance();
  }

  @VisibleForTesting
  MockWriter() {
  }

  @SuppressWarnings("PMD.UnusedFormalParameter")
  public MockWriter(Map<String, String> properties) {
    // do nothing
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
  public void close() throws Exception {
    MOCK_INSTANCE.close();
  }

  @Override
  public void flush() throws Exception {
    MOCK_INSTANCE.flush();
  }

  @Override
  public Collection<LogEntryValue> getRequiredLogEntryValues() {
    return MOCK_INSTANCE.getRequiredLogEntryValues();
  }

  @Override
  public void write(LogEntry logEntry) throws Exception {
    MOCK_INSTANCE.write(logEntry);
  }

}
