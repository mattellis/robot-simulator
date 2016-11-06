package com.mattellis.robot.service

import com.mattellis.robot.domain.Command
import com.mattellis.robot.domain.Direction
import com.mattellis.robot.domain.RobotCommand
import spock.lang.Specification

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
class RobotSimulatorServiceSpec extends Specification {

    def tableSize = 5
    def robot = Mock(Robot)
    def robotSimulatorService = new RobotSimulatorService(tableSize: tableSize, robot: robot)

    def "should execute a PLACE RobotCommand on the Robot"() {

        given: "A PLACE command"
        def command = new RobotCommand(command: Command.PLACE, xPosition: x, yPosition: y, facing: f)

        when: "I call executeCommand on a PLACE RobotCommand"
        robotSimulatorService.executeCommand(command)

        then: "A PLACE command is executed on the Robot"
        1 * robot.place(x, y, f)

        where: "The corresponding PLACE parameters are passed"
        x    | y    | f
        0    | 0    | Direction.NORTH
        1    | 4    | Direction.SOUTH
        -1   | 5    | Direction.EAST
        100  | 100  | Direction.WEST
        null | null | null
    }

    def "should execute a MOVE RobotCommand on the Robot"() {

        given: "A MOVE command"
        def command = new RobotCommand(command: Command.MOVE)

        when: "I call executeCommand on a MOVE RobotCommand"
        robotSimulatorService.executeCommand(command)

        then: "A MOVE command is executed on the Robot"
        1 * robot.move()
    }

    def "should execute a LEFT RobotCommand on the Robot"() {

        given: "A LEFT command"
        def command = new RobotCommand(command: Command.LEFT)

        when: "I call executeCommand on a LEFT RobotCommand"
        robotSimulatorService.executeCommand(command)

        then: "A LEFT command is executed on the Robot"
        1 * robot.left()
    }

    def "should execute a RIGHT RobotCommand on the Robot"() {

        given: "A RIGHT command"
        def command = new RobotCommand(command: Command.RIGHT)

        when: "I call executeCommand on a RIGHT RobotCommand"
        robotSimulatorService.executeCommand(command)

        then: "A RIGHT command is executed on the Robot"
        1 * robot.right()
    }

    def "should execute a REPORT RobotCommand on the Robot"() {

        given: "A REPORT command"
        def command = new RobotCommand(command: Command.REPORT)

        when: "I call executeCommand on a REPORT RobotCommand"
        robotSimulatorService.executeCommand(command)

        then: "A REPORT command is executed on the Robot"
        1 * robot.report()
    }

    def "should validate a RobotCommand according to Robot state and table bounds"() {

        when: "I validate a RobotCommand"
        robotSimulatorService.validCommand(new RobotCommand(command: command, xPosition: x, yPosition: y, facing: f))

        then: "The command is validated as true or false"
        expectValid

        where: "The command is validated against robot state and table bounds"
        command        | x    | y    | f               | expectValid
        Command.PLACE  | 0    | 0    | Direction.NORTH | true
        Command.MOVE   | null | null | null            | true
        Command.LEFT   | null | null | null            | true
        Command.RIGHT  | null | null | null            | true
        Command.REPORT | null | null | null            | true
    }
}
