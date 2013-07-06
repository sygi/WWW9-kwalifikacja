package robo;

import java.util.Random;

public class RandomRB extends RobotBrain
{
	private Random r;
	
	public RandomRB()
	{
		r = new Random();
	}
	
	public void planMoves(RobotInfo myInfo, RobotInfo enemyInfo, int[] moves, int numSteps)
	{
		for (int i = 0; i < numSteps; i++)
			moves[i] = r.nextInt(6);
	}
}
