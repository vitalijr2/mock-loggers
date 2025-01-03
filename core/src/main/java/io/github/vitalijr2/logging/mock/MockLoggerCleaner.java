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
package io.github.vitalijr2.logging.mock;

import java.util.List;

/**
 * An observer to listen {@linkplain MockLoggerKeeper keeper} when to clean and reset mocks. Mock logger factories
 * should implement this.
 *
 * @since 1.0.0
 */
public interface MockLoggerCleaner {

  /**
   * Receive notification to clean and reset mock loggers.
   *
   * @return list of logger names that were cleaned and reset
   */
  List<String> cleanAndReset();

  /**
   * Register itself with a keeper.
   */
  default void subscribeToNotifications() {
    MockLoggerKeeper.getInstance().addCleaner(this);
  }

}
