package main;

import java.util.Map;

import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class InterNavigator {
	private Map<Integer, Waypoint> translator;
	private Navigator pilot;
	private WallChecker checker;
	private int x;
	private int y;
	private int direction;
	public final int NORTH = 0;
	public final int WEST = 1;
	public final int SOUTH = 2;
	public final int EAST = 3;
	
	public InterNavigator(Navigator nav, WallChecker setCheck)
	{
		translator = new Map<>();
		pilot = nav;
		checker = setCheck;
		x = 0; y = 0;
		direction = 0;
	}
	
	public void addWaypoint(int num)
	{
		Waypoint wp = new Waypoint(x, y);
		translator.put(num, wp);
	}
	
	private void update(int vertical, int horizontal) {
		if (vertical == 1)
			y += 25;
		else if (vertical == -1)
			y -= 25;
		
		if (horizontal == 1)
			x += 25;
		else if (horizontal == -1)
			x -= 25;
	}
	
	
}
