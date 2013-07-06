package robo;

//Ta klasa ułatwia przeprowadzanie ruchów robotów.
//Pomaga też RobotBrain'om symulować zawczasu ruchy
public class RobotInfo
{
	public static final int DIR_RIGHT = 0;
	public static final int DIR_DOWN = 1;
	public static final int DIR_LEFT = 2;
	public static final int DIR_UP = 3;
	
	public int x, y;
	public int hp;
	public int orientation;
	
	public final void clone(RobotInfo ri)
	{
		x = ri.x;
		y = ri.y;
		hp = ri.hp;
		orientation = ri.orientation;
	}
	
	public final boolean isAt(int pX, int pY)
	{
		pX = (pX+8) % 8;
		pY = (pY+8) % 8;
		
		return (pX == x && pY == y);
	}
	
	public final void turnLeft()
	{
		if (--orientation < 0)
			orientation += 4;
	}
	
	public final void turnRight()
	{
		if (++orientation >= 4)
			orientation -= 4;
	}
	
	public final void fire(RobotInfo other)
	{
		switch (orientation)
		{
		case RobotInfo.DIR_UP:
		case RobotInfo.DIR_DOWN:
			if (x == other.x)
				other.hp--;
			break;
			
		case RobotInfo.DIR_LEFT:
		case RobotInfo.DIR_RIGHT:
			if (y == other.y)
				other.hp--;
			break;
		}
	}
	
	public final void hit(RobotInfo other)
	{
		switch (orientation)
		{
		case RobotInfo.DIR_UP:
			if (other.isAt(x-1, y-1) ||
				other.isAt(x, y-1) ||
				other.isAt(x+1, y-1))
				other.hp--;
			break;
			
		case RobotInfo.DIR_RIGHT:
			if (other.isAt(x+1, y-1) ||
				other.isAt(x+1, y) ||
				other.isAt(x+1, y+1))
				other.hp--;
			break;
			
		case RobotInfo.DIR_DOWN:
			if (other.isAt(x-1, y+1) ||
				other.isAt(x, y+1) ||
				other.isAt(x+1, y+1))
				other.hp--;
			break;
			
		case RobotInfo.DIR_LEFT:
			if (other.isAt(x-1, y-1) ||
				other.isAt(x-1, y) ||
				other.isAt(x-1, y+1))
				other.hp--;
			break;
		}
	}
	
	public final void step(int num, RobotInfo other)
	{
		int ori;
		if (num == -1)
			ori = (orientation + 2) % 4;	//Odwracamy o 180 stopni
		else if (num == 1)
			ori = orientation;
		else
			return;
		
		switch (ori)
		{
		case RobotInfo.DIR_UP:
			if (!other.isAt(x, y-1))
				y--;
			break;
			
		case RobotInfo.DIR_RIGHT:
			if (!other.isAt(x+1, y))
				x++;
			break;
			
		case RobotInfo.DIR_DOWN:
			if (!other.isAt(x, y+1))
				y++;
			break;
			
		case RobotInfo.DIR_LEFT:
			if (!other.isAt(x-1, y))
				x--;
			break;
		}
		
		if (x < 0)
			x += 8;
		if (y < 0)
			y += 8;
			
		x %= 8;
		y %= 8;
	}
}
