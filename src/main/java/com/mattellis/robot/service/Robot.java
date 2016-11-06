package com.mattellis.robot.service;

import com.mattellis.robot.domain.Direction;
import org.springframework.stereotype.Component;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
@Component
public class Robot {

    private final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private Integer xPosition;
    private Integer yPosition;
    private Direction facing;

    public void place(Integer xPosition, Integer yPosition, Direction facing) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.facing = facing;
    }

    public boolean isPlaced() {
        return xPosition != null && yPosition != null && facing != null;
    }

    public void move() {
        xPosition = nextXPosition();
        yPosition = nextYPosition();
    }

    public Integer nextXPosition() {
        return xPosition + facing.getxMove();
    }

    public Integer nextYPosition() {
        return yPosition + facing.getyMove();
    }

    public void left() {
        facing = DIRECTIONS[(facing.ordinal() + DIRECTIONS.length - 1) % DIRECTIONS.length];
    }

    public void right() {
        facing = DIRECTIONS[(facing.ordinal() + 1) % DIRECTIONS.length];
    }

    public String report() {
        return xPosition + "," + yPosition + "," + facing.name();
    }
}
