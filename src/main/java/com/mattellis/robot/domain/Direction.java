package com.mattellis.robot.domain;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
public enum Direction {

    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0);

    private final int xMove;
    private final int yMove;

    Direction(int xMove, int yMove) {
        this.xMove = xMove;
        this.yMove = yMove;
    }

    public int getxMove() {
        return xMove;
    }

    public int getyMove() {
        return yMove;
    }

    public static Optional<Direction> getForString(String directionString) {
        return Stream.of(values())
                .filter(direction -> direction.name().equals(directionString))
                .findFirst();
    }
}
