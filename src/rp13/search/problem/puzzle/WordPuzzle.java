package rp13.search.problem.puzzle;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class containing the word puzzle itself and all of the methods to affect the puzzle
 * @author hxc326
 *
 */
public class WordPuzzle {
	
	protected String str;
	private Random generator;
	
	private ArrayList<IntPair> moves;
	
	/**
	 * The constructor initialises the string and random generator and calls the method
	 * to randomly jumble the string
	 * @param str the word for the game
	 */
	public WordPuzzle(String str)
	{
			this.str = str;
			createMoves();
	}
	
	public ArrayList<IntPair> getMoves()
	{
		return moves;
	}
	
	public void createMoves() 
	{
		for(int i = 0; i<str.length(); i++)
		{
			for(int j =0; j<str.length(); j++)
			{
				if(j>i && isPossibleMove(new IntPair(i,j)))
				{
					moves.add(new IntPair(i,j));
				}
			}
		}
		
	}

	/**
	 * returns the goal string
	 * @return the goal string
	 */
	public String getString()
	{
		return str;
	}
	
	/**
	 * jumbles the string
	 * @return returns the jumbled string
	 */
	public String jumbleString(String it)
	{
		this.generator = new Random();
		this.str = jumble(it);
		return str;
	}
	
	/**
	 * randomly jumbles the string for the initial jumbled string
	 * @param puzzleStr the goal string
	 * @return the jumbled string
	 */
	public String jumble(String puzzleStr)
	{
		String puzzleJumbled = puzzleStr;
		
		for(int i=0; i<20; i++)
		{
			int a = generator.nextInt(puzzleStr.length());
			int b = generator.nextInt(puzzleStr.length());
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
	 * makes the move on the jumbled string
	 * @param Action the pair of indexes to swap
	 * @return the jumbled string
	 */
	public String makeMove(IntPair Action)
	{
		return swap(Action.x, Action.y, str);
	}
	
	/**
	 * uses a stringBuilder to swap the two characters around
	 * @param i the first char to be swapped
	 * @param j the second char to be swapped
	 * @param swap the string for the swap to be performed on
	 * @return the string with the swap performed
	 */
	public String swap(int i, int j, String swap)
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
		return Action.x < str.length() && Action.y < str.length();
	}
	
}