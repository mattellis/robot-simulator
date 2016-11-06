package com.mattellis.robot.error;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
public class InvalidArgumentException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidArgumentException(String message) {
        super(message);
    }

}
