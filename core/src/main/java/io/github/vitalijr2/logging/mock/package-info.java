/**
 * The observer pattern is implemented here: cleaners subscribe to notifications from a keeper, who sends alerts when
 * mock loggers need to be cleaned and reset. Logger factories should implement the cleaner interface and register
 * themselves with the keeper. The jUnit extension manages the keeper and sends alerts before and after tests.
 */
package io.github.vitalijr2.logging.mock;