package com.mattellis.robot.service

import com.mattellis.robot.domain.Direction
import spock.lang.Specification

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
class RobotSpec extends Specification {

    def robot = new Robot()

    def "should place the robot"() {

        when: "I place the robot"
        robot.place(x, y, facing)

        then: "The robot position is set accordingly"
        robot.report() == expectedReport

        where: "Various place parameters"
        x | y | facing          | expectedReport
        0 | 1 | Direction.NORTH | "0,1,NORTH"
        1 | 2 | Direction.SOUTH | "1,2,SOUTH"
        2 | 3 | Direction.EAST  | "2,3,EAST"
        3 | 4 | Direction.WEST  | "3,4,WEST"
    }

    def "should return the placement status of the Robot"() {

        given: "the robot with an existing position"
        robot.place(x, y, facing)

        when: "I check if the robot is placed"
        def result = robot.isPlaced()

        then: "The placement status is returned"
        result == expectedPlaced

        where: "Position parameters determine if robot is placed"
        x    | y    | facing          | expectedPlaced
        0    | 1    | Direction.NORTH | true
        1    | 0    | Direction.SOUTH | true
        null | 1    | Direction.EAST  | false
        null | null | Direction.WEST  | false
        null | null | null            | false
    }

    def "should move the robot"() {

        given: "the robot with an existing position"
        robot.place(x, y, facing)

        when: "I move the robot"
        robot.move()

        then: "The robot is moved in the direction it is facing"
        robot.report() == expectedReport

        where: "New X and X positions are set according to direction"
        x | y | facing          | expectedReport
        0 | 1 | Direction.NORTH | "0,2,NORTH"
        1 | 2 | Direction.SOUTH | "1,1,SOUTH"
        2 | 3 | Direction.EAST  | "3,3,EAST"
        3 | 4 | Direction.WEST  | "2,4,WEST"
    }

    def "should check the next X Position if a move were executed"() {

        given: "the robot with an existing position"
        robot.place(x, y, facing)

        when: "I check the next X position of the robot"
        def result = robot.nextXPosition()

        then: "The next X position is returned"
        result == expectedNextX

        where: "Next X position is returned according to direction"
        x | y | facing          | expectedNextX
        0 | 1 | Direction.NORTH | 0
        1 | 2 | Direction.SOUTH | 1
        2 | 3 | Direction.EAST  | 3
        3 | 4 | Direction.WEST  | 2
    }

    def "should check the next Y Position if a move were executed"() {

        given: "the robot with an existing position"
        robot.place(x, y, facing)

        when: "I check the next Y position of the robot"
        def result = robot.nextYPosition()

        then: "The next Y position is returned"
        result == expectedNextY

        where: "Next Y position is returned according to direction"
        x | y | facing          | expectedNextY
        0 | 1 | Direction.NORTH | 2
        1 | 2 | Direction.SOUTH | 1
        2 | 3 | Direction.EAST  | 3
        3 | 4 | Direction.WEST  | 4
    }

    def "should turn the robot left"() {

        given: "the robot with an existing position"
        robot.place(1, 1, facing)

        when: "I turn the robot left"
        robot.left()

        then: "The robot has turned to the left"
        robot.facing == newFacing

        where: "The new direction is 90 degrees anti-clockwise"
        facing          | newFacing
        Direction.NORTH | Direction.WEST
        Direction.EAST  | Direction.NORTH
        Direction.SOUTH | Direction.EAST
        Direction.WEST  | Direction.SOUTH
    }

    def "should turn the robot right"() {

        given: "the robot with an existing position"
        robot.place(1, 1, facing)

        when: "I turn the robot right"
        robot.right()

        then: "The robot has turned to the right"
        robot.facing == newFacing

        where: "The new direction is 90 degrees clockwise"
        facing          | newFacing
        Direction.NORTH | Direction.EAST
        Direction.EAST  | Direction.SOUTH
        Direction.SOUTH | Direction.WEST
        Direction.WEST  | Direction.NORTH
    }

    def "should report the robot's position"() {

        given: "the robot with an existing position"
        robot.place(x, y, facing)

        when: "I report the robot's position"
        def result = robot.report()

        then: "The robot position is returned"
        result == expectedReport

        where: "The X, Y and Facing parameters are reported"
        x | y | facing          | expectedReport
        0 | 1 | Direction.NORTH | "0,1,NORTH"
        1 | 2 | Direction.SOUTH | "1,2,SOUTH"
        2 | 3 | Direction.EAST  | "2,3,EAST"
        3 | 4 | Direction.WEST  | "3,4,WEST"
    }
}
