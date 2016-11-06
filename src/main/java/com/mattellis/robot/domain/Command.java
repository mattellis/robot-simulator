package com.mattellis.robot.domain;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
public enum Command {

    PLACE, MOVE, LEFT, RIGHT, REPORT;

    public static Optional<Command> getForString(String commandString) {
        return Stream.of(values())
                .filter(command -> command.name().equals(commandString))
                .findFirst();
    }
}
