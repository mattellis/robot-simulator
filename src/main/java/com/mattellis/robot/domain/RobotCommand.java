package com.mattellis.robot.domain;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
public class RobotCommand {

    private Command command;
    private Integer xPosition;
    private Integer yPosition;
    private Direction facing;

    public Command getCommand() {
        return command;
    }

    public RobotCommand setCommand(Command command) {
        this.command = command;
        return this;
    }

    public Integer getXPosition() {
        return xPosition;
    }

    public RobotCommand setXPosition(Integer xPosition) {
        this.xPosition = xPosition;
        return this;
    }

    public Integer getYPosition() {
        return yPosition;
    }

    public RobotCommand setYPosition(Integer yPosition) {
        this.yPosition = yPosition;
        return this;
    }

    public Direction getFacing() {
        return facing;
    }

    public RobotCommand setFacing(Direction facing) {
        this.facing = facing;
        return this;
    }
}