package robo;

public class BoxerRB extends RobotBrain
{
	public void planMoves(RobotInfo myInfo, RobotInfo enemyInfo, int[] moves, int numSteps)
	{
		int prevlife = enemyInfo.hp;
		int i = 0;
		
		//Jeżeli można strzelić, to czemu nie
		myInfo.fire(enemyInfo);
		if (enemyInfo.hp != prevlife)
		{
			i = 1;
			moves[0] = OP_FIRE;
		}
		
		//Bokser podchodzi i próbuje stłuc przeciwnika piąchami
		//Jako, że to tylko głupi bokser, chodzi tylko w prawo i w dół...
		for (; i < numSteps; i++)
		{
			if (myInfo.orientation == RobotInfo.DIR_UP)
			{
				myInfo.turnRight();
				moves[i] = OP_TURN_RIGHT;
				continue;
			}
			if (myInfo.orientation == RobotInfo.DIR_LEFT)
			{
				myInfo.turnLeft();
				moves[i] = OP_TURN_LEFT;
				continue;
			}
			
			int distx = (enemyInfo.x - myInfo.x + 8) % 8;
			int disty = (enemyInfo.y - myInfo.y + 8) % 8;
			
			if (distx > 1 || distx == 0)
			{
				if (myInfo.orientation == RobotInfo.DIR_DOWN)
				{
					myInfo.turnLeft();
					moves[i] = OP_TURN_LEFT;
					continue;
				}
				
				myInfo.step(1, enemyInfo);
				moves[i] = OP_STEP_FORWARD;
				continue;
			}
			
			if (disty > 1 || disty == 0)
			{
				if (myInfo.orientation == RobotInfo.DIR_RIGHT)
				{
					myInfo.turnRight();
					moves[i] = OP_TURN_RIGHT;
					continue;
				}
				
				myInfo.step(1, enemyInfo);
				moves[i] = OP_STEP_FORWARD;
				continue;
			}
			
			//Buch!
			myInfo.hit(enemyInfo);
			moves[i] = OP_HIT;
		}
	}
}
