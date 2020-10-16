package dummies;

import java.awt.Color;
import java.awt.Graphics;

import lejos.hardware.lcd.Font;

public class DummyPilot {
	private int x, y;
	private int direction;
	private Graphics g;
	private DummySensor toUpdate;
	private Color clr;
	
	public DummyPilot(Graphics g1, DummySensor toU)
	{
		toUpdate = toU;
		
		g = g1;
		x = 1; y = 1;
		direction = 0;
		
		clr = Color.PINK;
		g.setColor(clr);
		g.fillRect(50 + x * 100, 50 + y * 100, 100, 100);
		clr = Color.BLACK;
		g.setColor(clr);
		g.drawRect(50 + x * 100, 50 + y * 100, 100, 100);
		drawDir();
	}
	
	public void setSensor(DummySensor toU)
	{
		toUpdate = toU;
	}
	
	public void travel(int dist)
	{
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		clr = Color.WHITE;
		g.setColor(clr);
		g.fillRect(50 + x * 100, 50 + y * 100, 100, 100);
		clr = Color.BLACK;
		g.setColor(clr);
		g.drawRect(50 + x * 100, 50 + y * 100, 100, 100);
		
		if (direction == 0)
		{
			toUpdate.move(0, -1);
			y--;
		}
		else if (direction == 1)
		{
			toUpdate.move(1, 0);
			x++;
		}
		else if (direction == 2)
		{
			toUpdate.move(0, 1);
			y++;
		}
		else
		{
			toUpdate.move(-1, 0);
			x--;
		}
		
		clr = Color.PINK;
		g.setColor(clr);
		g.fillRect(50 + x * 100, 50 + y * 100, 100, 100);
		clr = Color.BLACK;
		g.setColor(clr);
		g.drawRect(50 + x * 100, 50 + y * 100, 100, 100);
	}
	
	public void setDir(int x)
	{
		direction = x;
	}
	
	public int getDir()
	{
		return direction;
	}
	
	public void drawDir()
	{
		g.clearRect(1400, 0, 300, 300);
		g.drawString("dir: " + Integer.toString(direction), 1400, 100);	
	}
	
	public void rotate(float rot) throws InterruptedException {
		
		if (rot == 90)
			direction++;
		else if (rot == -90)
			direction--;
		
		if (direction == 4)
			direction = 0;
		if (direction == -1)
			direction = 3;
		clr = Color.WHITE;
		Thread.sleep(500);
		drawDir();
	}
	
}
