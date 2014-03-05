package robotFiles;

import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * This is the highest level behaviour, it takes precedence over the other
 * behaviours, this is where the junctions are handled, here we can set a
 * pattern of turns for the robot to take at each junction
 * 
 * @author E1
 * 
 */
public class TakeTurn implements Behavior {

	private DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	private boolean suppressedTurn;

	// fields for direction turning
	private static final int UP = 0;
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int LEFT = 3;
	private ArrayList<Integer> directions = new ArrayList<Integer>();
	private boolean repeatPattern;
	private int currentMovement = 0;
	private boolean randomise = false;
	private static final int PRETATE = 4;

	/**
	 * The constructor declares the variables and also the pattern for the robot
	 * to follow can be added here
	 * 
	 * @param pilot
	 *            the differential pilot that controls the robots movements
	 * @param left
	 *            the left light sensor
	 * @param right
	 *            the right light sensor
	 */
	public TakeTurn(DifferentialPilot pilot, LightSensor left, LightSensor right) {
		super();
		this.pilot = pilot;
		this.left = left;
		this.right = right;
		left.setFloodlight(true);
		right.setFloodlight(true);
		suppressedTurn = false;
		// set repeat to true for the pattern to keep repeating
		repeatPattern = false;
	}

	/**
	 * this method contains the criteria for this behaviour to take over in this
	 * case it is if both sensors are detecting black tape
	 */
	@Override
	public boolean takeControl() {
		return (right.readNormalizedValue() < 400)
				&& (left.readNormalizedValue() < 400);
		// takes control if both sensors are on black
	}

	/**
	 * This method is the actual action class that runs when this behaviour
	 * takes control when the robot arrives at a junction the decision for what
	 * to do is made here, it will either follow a set of instructions provided
	 * in the constructor or it will follow random movements
	 */
	@Override
	public void action() {
		int turn = 0;

		if (currentMovement >= directions.size()) {
			if (repeatPattern) {
				// resets the preset pattern
				currentMovement = 0;
			}

			else {
				pilot.rotate(1080);
				System.exit(0);
			}

		}

		if (randomise) {
			turn = (int) (Math.random() * 5);
			// takes a random number to use for the random movements
		} else {
			turn = directions.get(currentMovement);
			// gets a preset direction from the instructions set in the
			// constructor
		}

		currentMovement++;
		switch (turn)// switch statement for the turn to easily display and
						// alter each turn
		{
		case 0:
			pilot.travel(10);
			break;// forward on junction
		case 1:
			pilot.travel(PRETATE);
			pilot.rotate(-90);
			break;// turns right at junction
		case 2:
			pilot.rotate(180);
			pilot.travel(50);
			break;// turns around at a junction
		case 3:
			pilot.travel(PRETATE);
			pilot.rotate(90);
			break;// turns left at junction
		default:
			break;
		}

		while (!suppressedTurn && pilot.isMoving()) {
			Thread.yield();
			// while pilot moving as this is the highest level behaviour
			// and noting suppresses it
		}
		suppressedTurn = false;
	}

	/**
	 * the method ran when suppressed
	 */
	@Override
	public void suppress() {
		suppressedTurn = true;

	}

	/**
	 * Adds a direction to the array list for the robot to perform
	 * 
	 * @param i
	 *            the direction to be taken (expressed mainly as constants)
	 */
	public void addDirections(int i) {
		if (i > 3) {
			;// If invalid number do nothing
		} else {
			directions.add(i);
		}
	}

	/**
	 * Returns the list of directions that the robot currently has!
	 * @return The list of instruction that the robot is following.
	 */
	public ArrayList<Integer> getDirections() {
		return this.directions;
	}

}
