package searchStructures;
import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.SuccessorFunction;

public class framework {

	private Agenda agenda;
	private GoalTest gtest;
	private SuccessorFunction sfunc;

	public framework(Agenda agenda, GoalTest gtest, SuccessorFunction sfunc) {
		super();
		this.agenda = agenda;
		this.gtest = gtest;
		this.sfunc = sfunc;
	}

	public Object searchLoop() {

		while (!agenda.isEmpty()) {
			Object testNode = agenda.pop();

			if (gtest.isGoal(testNode)) {
				return testNode;
			}

			else
				sfunc.getSuccessors(testNode, agenda);
		}

		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
