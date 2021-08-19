# BattleshipGame

Battleship game with Java.

The game works but the computer shots are completely randomised which means it is really easy to win. I am working on a way to make the computer at least decent.

# What it does

The 4 main steps are :
- computer field generation : it randomizes the boats' positions without overlapping
- player field generation : it takes inputs for the boats' positions and has to check whether the it can actually be palced there
- player shot : it takes a coordinate as input and converts it to indices to then check whether it's a miss or a hit
- computer shot (needs to be worked on) : it randomizes a position and checks whether it's a hit or a miss

At the end of the game the player can choose to do another one or to quit the program.
