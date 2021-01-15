# Description
Simple scientific calculator application that implements Dijkstra's
shunting yard algorithm. Built using an MVC design pattern.

[![calc.png](https://i.postimg.cc/59wFPRp7/calc.png)](https://postimg.cc/RqZFhGkK =50x50)

## How To Use

You will need:
`Javafx-sdk 14.0.1` or  later to run the program.
You will need to download both RPN.jar and run.command files.
To configure the run.command you need to edit two sections in
run.command:

`java --module-path *add path to your javafxSDK* --add-modules javafx.controls,javafx.fxml -jar *add path to jar*/RPN.jar`

Make sure the run.command file is in the same directory
as RPN.jar before execution. 