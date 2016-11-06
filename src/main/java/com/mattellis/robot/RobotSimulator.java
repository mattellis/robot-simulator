package com.mattellis.robot;

import com.mattellis.robot.error.InvalidArgumentException;
import com.mattellis.robot.service.RobotCommandMapper;
import com.mattellis.robot.service.RobotSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Toy Robot Simulator V3
 * <p>
 * Author: Matt Ellis
 * Date: 5/11/16
 */
@Controller
public class RobotSimulator implements CommandLineRunner {

    @Autowired
    private RobotCommandMapper robotCommandMapper;

    @Autowired
    private RobotSimulatorService robotSimulatorService;

    /**
     * Reads a list of commands from an input file and then validates and executes them using the RobotSimulatorService
     * A single filename argument is supported, which can be an absolute or relative path. An invalid filename argument
     * or IOException will stop program execution.
     * <p>
     * Each input line is mapped into a RobotCommand, which is used to simulate a Robot moving around a table.
     * Any mapping failure will stop program execution
     * <p>
     * Each RobotCommand is then validated against the Robot state before execution, ignoring invalid commands
     * Each RobotCommand is executed in turn against the Robot, which maintains state between commands
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        if (args.length != 1) {
            System.err.println("Invalid usage. Must supply exactly one argument specifying input file name.");
            return;
        }

        String fileName = args[0];
        try {
            System.out.println("Attempting to read robot simulator commands from file: " + fileName);
            Files.lines(Paths.get(fileName))
                    .map(robotCommandMapper::mapRobotCommand)
                    .filter(robotSimulatorService::validCommand)
                    .forEach(robotSimulatorService::executeCommand);

        } catch (IOException e) {
            System.err.println("Unable to read from specified file " + fileName + ". Error message: " + e.getMessage());

        } catch (InvalidArgumentException e) {
            System.err.println("InvalidArgumentException. Error message: " + e.getMessage());
        }
    }
}
