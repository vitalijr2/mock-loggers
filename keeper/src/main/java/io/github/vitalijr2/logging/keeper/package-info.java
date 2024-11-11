/**
 * This implements the observer pattern: cleaners subscribe to notifications from a keeper, who sends alerts when they
 * need to clean and reset mock loggers. Logger factories should implement the cleaner interface and register themselves
 * with the keeper.
 */
package io.github.vitalijr2.logging.keeper;