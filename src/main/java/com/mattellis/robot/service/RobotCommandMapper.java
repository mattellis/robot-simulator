package com.mattellis.robot.service;

import com.mattellis.robot.domain.Command;
import com.mattellis.robot.domain.Direction;
import com.mattellis.robot.domain.RobotCommand;
import com.mattellis.robot.error.InvalidArgumentException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
@Component
public class RobotCommandMapper {

    private static final String INVALID_COMMAND_MSG = "Invalid command specified";
    private static final String INVALID_PLACE_OPTIONS_MSG = "Invalid PLACE command options, " +
            "please specify exactly 3 options in the following format: PLACE X,Y,F";
    private static final String INVALID_NUMBER_FORMAT_MSG = "Invalid number format for PLACE X,Y,F command options";
    private static final String INVALID_FACING_OPTION_MSG = "Invalid Facing option for PLACE X,Y,F command";

    /**
     * Maps a String input line into a RobotCommand
     * Throws an InvalidArgumentException if unable to parse any argument
     *
     * @param inputLine
     * @return RobotCommand
     */
    public RobotCommand mapRobotCommand(String inputLine) {

        String[] commandArgs = parseCommandArgs(inputLine);

        // Map robot command from tokenised input line
        RobotCommand robotCommand = new RobotCommand();
        robotCommand.setCommand(parseCommand(commandArgs[0]));

        // Map arguments specific to PLACE command
        if (Command.PLACE.equals(robotCommand.getCommand())) {
            if (commandArgs.length != 4) {
                throw new InvalidArgumentException(INVALID_PLACE_OPTIONS_MSG);
            }

            robotCommand.setXPosition(parseInteger(commandArgs[1]));
            robotCommand.setYPosition(parseInteger(commandArgs[2]));
            robotCommand.setFacing(parseFacing(commandArgs[3]));
        }

        return robotCommand;
    }

    private String[] parseCommandArgs(String commandLine) {

        String[] commandArgs = StringUtils.tokenizeToStringArray(commandLine, ", ");
        if (commandArgs == null || commandArgs.length == 0) {
            throw new InvalidArgumentException(INVALID_COMMAND_MSG);
        }
        return commandArgs;
    }

    private Command parseCommand(String commandArg) {

        return Command.getForString(commandArg)
                .orElseThrow(() -> new InvalidArgumentException(INVALID_COMMAND_MSG));
    }

    private Direction parseFacing(String directionArg) {

        return Direction.getForString(directionArg)
                .orElseThrow(() -> new InvalidArgumentException(INVALID_FACING_OPTION_MSG));
    }

    private Integer parseInteger(String integerArg) {

        try {
            return Integer.valueOf(integerArg);
        } catch (NumberFormatException nfe) {
            throw new InvalidArgumentException(INVALID_NUMBER_FORMAT_MSG);
        }
    }
}
