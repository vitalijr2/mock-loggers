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
package io.github.vitalijr2.logging.keeper;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

/**
 * An observable object to notify mock logger factories to clean and reset mocks.
 *
 * @since 1.0.0
 */
public class MockLoggerKeeper {

  private static final MockLoggerKeeper INSTANCE = new MockLoggerKeeper();

  private final Set<MockLoggerCleaner> cleaners;

  @VisibleForTesting
  MockLoggerKeeper() {
    cleaners = new CopyOnWriteArraySet<>();
  }

  @NotNull
  public static MockLoggerKeeper getInstance() {
    return INSTANCE;
  }

  /**
   * Add a cleaner to the set.
   *
   * @param cleaner mock logger factory who wants to get notification
   */
  public void addCleaner(MockLoggerCleaner cleaner) {
    cleaners.add(cleaner);
  }

  /**
   * Send notifications to all cleaners.
   */
  public void cleanAndReset() {
    cleaners.forEach(MockLoggerCleaner::cleanAndReset);
  }

}
