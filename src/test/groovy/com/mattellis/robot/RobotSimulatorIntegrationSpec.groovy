package com.mattellis.robot

import com.mattellis.robot.service.Robot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
@ContextConfiguration
@SpringBootTest
class RobotSimulatorIntegrationSpec extends Specification {

    @Autowired
    def RobotSimulator robotSimulatorRunner

    @Autowired
    def Robot robot

    @DirtiesContext
    def "should execute valid commands, and produce the correct robot output"() {

        when: "I run the robot simulator with a test file"
        robotSimulatorRunner.run(filename)

        then: "I expect the robot to end in position according to valid commands"
        robot.isPlaced()
        robot.report() == expectedReport

        where: "Valid PLACE, MOVE, LEFT, RIGHT and REPORT commands are executed"
        filename                                  | expectedReport
        'src/test/resources/valid-commands-a.txt' | "0,1,NORTH"
        'src/test/resources/valid-commands-c.txt' | "3,3,NORTH"
        'src/test/resources/valid-commands-b.txt' | "0,0,WEST"
    }

    @DirtiesContext
    def "should not execute commands other the PLACE before the robot is placed"() {

        when: "I run the robot simulator with a test file"
        robotSimulatorRunner.run(filename)

        then: "I expect the robot to never be placed on the table"
        !robot.isPlaced()

        where: "MOVE, LEFT, RIGHT and REPORTS commands are issued before PLACE"
        filename << ['src/test/resources/invalid-commands-a.txt']
    }

    @DirtiesContext
    def "should not execute PLACE commands for a position outside the table bounds"() {

        when: "I run the robot simulator with a test file"
        robotSimulatorRunner.run(filename)

        then: "I expect the robot to never be placed on the table"
        !robot.isPlaced()

        where: "PLACE commands are issued for positions outside of the table bounds"
        filename << ['src/test/resources/invalid-commands-b.txt']
    }

    @DirtiesContext
    def "should not execute invalid move commands at the table edges"() {

        when: "I run the robot simulator with a test file"
        robotSimulatorRunner.run(filename)

        then: "I expect the robot to end in starting position after invalid MOVE commands"
        robot.isPlaced()
        robot.report() == expectedReport

        where: "MOVE commands taking the robot off the table are not executed"
        filename                                 | expectedReport
        'src/test/resources/invalid-moves-a.txt' | "0,0,WEST"
        'src/test/resources/invalid-moves-b.txt' | "0,4,WEST"
        'src/test/resources/invalid-moves-c.txt' | "4,0,EAST"
        'src/test/resources/invalid-moves-d.txt' | "4,4,EAST"
        'src/test/resources/invalid-moves-e.txt' | "0,0,NORTH"
    }

    @DirtiesContext
    def "should load files with un-parsable commands, and throw an exception"() {

        when: "I run the robot simulator with a test file"
        robotSimulatorRunner.run(filename)

        then: "I expect the robot to not be placed"
        !robot.isPlaced()

        where: "Invalid command arguments are encountered"
        filename << ['src/test/resources/invalid-input-line-a.txt', 'src/test/resources/invalid-input-line-c.txt',
                     'src/test/resources/invalid-input-line-d.txt', 'src/test/resources/invalid-input-line-e.txt',
                     'src/test/resources/invalid-input-line-f.txt', 'src/test/resources/invalid-input-line-g.txt',
                     'src/test/resources/invalid-input-line-h.txt', 'src/test/resources/invalid-input-line-i.txt',
                     'src/test/resources/invalid-input-line-j.txt']
    }

    @DirtiesContext
    def "should throw an exception if an empty file is loaded"() {

        when: "I run the robot simulator with a test file"
        robotSimulatorRunner.run(filename)

        then: "I expect the robot to not be placed"
        !robot.isPlaced()

        where: "Invalid command arguments are encountered"
        filename << ['src/test/resources/example-0.txt']
    }
}
