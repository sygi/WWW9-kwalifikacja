//Blablabla
package robo;

import java.util.Random;

public class RobotBattle
{
	public final int STEPS_PER_ROUND = 5;
	
	public final int ROBOT_A_WON = 0;
	public final int ROBOT_B_WON = 1;
	
	public final boolean VERBOSE_MOVES = true;
	
	private RobotBrain rBrainA, rBrainB;
	private RobotInfo rInfoA, rInfoB;
	private RobotInfo riProxyA, riProxyB;
	private int[] stepArrA, stepArrB;
	
	public RobotBattle(RobotBrain a, RobotBrain b)
	{
		rBrainA = a;
		rBrainB = b;
		
		Random rnd = new Random();
		
		rInfoA = new RobotInfo();
		rInfoA.x = rnd.nextInt(8);
		rInfoA.y = rnd.nextInt(8);
		rInfoA.hp = 5;
		rInfoA.orientation = rnd.nextInt(4);
		
		rInfoB = new RobotInfo();
		rInfoB.x = rnd.nextInt(8);
		rInfoB.y = rnd.nextInt(8);
		rInfoB.hp = 5;
		rInfoB.orientation = rnd.nextInt(4);
		
		riProxyA = new RobotInfo();
		riProxyB = new RobotInfo();
		
		//Upewniamy się że nie leżą na tym samym polu
		//(chociaż to dość mało prawdopodobne)
		while (	rInfoA.x == rInfoB.x &&
				rInfoA.y == rInfoB.y)
		{
			rInfoB.x = rnd.nextInt(8);
			rInfoB.y = rnd.nextInt(8);
		}
		
		stepArrA = new int[STEPS_PER_ROUND];
		stepArrB = new int[STEPS_PER_ROUND];
	}
	
	private void performMove(RobotInfo thisRobot, RobotInfo thatRobot, int op)
	{
		switch (op)
		{
		case RobotBrain.OP_FIRE:
			thisRobot.fire(thatRobot);
			if (VERBOSE_MOVES)
				System.out.println("fired");
			break;
			
		case RobotBrain.OP_HIT:
			thisRobot.hit(thatRobot);
			if (VERBOSE_MOVES)
				System.out.println("hit");
			break;
			
		case RobotBrain.OP_STEP_FORWARD:
			thisRobot.step(1, thatRobot);
			if (VERBOSE_MOVES)
				System.out.println("stepped forward");
			break;
			
		case RobotBrain.OP_STEP_BACKWARD:
			thisRobot.step(-1, thatRobot);
			if (VERBOSE_MOVES)
				System.out.println("stepped backward");
			break;
			
		case RobotBrain.OP_TURN_LEFT:
			thisRobot.turnLeft();
			if (VERBOSE_MOVES)
				System.out.println("turned left");
			break;
			
		case RobotBrain.OP_TURN_RIGHT:
			thisRobot.turnRight();
			if (VERBOSE_MOVES)
				System.out.println("turned right");
			break;
			
		default:
			//Nie wiem co tutaj robić - dyskwalifikować?
			thisRobot.hp = 0; //O jak mi przykro...
			break;
		}
	}
	
	public final boolean isOver()
	{
		return (rInfoA.hp <= 0 || rInfoB.hp <= 0);
	}
	
	public void simulationStep()
	{
		if (isOver())
			return;
		
		//Dwukrotne klonowanie jest potrzebne, gdyż gracz A mógł złośliwie coś zmienić
		riProxyA.clone(rInfoA);
		riProxyB.clone(rInfoB);
		rBrainA.planMoves(riProxyA, riProxyB, stepArrA, STEPS_PER_ROUND);
		
		riProxyA.clone(rInfoA);
		riProxyB.clone(rInfoB);
		rBrainB.planMoves(riProxyB, riProxyA, stepArrB, STEPS_PER_ROUND);
		
		for (int i = 0; i < STEPS_PER_ROUND; i++)
		{
			System.out.print("Player A ");
			performMove(rInfoA, rInfoB, stepArrA[i]);
			if (isOver())
				return;
			
			System.out.print("Player B ");
			performMove(rInfoB, rInfoA, stepArrB[i]);
			if (isOver())
				return;
		}
	}
	
	public int fightToDeath()
	{
		while (!isOver())
			simulationStep();
		
		if (rInfoA.hp > 0)
			return ROBOT_A_WON;
		else
			return ROBOT_B_WON;
	}
	
	public RobotInfo getInfoA()
	{
		riProxyA.clone(rInfoA);
		return riProxyA;
	}
	
	public RobotInfo getInfoB()
	{
		riProxyB.clone(rInfoB);
		return riProxyB;
	}
}
