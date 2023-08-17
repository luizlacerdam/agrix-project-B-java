package com.betrybe.agrix.exception;

/**
 * Error class.
 */
public class NotFoundError extends RuntimeException {

  /**
   * Error constructor.
   */
  public NotFoundError(String message) {
    super(message);
  }

}
