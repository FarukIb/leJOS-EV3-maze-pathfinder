package main;

import dummies.DummyPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Waypoint;

public class MyNavigator {
	private MovePilot pilot;
	private int direction;
	private Waypoint currWp;
	
	public MyNavigator(MovePilot pilot2)
	{
		currWp = new Waypoint(100, 100);
		pilot = pilot2;
		direction = 0;
	}
	
	private void turnRight() throws InterruptedException
	{
		pilot.rotate(90);
		direction++;
		if (direction == 4)
			direction = 0;
	}
	
	private int turnRightSim(int toTurn)
	{
		int cnt = 0;
		int fakeDir = direction;
		while (fakeDir != toTurn)
		{
			fakeDir++;
			cnt++;
			if (fakeDir == 4)
				fakeDir = 0;
		}
		return cnt;
	}
	
	private void turnLeft() throws InterruptedException
	{
		pilot.rotate(-90);
		direction--;
		if (direction == -1)
			direction = 3;
	}
	
	private int turnLeftSim(int toTurn)
	{
		int cnt = 0;
		int fakeDir = direction;
		while (fakeDir != toTurn)
		{
			fakeDir--;
			cnt++;
			if (fakeDir == -1)
				fakeDir = 3;
		}
		return cnt;
	}
	
	public int getDir(Waypoint a, Waypoint from)
	{
		if (a.y > from.y)
			return 0;
		else if (a.x > from.x)
			return 1;
		else if (a.y < from.y)
			return 2;
		else
			return 3;
	}
	
	public void goTo(Waypoint toGo) throws InterruptedException
	{
		int toTurn = getDir(toGo, currWp);
		if (turnRightSim(toTurn) < turnLeftSim(toTurn))
		{
			while (direction != toTurn)
				turnRight();
		}
		else
		{
			while (direction != toTurn)
				turnLeft();
		}
		pilot.travel(30);
		currWp = toGo;
	}
}
