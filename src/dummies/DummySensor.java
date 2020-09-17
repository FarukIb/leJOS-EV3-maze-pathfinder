package dummies;

import simulator.Maze;

public class DummySensor {
	private int x, y;
	private Maze maze;
	private DummyPilot dirGetter;
	
	public DummySensor(Maze maze1, DummyPilot dirGetter1)
	{
		dirGetter = dirGetter1;
		maze = maze1;
		x = 1;
		y = 1;
	}
	
	public void move(int x1, int y1)
	{
		x += x1;
		y += y1;
	}
	
	public float[] fetchSample()
	{
		return maze.getWalls(x, y, dirGetter.getDir());
	}
}
