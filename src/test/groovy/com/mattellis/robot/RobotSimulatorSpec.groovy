package com.mattellis.robot

import com.mattellis.robot.domain.Command
import com.mattellis.robot.domain.Direction
import com.mattellis.robot.domain.RobotCommand
import com.mattellis.robot.error.InvalidArgumentException
import com.mattellis.robot.service.RobotCommandMapper
import com.mattellis.robot.service.RobotSimulatorService
import spock.lang.Specification

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
class RobotSimulatorSpec extends Specification {

    def robotCommandMapper = Mock(RobotCommandMapper)
    def robotSimulatorService = Mock(RobotSimulatorService)

    def RobotSimulator robotSimulator = new RobotSimulator(
            robotCommandMapper: robotCommandMapper,
            robotSimulatorService: robotSimulatorService
    )

    def placeCommand = new RobotCommand(command: Command.PLACE, xPosition: 0, yPosition: 0, facing: Direction.NORTH)
    def moveCommand = new RobotCommand(command: Command.MOVE, xPosition: null, yPosition: null, facing: null)
    def reportCommand = new RobotCommand(command: Command.REPORT, xPosition: null, yPosition: null, facing: null)

    def "should open a valid filename and process a list of robot commands"() {

        given: "A valid filename"
        def filename = "src/test/resources/valid-commands-a.txt"

        when: "I run the RobotSimulator with the filename"
        robotSimulator.run(filename)

        then: "The first input line is mapped, validated and executed"
        1 * robotCommandMapper.mapRobotCommand("PLACE 0,0,NORTH") >> placeCommand
        1 * robotSimulatorService.validCommand(placeCommand) >> true
        1 * robotSimulatorService.executeCommand(placeCommand)

        then: "The second input line is mapped, validated and executed"
        1 * robotCommandMapper.mapRobotCommand("MOVE") >> moveCommand
        1 * robotSimulatorService.validCommand(moveCommand) >> true
        1 * robotSimulatorService.executeCommand(moveCommand)

        then: "The third input line is mapped, validated and executed"
        1 * robotCommandMapper.mapRobotCommand("REPORT") >> reportCommand
        1 * robotSimulatorService.validCommand(reportCommand) >> true
        1 * robotSimulatorService.executeCommand(reportCommand)
    }

    def "should open a valid filename and process a list of robot commands, ignoring invalid commands"() {

        given: "A valid filename"
        def filename = "src/test/resources/partly-valid-commands.txt"

        when: "I run the RobotSimulator with the filename"
        robotSimulator.run(filename)

        then: "The first input line is invalid and not executed"
        1 * robotCommandMapper.mapRobotCommand("PLACE 10,10,NORTH") >> placeCommand
        1 * robotSimulatorService.validCommand(placeCommand) >> false
        0 * robotSimulatorService.executeCommand(placeCommand)

        then: "The second input line is invalid and not executed"
        1 * robotCommandMapper.mapRobotCommand("LEFT") >> moveCommand
        1 * robotSimulatorService.validCommand(moveCommand) >> false
        0 * robotSimulatorService.executeCommand(moveCommand)

        then: "The third input line is mapped, validated and executed"
        1 * robotCommandMapper.mapRobotCommand("PLACE 4,4,NORTH") >> moveCommand
        1 * robotSimulatorService.validCommand(moveCommand) >> true
        1 * robotSimulatorService.executeCommand(moveCommand)

        then: "The fourth input line is invalid and not executed"
        1 * robotCommandMapper.mapRobotCommand("MOVE") >> moveCommand
        1 * robotSimulatorService.validCommand(moveCommand) >> false
        0 * robotSimulatorService.executeCommand(moveCommand)

        then: "The fifth input line is mapped, validated and executed"
        1 * robotCommandMapper.mapRobotCommand("REPORT") >> reportCommand
        1 * robotSimulatorService.validCommand(reportCommand) >> true
        1 * robotSimulatorService.executeCommand(reportCommand)
    }

    def "should exit without executing any commands if there are no command line arguments"() {

        when: "I run the RobotSimulator with no arguments"
        robotSimulator.run()

        then: "No commands are executed"
        0 * robotCommandMapper.mapRobotCommand(_ as String)
        0 * robotSimulatorService.validCommand(_ as RobotCommand)
        0 * robotSimulatorService.executeCommand(_ as RobotCommand)
    }

    def "should exit without executing any commands if there are more than one command line arguments"() {

        when: "I run the RobotSimulator with two arguments"
        robotSimulator.run("example-a", "example-b")

        then: "No commands are executed"
        0 * robotCommandMapper.mapRobotCommand(_ as String)
        0 * robotSimulatorService.validCommand(_ as RobotCommand)
        0 * robotSimulatorService.executeCommand(_ as RobotCommand)
    }

    def "should exit without executing any commands if unable to open file"() {

        given: "An invalid file"
        def invalidFilename = "invalidFile"

        when: "I run the RobotSimulator with the invalid filename"
        robotSimulator.run(invalidFilename)

        then: "No commands are executed"
        0 * robotCommandMapper.mapRobotCommand(_ as String)
        0 * robotSimulatorService.validCommand(_ as RobotCommand)
        0 * robotSimulatorService.executeCommand(_ as RobotCommand)
    }

    def "should exit without executing any further commands if an InvalidArgumentException occurs"() {

        given: "A valid filename"
        def filename = "src/test/resources/invalid-input-line-b.txt"

        when: "I run the RobotSimulator with the filename"
        robotSimulator.run(filename)

        then: "The first line is mapped, validated and executed"
        1 * robotCommandMapper.mapRobotCommand("PLACE 0,0,NORTH") >> placeCommand
        1 * robotSimulatorService.validCommand(placeCommand) >> true
        1 * robotSimulatorService.executeCommand(placeCommand)

        and: "An invalid command causes an exception and no further lines are processed"
        1 * robotCommandMapper.mapRobotCommand("WRONG") >> { throw new InvalidArgumentException("Error") }
        0 * robotSimulatorService.validCommand(_ as RobotCommand)
        0 * robotSimulatorService.executeCommand(_ as RobotCommand)
        0 * robotCommandMapper.mapRobotCommand(_ as String)
    }
}
