Toy Robot Simulator
===================


Description
-----------

- The application is a simulation of a toy robot moving on a square tabletop, of dimensions 5 units x 5 units.
- There are no other obstructions on the table surface.
- The robot is free to roam around the surface of the table, but is prevented from falling to destruction.
  Any movement that would result in the robot falling from the table is ignored, however further valid
  movement commands are allowed.


Usage
-----

To build and run the toy robot simulator, run the following commands from the project root

    $ gradle build
    $ java -jar build/libs/robot-simulator-0.0.1-SNAPSHOT.jar {filename}

The application takes a single argument 'filename', which must be a valid relative path or absolute file path.


Robot Commands
--------------

The application reads a list of commands from the input file and then validates and executes them in turn.
A single filename argument is supported, which can be an absolute or relative path. An invalid filename argument
or read error will stop program execution.

Each input line is mapped to a robot command, which is used to simulate a robot moving around a table.
Any failure to map a command from a file input line will stop program execution.

Each robot command and is validated against the robot state before execution, ignoring any invalid commands
Each robot command is executed in turn, with the robot maintaining state between commands

The following commands and formats are valid, which must each be listed on a new line of the input file:

    PLACE X,Y,F
    MOVE
    LEFT
    RIGHT
    REPORT

- PLACE will put the toy robot on the table in position X,Y and facing NORTH, SOUTH, EAST or WEST.
- The origin (0,0) is the SOUTH WEST most corner of the table.
- The first valid command to the robot is a PLACE command, after that, any sequence of commands may be issued,
  in any order, including another PLACE command. The application should discard all commands in the sequence until
  a valid PLACE command has been executed.
- MOVE will move the toy robot one unit forward in the direction it is currently facing.
- LEFT and RIGHT will rotate the robot 90 degrees in the specified direction without changing the position of the robot.
- REPORT will announce the X, Y and F of the robot to standard output.

- A robot that is not on the table will ignore the MOVE, LEFT, RIGHT and REPORT commands.
- Input can be from a file, or from standard input, as the developer chooses.
- Provide test data to exercise the application.


Example Input and Output
------------------------

### Example a

    PLACE 0,0,NORTH
    MOVE
    REPORT

Expected output:

    0,1,NORTH

### Example b

    PLACE 0,0,NORTH
    LEFT
    REPORT

Expected output:

    0,0,WEST

### Example c

    PLACE 1,2,EAST
    MOVE
    MOVE
    LEFT
    MOVE
    REPORT

Expected output

    3,3,NORTH