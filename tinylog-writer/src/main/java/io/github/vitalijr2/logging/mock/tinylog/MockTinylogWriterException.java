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

/**
 * This exception is thrown when {@link MockTinylogWriterExtension} annot inject a mock {@link MockWriter} instance.
 *
 * @since 1.1.0
 */
public class MockTinylogWriterException extends RuntimeException {

  /**
   * Common exception.
   *
   * @param message message
   * @param cause   cause
   */
  public MockTinylogWriterException(String message, Throwable cause) {
    super(message, cause);
  }

}
