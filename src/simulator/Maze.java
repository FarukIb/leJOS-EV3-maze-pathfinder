package simulator;

import java.awt.Color;
import java.awt.Graphics;

public class Maze {
	private static Graphics g;
	
	public float [][] maze = 
		{ {1, 1, 1, 1, 1},
		  {1, 0, 0, 0, 1},
		  {1, 0, 1, 0, 1},
		  {1, 0, 1, 1, 1},
		  {1, 0, 0, 4, 1},
		  {1, 1, 1, 1, 1}
		};
	
	public Maze(Graphics g1)
	{
		g = g1;
	}
	
	public float[] getWalls(int x, int y, int dir)
	{
		float[] out = new float[1];
		out[0] = 40;
		
		if (dir == 0)
			if (maze[y - 1][x] == 1)
				out[0] = 1;
		if (dir == 1)
			if (maze[y][x + 1] == 1)
				out[0] = 1;
		if (dir == 2)
			if (maze[y + 1][x] == 1)
				out[0] = 1;
		if (dir == 3)
			if (maze[y][x - 1] == 1)
				out[0] = 1;
		
		return out;
	}
	
	public void draw()
	{
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				Color clr;
				if (maze[i][j] == 1)
					clr = Color.BLACK;
				else if (maze[i][j] == 3)
					clr = Color.GREEN;
				else if (maze[i][j] == 4)
					clr = Color.RED;
				else
					clr = Color.WHITE;
				g.setColor(clr);
				g.fillRect(j * 100 + 50, i * 100 + 50, 100, 100);
				g.setColor(Color.BLACK);
				g.drawRect(j * 100 + 50, i * 100 + 50, 100, 100);
			}
		}
	}
}
