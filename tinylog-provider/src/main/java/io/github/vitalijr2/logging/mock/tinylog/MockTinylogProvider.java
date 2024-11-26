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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.tinylog.provider.LoggingProvider;

/**
 * An annotation for injecting an instance of a mock {@link LoggingProvider} instance.
 * <p>
 * A field of type {@link LoggingProvider} marked with the {@link MockTinylogProvider} annotation will be assigned a
 * mock instance. This mock can then be used to test logging behavior.
 * <p>
 * Example:
 * <pre><code class="language-java">
 *   {@literal @}Test
 *   void helloWorld() {
 *     when(logger.getMinimumLevel(isNull())).thenReturn(Level.INFO);
 *
 *     assertDoesNotThrow(helloService::sayHelloWorld);
 *
 *     verify(logger).log(eq(Level.INFO.ordinal()), isNull(), eq(Level.INFO), isNull(), isNull(), anyString(), isNull());
 *   }
 * </code></pre>
 *
 * @since 1.1.0
 */
@ExtendWith(MockTinylogProviderExtension.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MockTinylogProvider {

}
