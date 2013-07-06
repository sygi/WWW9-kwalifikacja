package robo;

public abstract class RobotBrain
{
	public static final int OP_FIRE = 0;
	public static final int OP_HIT = 1;
	public static final int OP_STEP_FORWARD = 2;
	public static final int OP_STEP_BACKWARD = 3;
	public static final int OP_TURN_LEFT = 4;
	public static final int OP_TURN_RIGHT = 5;
	
	public abstract void planMoves(RobotInfo myInfo, RobotInfo enemyInfo, int[] moves, int numSteps);
}
