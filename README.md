# CS310 Foundations of Artificial Intelligence, 2017

## Practical 1: 2048

2048 is a sliding tile game that was created by Gabriele Cirulli in March 2014. The aim of the game is to combine tiles, starting initially with 2’s and 4’s, until the 2048 tile is reached. Initially it was created as a web application. You can play the original version of the game here:

http://gabrielecirulli.github.io/2048/

To move the tiles around, press the arrow keys (up, down, left and right). When you hit a key, all tiles that can move will move in that direction as far as they can go. When two 2’s collide, they make a 4 (and add 4 points to your score), when two 4’s collide they make an 8 (and add 8 points to the score) and so on. If you reach 2048, you can keep playing, to see how high you can get your score. It is possible to create the 4096 tile, the 8192 tile, and so on. The game ends when the board is full and you have no legal moves that you can play.

In this practical, you will implement an AI agent to play the game of 2048. The goal is to create an AI player for this game that can get as high a score as possible.

## How to Install 2048

The 2048 code is held at the following repository: https://gitlab.cis.strath.ac.uk/njb11150/CS310_2048

There are two options for installing 2048: you either can clone the repository through gitlab, or you can download the zip from the same address and use the following instructions:
1.	From the repository page above, find the download button, and select “Download zip”
2.	Open eclipse.
3.	Select "File" > "Import" > "Existing Projects into Workspace" and hit the "Next >" button.
4.	Select the radio button labelled "Select archive file:" and hit "Browse..."
5.	Find the 2048 zip file and hit "Open".
6.	Hit "Finish" to import the code into eclipse.

## How to run the code

1.	Expand CS310-2048\src\controller in eclipse and you will find a file called Controller.java
2.	Select this file.
3.	Select "Run" > "Run" or hit the "Run" button.
You should now see a game of 2048. Hit "Play" to see the RandomAI agent play a game. The score is shown in the title bar.

On average, the RandomAI agent scores about 1000 points. Your task is to create a player that can do better than this. In CS310-2048\src\ai, make a copy of RandomAI.java and call it <yourFirstName><yourLastName>2048.java. Put your code into this class.

Your aim should be to create a player which can score as many points as possible. A player that scores less than 2000 points scores zero; a score of 2000 points gets a mark of 40%, with 32000 points getting a score of 70% - between these two scores, the mark rises linearly. If you score over 32000 points, you mark will be (70 + ((your-score – 32000)/your-score)*30.

Our version of 2048 enforces a time limit of 1 second per move. The way this works is by running a timer while waiting for your agent to post its move: if the time taken is longer than 1 second, the game will ignore the move made, drop a new tile onto the board and then start the timer again.

To see what your mark might be, you can use CS310-2048/src/data/Statistics.java. First find the line in the main method that reads:

	Statistics s = new Statistics(100, new RandomAI());

Change RandomAI() to be the name of your player, and keep the number of trials as at least 100.

## How to submit your player
1.	Your player needs to be a single Java file.
2.	Your Java file should be called <Firstname><Surname>2048.java (so Fred Bloggs would submit a file called FredBloggs2048.java).
3.	You should submit your file on MyPlace – instructions will be provided later in the semester.
The deadline for submission is midnight on Friday 31st March 2017. Players submitted after this deadline will not be considered.

John Levine

Damien Anderson

23rd January 2017
