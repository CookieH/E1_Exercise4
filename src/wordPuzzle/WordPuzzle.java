package wordPuzzle;

import java.util.ArrayList;
import java.util.Random;

import rp13.search.interfaces.Heuristic;

/**
 * The class containing the word puzzle itself and all of the methods to affect the puzzle
 * @author hxc326
 *
 */
public class WordPuzzle implements Heuristic{
	
	protected String str;
	private WordPuzzle goal;
	private static Random generator;
	private int costToMove;
	private ArrayList<IntPair> moves;
	
	/**
	 * The constructor initialises the string and random generator and calls the method
	 * to randomly jumble the string
	 * @param str the word for the game
	 * @param goal the goal word so the heuristic can check the current word against th goal
	 */
	public WordPuzzle(String str, WordPuzzle goal)
	{
			this.str = str;
			this.goal = goal;
			costToMove = 0;
			moves = new ArrayList<IntPair>();
			createMoves();
	}
	
	/**
	 * a separate constructor for when a WordPuzzle is made without sending the goal, i.e.
	 * the first instantiation (the goal itself)
	 * @param str the string to be stored
	 */
	public WordPuzzle(String str)
	{
			this.str = str;
			costToMove = 0;
			moves = new ArrayList<IntPair>();
			createMoves();
	}
	
	/**
	 * returns the possible moves
	 * @return the array list of possible moves
	 */
	public ArrayList<IntPair> getMoves()
	{
		return moves;
	}
	
	/**
	 * creates all of the next possible moves for each state and stores them in 
	 * a private global array list
	 */
	public void createMoves() 
	{
		for(int i = 0; i<str.length(); i++)
		{
			for(int j =0; j<str.length(); j++)
			{
				if(j>i && isPossibleMove(new IntPair(i,j)))
				{//only adds if j is more than i meaning duplicate pairs are not added,
				//reduces the workload of the agenda
					moves.add(new IntPair(i,j));
				}
			}
		}
		
	}

	/**
	 * returns the current string
	 * @return the current string
	 */
	public String getString()
	{
		return str;
	}
	
	/**
	 * jumbles the string and also stores it as the current string
	 * @return returns the jumbled string
	 */
	public String jumbleString(String it)
	{
		this.str = jumble(it);
		return str;
	}
	
	/**
	 * randomly jumbles the string for the initial jumbled string
	 * acts as static so it can be put straight into a new constructor
	 * @param puzzleStr the string to be jumbled
	 * @return the jumbled string
	 */
	public static String jumble(String puzzleStr)
	{
		String puzzleJumbled = puzzleStr;
		generator = new Random();
		
		for(int i=0; i<20; i++)
		{
			int a = generator.nextInt(puzzleJumbled.length());
			int b = generator.nextInt(puzzleJumbled.length());
			/*
			 * above - creates two random numbers that are the indexes of the characters
			 * to be swapped
			 * below - calls the swap method with the two indexes and string
			 */
			puzzleJumbled = swap(a, b, puzzleJumbled);
		}		
		
		return puzzleJumbled;
	}
	
	/**
	 * Returns the current game state represented as a string
	 */
	public String toString()
	{
		return "Current: " + str;
	}
	
	/**
	 * an equality comparison for the string state of two word puzzle states
	 */
	@Override
	public boolean equals(Object obj) {
		WordPuzzle that = (WordPuzzle) obj;
		return str.compareTo(that.getString()) == 0;
	}
	
	/**
	 * makes the move on the jumbled string
	 * @param Action the pair of indexes to swap
	 * @return the string after the swap
	 */
	public void makeMove(IntPair Action)
	{
		str = swap(Action.getX(), Action.getY(), str);
		
	}
	
	/**
	 * uses a stringBuilder to swap the two characters around
	 * @param i the first char to be swapped
	 * @param j the second char to be swapped
	 * @param swap the string for the swap EightPuzzleto be performed on
	 * @return the string with the swap performed
	 */
	public static String swap(int i, int j, String swap)
	{
		StringBuilder swapBuilder = new StringBuilder(swap);//creates a string builder
															// for easy access to chars
		char temp = swapBuilder.charAt(i);//stores the char at i to prevent overwriting
		swapBuilder.setCharAt(i, swapBuilder.charAt(j));//changes char at i to the char at j
		swapBuilder.setCharAt(j, temp);//changes the char at j to the temp char
		swap = swapBuilder.toString();//converts back to string
		return swap;
	}
	
	/**
	 * checks if the requested move is a valid move
	 * @param i the first char to be swapped
	 * @param j the second char to be swapped 
	 * @return the boolean result of whether it is valid or not
	 */
	public boolean isPossibleMove(IntPair Action)
	{
		return Action.getX() < str.length() && Action.getY() < str.length();
	}

	/**
	 * returns the cost function for the a star implementation
	 */
	@Override
	public int getCostToMove() {
		// TODO Auto-generated method stub
		return costToMove;
	}

	/**
	 * returns the heuristic value for the current state, uses hamming distance
	 * (amount of characters in two same lenght strings that dont match)
	 */
	@Override
	public int getHeuristicValue() {
		String goalStr = goal.getString();
		assert goalStr.length() == str.length();//ensure the strings are both of the same length
		int misplaced = 0;
		for(int i = 0; i< str.length(); i++)//checks every char pair 
		{
			if(str.charAt(i) != goalStr.charAt(i))
				{//if they dont match the misplaced counter is incremented
					misplaced++;
				}
		}
		return misplaced;
	}
	
}

