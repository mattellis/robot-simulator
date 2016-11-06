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

    /**
     * PLACE the toy robot on the table in position X, Y and facing NORTH, SOUTH, EAST or WEST.
     *
     * @param xPosition
     * @param yPosition
     * @param facing
     */
    public void place(Integer xPosition, Integer yPosition, Direction facing) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.facing = facing;
    }

    /**
     * Check if the robot has been placed on the table
     *
     * @return boolean
     */
    public boolean isPlaced() {
        return xPosition != null && yPosition != null && facing != null;
    }

    /**
     * MOVE the robot one unit forward in the direction it is currently facing
     */
    public void move() {
        xPosition = nextXPosition();
        yPosition = nextYPosition();
    }

    /**
     * Check the next X position if a MOVE command were executed from the current position and direction
     *
     * @return Integer
     */
    public Integer nextXPosition() {
        return xPosition + facing.getxMove();
    }

    /**
     * Check the next X position if a MOVE command were executed from the current position and direction
     *
     * @return Integer
     */
    public Integer nextYPosition() {
        return yPosition + facing.getyMove();
    }

    /**
     * Rotate the robot 90 degrees LEFT (anti-clockwise) without changing the position of the robot.
     */
    public void left() {
        facing = DIRECTIONS[(facing.ordinal() + DIRECTIONS.length - 1) % DIRECTIONS.length];
    }

    /**
     * Rotate the robot 90 degrees RIGHT (clockwise) without changing the position of the robot.
     */
    public void right() {
        facing = DIRECTIONS[(facing.ordinal() + 1) % DIRECTIONS.length];
    }

    /**
     * REPORT the X, Y and F of the robot as String output
     *
     * @return String
     */
    public String report() {
        return xPosition + "," + yPosition + "," + facing.name();
    }
}
