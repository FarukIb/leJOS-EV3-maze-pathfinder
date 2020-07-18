package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class PathFinder {
	private Map<Short, ArrayList<Short> > graph;
	private short gridGraph[][];
	private InterNavigator interNavigator;
	private WallChecker checker;
	
	private Stack<Short> toVisit;
	
	public final Pair<Integer, Integer> NORTH = new Pair<Integer, Integer>(-1, 0);
	public final Pair<Integer, Integer> WEST = new Pair<Integer, Integer>(0, 1);
	public final Pair<Integer, Integer> SOUTH = new Pair<Integer, Integer>(1, 0);
	public final Pair<Integer, Integer> EAST = new Pair<Integer, Integer>(0, -1);
	public ArrayList<Pair<Integer, Integer> > sides;
	
	public PathFinder(InterNavigator inter, WallChecker checke) {
		interNavigator = inter;
		checker = checke;
		graph = new HashMap<Short, ArrayList<Short> >();
		gridGraph = new short[110][110];
		ArrayList<Pair<Integer, Integer> > sides = new ArrayList<Pair<Integer, Integer> >();
		sides.add(NORTH);
		sides.add(WEST);
		sides.add(SOUTH);
		sides.add(EAST);
	}
	
	public void start() {
		
	}
	
	private void updateGraph() {
		ArrayList<Boolean> walls = checker.check(interNavigator.getDirection());
		short currWp = interNavigator.getCurrWp();
		Pair<Float, Float> currCordsFloat = interNavigator.getGridCords(currWp);
		Pair<Integer, Integer> currCords = new Pair<Integer, Integer>(
				Math.round(currCordsFloat.first),
				Math.round(currCordsFloat.second));
		short nextWp = interNavigator.getNextWp();
		
		
		ArrayList<Short> toAdd = new ArrayList<Short>();
		for (int i = 0; i < 4; i++)
		{
			if (walls.get(i) == true) {
				if (!(gridGraph[currCords.first + sides.get(i).first]
						[currCords.second + sides.get(i).second] > 0)) {
					toAdd.add(gridGraph[currCords.first + sides.get(i).first]
							[currCords.second + sides.get(i).second]);
				}
				else
				{
					gridGraph[currCords.first + sides.get(i).first]
							[currCords.second + sides.get(i).second] = nextWp;
					toAdd.add(nextWp);
					nextWp++;
				}
			}
		}
		
		
		interNavigator.update(walls);
	}
}
