## Approach

I noticed that it's possible to update the state of the game by only operating on living game cells instead of an the full grid.
After each iteration, we construct an initial array of "0"s and for each indices in the array that correspond to a living 
cell we switch to a "1" before printing the board state to the console.

## Installation

Conway's Game of Life requires the user to input starting parameters. This is accomplished by updating the accompanying 
parameters.json file. Once the desired parameters have been chosen, the project can by executed with the following command:

java -cp /path/to/jar ConwaysGameOfLife /path/to/parameters.json

## Tests

I changed the signature of my testGame method to reflect the design of my grid class. Specifically, instead of taking a 
2d grid I chose to replace that argument with a Set[Cell].