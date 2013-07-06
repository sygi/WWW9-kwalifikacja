package robo;

public class Animacja
{
	static char[] dirchars = { '>', 'v', '<', '^' };
	
	public static final int WAIT_TIME = 1000;
	
	public static void main(String args[])
	{
		RobotBrain roboA = new RandomRB();
		RobotBrain roboB = new BoxerRB();
		RobotBattle rb = new RobotBattle(roboA, roboB);
		
		int nstep = 0;
		try
		{
			while (true)
			{
				System.out.println("Stage " + nstep + ":");
				
				for (int y = 0; y < 8; y++)
				{
					for (int x = 0; x < 8; x++)
					{
						if (rb.getInfoA().x == x && rb.getInfoA().y == y)
							System.out.print("" + dirchars[rb.getInfoA().orientation] + "A");
						else if (rb.getInfoB().x == x && rb.getInfoB().y == y)
							System.out.print("" + dirchars[rb.getInfoB().orientation] + "B");
						else
							System.out.print("_ ");
					}
					
					System.out.println("");
				}
				
				System.out.println("A's HP: " + rb.getInfoA().hp + " B's HP: " + rb.getInfoB().hp);
				//System.out.println("A: (" + rb.getInfoA().x + ", " + rb.getInfoA().y + ")");
				//System.out.println("B: (" + rb.getInfoB().x + ", " + rb.getInfoB().y + ")");
				
				if (rb.isOver())
					break;
				
				Thread.sleep(WAIT_TIME);
				
				rb.simulationStep();
				nstep++;
			}
			
			if (rb.getInfoA().hp == 0)
				System.out.println("Robot B wins!");
			else
				System.out.println("Robot A wins!");
		}
		catch (InterruptedException iex)
		{
			
		}
	}
}
