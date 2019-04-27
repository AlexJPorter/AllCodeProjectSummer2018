
/**
 * Defines the methods and variables in
 * a text and GUI based dice game
 * 
 * @ Alex Porter
 * @ 10/26/2017
 */
public class PigGame
{
    //generates two die objects from provided class
    GVdie die1 = new GVdie();
    GVdie die2 = new GVdie();
    
    //sets scores to 0, and creates a final win score
    private int playerScore     = 0;
    private int computerScore   = 0;
    private int roundScore      = 0;
    private final int WIN_SCORE = 100;
    
    //intializes booleans that determine the winner
    private boolean playerWin = false;
    private boolean computerWin = false;
    
    //sets the player to go first
    private boolean isPlayerTurn = true;
    private boolean isComputerTurn = false;
    
    //initializes vars to keep track of turns
    private int numPlayerTurns = 0;
    private int numComputerTurns = 0;
    
    //this constructor creates the dice, and resets the scores
    public PigGame() 
    {
        GVdie die1 = new GVdie();
        GVdie die2 = new GVdie();
    
        playerScore = 0;
        computerScore = 0;
    
        roundScore = 0; 
        //prints a welcome message
        System.out.println("Welcome to Alex's Game of Pig!\n");
    }
    
    public int getRoundScore()
    {
        return roundScore;
    }
    
    public int getPlayerScore()
    {
        return playerScore;
    }
    
    public int getComputerScore()
    {
        return computerScore;
    }
    
    //checks if player's turn
    public boolean isPlayerTurn() 
    {
        return isPlayerTurn;
    }
    
    //checks if computer's turn
    public boolean isComputerTurn() 
    {
        return isComputerTurn;
    }
    
    //returns specified die
    //valid parameter is 1 or 2
    public GVdie getDie(int num)
    {
        if (num ==1) {
            return die1;
        }
        else {
            return die2;
        }
    }
    
    //returns true if player won
    public boolean playerWon()
    {
        if (playerScore > computerScore) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //returns true if computer won
    public boolean computerWon() 
    {
        if (computerScore > playerScore) {
        return true;
        }
        else {
        return false;
        }
    }
    
    //sets a random value to each die using roll()
    //if a one is rolled, roundscore is 0
    //else round score is calulated by adding the die + roundscore
    private void rollDice() {
        die1.roll();
        die2.roll();
        if (die1.getValue() == 1 || die2.getValue() == 1){
            this.roundScore = 0;
        }
        else {
            this.roundScore += (die1.getValue() + die2.getValue());
        }
    }
    
    //this method describes the player rolling the dice
    public void playerRolls()
    {
        //rollDice() method is used to determine die values
        rollDice();
        //prints die values to console
        System.out.println(die1.getValue() + " " + die2.getValue() + " Round Score: " + roundScore);
        if (die1.getValue() == 1 && die2.getValue() == 1){
            //sets player score to 0 and passes the turn to computer
            playerScore = 0;
            playerHolds();
        }
        else if (die1.getValue() == 1 || die2.getValue() == 1) {
            //if a single 1 is rolled, 0 round points and turn is over
            playerHolds();
        }
        else {
            //checks for a win
            if ((playerScore + roundScore) >= WIN_SCORE) {
                System.out.println("\nYou won!\n");
                playerHolds();
                //used by autoGame(), checks if player won
                playerWin = playerWon();
                //sets the player turn to true so the computer doesn't go
                isComputerTurn = false;
                isPlayerTurn = true;
            }
        }
    }
    
    //playerHolds() ends the turn of the player,
    //adds roundscore to playerscore
    //resets roundscore
    //passes turn to the computer
    public void playerHolds() 
    {
        playerScore += roundScore;
        roundScore = 0;
        System.out.println("---- Your score: " + playerScore + "\n");
        isComputerTurn = true;
        isPlayerTurn = false;
    }
    
    //this method descirbes the computer's turn
    public void computerTurn() 
    {
        //runs until win is achieved, or round score of 18 is surpassed, or a one is rolled
        while (true) 
        {
            //rolls dice and prints die values
            rollDice();
            System.out.println(die1.getValue() + " " + die2.getValue() + " Round Score: " + roundScore
            );
            if (die1.getValue() == 1 && die2.getValue() == 1){
                //checks if snake eyes is rolled
                //resets score than passes turn to player
                //exits loop because turn is over
                computerScore = 0;
                System.out.println("---- Computer Score: " + computerScore + "\n");
                break;
            }
            else if (die1.getValue() == 1 || die2.getValue() == 1) {
                //same as playerRolls(), no points scored and loop ends
                System.out.println("---- Computer Score: " + computerScore + "\n");
                break;
            }
            else if (roundScore <= 19) {
                //computer continues rolling unless a win is present
                if ((computerScore +roundScore) >= WIN_SCORE) {
                    //checks for a win, if so, a message is shown
                    System.out.println("Game over! Computer wins with score: " + (computerScore + roundScore));
                    //adds roundScore to total score
                    //sets computerWin to true
                    computerScore += roundScore;
                    computerWin = computerWon();
                    break;
                }
            }
            else {
                //runs if roundScore of 18 is surpassed
                //checks for a win also
                if ((computerScore + roundScore) >= WIN_SCORE) {
                    System.out.println("Game over! Computer wins with score: " + (computerScore + roundScore));
                    computerScore += roundScore;
                    computerWin = computerWon();
                    break;
                }
                //adds roundScore to computerScore
                computerScore += roundScore;
                //displays info on the console
                System.out.println("---- Computer Score: " + computerScore + "\n");
                break;
            }
        }
        //resets roundScore, passes turn to player
        roundScore = 0;
        isComputerTurn = false;
        isPlayerTurn = true;
    }
    
    //this method automates the player's turn
    //contains the same 'AI' as the computer
    private void playerTurn() 
    {
        while (true){
            //uses playerRolls() method for simplicity
            //win status is also checked here
            playerRolls();
            if (roundScore == 0) {
                break;
            }
            else if (roundScore <= 19) {
                //continues until roundScore of 18 is surpassed
                continue;
            }
            else {
                //displays roundScore to console
                //System.out.println("Player Round Score: " + roundScore);
                //score is added to total score, turn passed to computer
                playerHolds();
                break;
            }
        }
    }
    
    //this method resets values for a new game
    public void restart() 
    {
        //resets scores
        playerScore = 0;
        computerScore = 0;
        roundScore = 0;
        //player goes first
        isPlayerTurn = true;
        isComputerTurn = false;
        //turns are reset
        numComputerTurns = 0;
        numPlayerTurns = 0;
    }
    
    //this method runs a game automatically 
    //Uses playerTurn() computerTurn().
    public void autoGame() 
    {
        //before starting, appropriate values are reset
        restart();
        while (true) {
            //player goes then increments # turns
            playerTurn();
            numPlayerTurns += 1;
            //checks if there is a win, breaks out of game if true
            if (playerWin) {
                System.out.println("Player wins with score: " + (playerScore + roundScore));
                System.out.println("Computer score: " + computerScore);
                System.out.println("Turns: C: " + numComputerTurns +" P: " + numPlayerTurns);
                break;
            }
            //computer goes then increments # of turns
            //but only if it's computer turn
            if(!isPlayerTurn) {
                computerTurn();
                numComputerTurns += 1; 
            }
            //checks if there is a win, breaks out of the game if true
            if (computerWin) {
                System.out.println("Computer wins with score: " + (computerScore + roundScore));
                System.out.println("Player score: " + playerScore);
                System.out.println("Turns: C" + numComputerTurns +" P" + numPlayerTurns);
                break;
            }
        }
    }
}