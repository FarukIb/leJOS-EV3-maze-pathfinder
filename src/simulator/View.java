/*package simulator;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import dummies.DummyPilot;
import dummies.DummySensor;
import main.*;

public class View extends JFrame {
	private Maze maze;
	private static DummyPilot pilot;
	private static DummySensor sensor;
	
	public View() {
		setTitle("Maze man");
		setSize(1700, 1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	public void paint(Graphics g)
	{
		maze = new Maze(g);
		maze.draw();
		
		// I AM AWARE THAT THE CONNECTION BETWEEN PILOT AND SENSOR IS COULD BE DELT WITH BETTER
		pilot = new DummyPilot(g, sensor);
		sensor = new DummySensor(maze, pilot);
		pilot.setSensor(sensor);
		
		MyNavigator nav = new MyNavigator(pilot);
		WallChecker checker = new WallChecker(nav, sensor);
		
		InterNavigator navig = new InterNavigator(nav);
		
		PathFinder pathFind = new PathFinder(navig, checker);
		try {
			pathFind.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws InterruptedException
	{
		View view = new View();
		view.setVisible(true);
		
		
	}
}*/