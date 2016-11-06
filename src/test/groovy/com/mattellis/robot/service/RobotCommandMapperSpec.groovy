package com.mattellis.robot.service

import com.mattellis.robot.domain.Command
import com.mattellis.robot.domain.Direction
import com.mattellis.robot.error.InvalidArgumentException
import spock.lang.Specification

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
class RobotCommandMapperSpec extends Specification {

    def reportCommandMapper = new RobotCommandMapper()

    def "should map a valid input line to a RobotCommand"() {

        when: "I map an input line to a RobotCommand"
        def result = reportCommandMapper.mapRobotCommand(validInputLine)

        then: "The result is a valid RobotCommand"
        result.command == expectedCommand
        result.XPosition == expectedX
        result.YPosition == expectedY
        result.facing == expectedFacing

        where: "Valid input lines"
        validInputLine       | expectedCommand | expectedX | expectedY | expectedFacing
        "PLACE 0,0,NORTH"    | Command.PLACE   | 0         | 0         | Direction.NORTH
        "PLACE 1,4,SOUTH"    | Command.PLACE   | 1         | 4         | Direction.SOUTH
        "PLACE -1,5,EAST"    | Command.PLACE   | -1        | 5         | Direction.EAST
        "PLACE 100,100,WEST" | Command.PLACE   | 100       | 100       | Direction.WEST
        "MOVE"               | Command.MOVE    | null      | null      | null
        "LEFT"               | Command.LEFT    | null      | null      | null
        "RIGHT"              | Command.RIGHT   | null      | null      | null
        "REPORT"             | Command.REPORT  | null      | null      | null
    }

    def "should throw an InvalidArgumentException for an invalid input line"() {

        when: "I map an input line to a RobotCommand"
        def result = reportCommandMapper.mapRobotCommand(invalidInputLine)

        then: "The result is null and an InvalidArgumentException is thrown"
        result == null
        thrown(InvalidArgumentException)

        where: "Valid input lines"
        invalidInputLine << [null, "", "WRONG", "PLACE 0,0", "PLACE 0,,NORTH", "PLACE 0,,NORTH", "0,0,WRONG",
                             "PLACE 0,0,", "PLACE ZERO,0,NORTH", "PLACE 0,ZERO,NORTH", "PLACE 0.00,000,NORTH",
                             "PLACE 0,0,NORTH,EXTRA"]
    }
}
