package com.mattellis.robot.service;

import com.mattellis.robot.domain.RobotCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.mattellis.robot.domain.Command.MOVE;
import static com.mattellis.robot.domain.Command.PLACE;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
@Service
public class RobotSimulatorService {

    @Value("${table.size:5}")
    private int tableSize;

    @Autowired
    private Robot robot;

    /**
     * Executes the robot command specified in the RobotCommand parameter
     * Command execution is performed on the singleton application Robot
     *
     * @param robotCommand
     */
    public void executeCommand(RobotCommand robotCommand) {

        System.out.println("Robot: " + robotCommand.getCommand());

        switch (robotCommand.getCommand()) {
            case PLACE:
                robot.place(robotCommand.getXPosition(), robotCommand.getYPosition(), robotCommand.getFacing());
                break;
            case MOVE:
                robot.move();
                break;
            case LEFT:
                robot.left();
                break;
            case RIGHT:
                robot.right();
                break;
            case REPORT:
                System.out.println(robot.report());
                break;
            default:
                break;
        }
    }

    /**
     * Validates a RobotCommand according to the current Robot state:
     * - A PLACE command that would place the robot outside the table bounds is invalid
     * - A MOVE, LEFT, RIGHT and REPORT commands are invalid if the Robot has not been placed (using the PLACE command)
     * - A MOVE command that would move the robot outside the table bounds is invalid
     *
     * @param command
     * @return boolean
     */
    public boolean validCommand(RobotCommand command) {

        // Ensure PLACE command is within table bounds
        if (PLACE.equals(command.getCommand())) {
            return onTable(command.getXPosition(), command.getYPosition());
        }

        // Ensure Robot has been placed for any other command, and any potential MOVE is within the table bounds
        return robot.isPlaced()
                && (!MOVE.equals(command.getCommand()) || onTable(robot.nextXPosition(), robot.nextYPosition()));
    }

    private boolean onTable(Integer xPosition, Integer yPosition) {

        return xPosition != null && yPosition != null
                && xPosition >= 0 && xPosition < tableSize
                && yPosition >= 0 && yPosition < tableSize;
    }

}
