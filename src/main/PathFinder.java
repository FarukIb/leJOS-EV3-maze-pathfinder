package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class PathFinder {
	private short gridGraph[][];
	private InterNavigator interNavigator;
	private WallChecker checker;
	
	private Stack<Short> toVisit;
	
	public ArrayList<Pair<Integer, Integer> > sides;
	
	public PathFinder(InterNavigator inter, WallChecker checke) {
		interNavigator = inter;
		checker = checke;
		
		gridGraph = new short[210][210];
		for (int i = 0; i < 210; i++)
			for (int j = 0; j < 210; j++)
				gridGraph[i][j] = 0;
		gridGraph[100][100] = 1;
		
		toVisit = new Stack<Short>();
		toVisit.push((short) 1);
		
		sides = new ArrayList<Pair<Integer, Integer> >();
		for (int i = 0; i < 4; i++)
			sides.add(new Pair<Integer, Integer>(0, 0));
		sides.set(0, new Pair<Integer, Integer>(0, 1)); // north
		sides.set(1, new Pair<Integer, Integer>(1, 0)); //  east
		sides.set(2, new Pair<Integer, Integer>(0, -1)); // south
		sides.set(3,  new Pair<Integer, Integer>(-1, 0)); // west
	}
	
	public void start() throws InterruptedException {
		while (!toVisit.isEmpty())
		{
			Short curr = toVisit.peek();
			toVisit.pop();
			if (curr != 1)
				interNavigator.moveFromTo(interNavigator.getCurrWp(), curr);
			updateGraph();
		}
	}
	
	private void updateGraph() throws InterruptedException {
		ArrayList<Boolean> walls = checker.check();
		
		Pair<Float, Float> currCordsFloat = interNavigator.getGridCords(interNavigator.getCurrWp());
		Pair<Integer, Integer> currCords = new Pair<Integer, Integer>(
				Math.round(currCordsFloat.first),
				Math.round(currCordsFloat.second));
		
		short nextWp = interNavigator.getNextWp();
		
		
		ArrayList<Short> toAdd = new ArrayList<Short>();
		for (int i = 0; i < 4; i++)  {
			if (walls.get(i) == false)  {
				if (gridGraph[currCords.first + sides.get(i).first]
						[currCords.second + sides.get(i).second] > 0) 
				{
					toAdd.add(gridGraph[currCords.first + sides.get(i).first]
						[currCords.second + sides.get(i).second]);
					walls.set(i, true); // this is so update in interNavigator does not register it as a new waypoint
				}
				else {
					gridGraph[currCords.first + sides.get(i).first]
							[currCords.second + sides.get(i).second] = nextWp;
					
					toAdd.add(nextWp);
					toVisit.add(nextWp);
					
					nextWp++;
				}
			}
		}
		interNavigator.addToGraph(interNavigator.getCurrWp(), toAdd);
		
		interNavigator.update(walls);
	}
}
