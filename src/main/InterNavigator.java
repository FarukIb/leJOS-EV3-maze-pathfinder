package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lejos.hardware.Sound;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class InterNavigator {
	private Map<Short, ArrayList<Short> > graph;
	private Map<Short, Waypoint> translator;
	private MyNavigator pilot;
	private short currWp;
	public final int NORTH = 0;
	public final int EAST = 1;
	public final int SOUTH = 2;
	public final int WEST = 3;
	
	public InterNavigator(MyNavigator nav) {
		translator = new HashMap<Short, Waypoint>();
		translator.put((short) 1, new Waypoint(100, 100));
		
		graph = new HashMap<Short, ArrayList<Short> >();
		for (short i = 0; i < 1000; i++)
			graph.put(i, new ArrayList<Short>());
		
		pilot = nav;
		currWp = 1;
	}
	
	// UTILITY

	public void addToGraph(short a, ArrayList<Short> array)
	{
		graph.put(a, array);
	}
	
	public short getCurrWp() {
		return currWp;
	}
	
	public short getNextWp() {
		return (short) (translator.size() + 1);
	}
	
	public Pair<Float, Float> getGridCords(short num)
	{
		Pair<Float, Float> cords = new Pair<Float, Float>(translator.get(num).x, translator.get(num).y);
		return cords;
	}
	
	// UPDATE
	
	private void addWaypoint(Waypoint toAdd) {
		if (toAdd == null)
			return;
		translator.put((short) (getNextWp()), toAdd);
	}
	
	public void update(ArrayList<Boolean> result) {
		Waypoint northWp = null;
		Waypoint westWp = null;
		Waypoint southWp = null;
		Waypoint eastWp = null;
		
		if (result.get(NORTH) == false)
			northWp = new Waypoint(translator.get(currWp).x, translator.get(currWp).y + 1);
		if (result.get(EAST) == false)
			westWp = new Waypoint(translator.get(currWp).x + 1, translator.get(currWp).y);
		if (result.get(SOUTH) == false)
			southWp = new Waypoint(translator.get(currWp).x, translator.get(currWp).y - 1);
		if (result.get(WEST) == false)
			eastWp = new Waypoint(translator.get(currWp).x - 1, translator.get(currWp).y);
		
		if (northWp != null)
			addWaypoint(northWp);
		if (westWp != null)
			addWaypoint(westWp);
		if (southWp != null)
			addWaypoint(southWp);
		if (eastWp != null)
			addWaypoint(eastWp);
	}
	
	// MOVING FROM TO
	
	private ArrayList<Short> path;
	private Map<Short, Boolean> visited;
	public void moveFromTo(short start, short end) throws InterruptedException {
		path = new ArrayList<Short>();
		
		visited = new HashMap<Short, Boolean>();
		for (short i = 1; i <= 1000; i++)
			visited.put(i, false);
		
		dfs(start, end, new ArrayList<Short>());
		
		move();
		
		currWp = end;
	}
	
	private void move() throws InterruptedException {
		int n = 0;
		
		for (short i : path) {
			if (n == 0)
			{
				n++;
				continue;
			}
			pilot.goTo(translator.get(i));
			n++;
		}
		
	}
	
	private void dfs(short curr, short dest, ArrayList<Short> currPath) {
		currPath.add(curr);
		visited.put(curr, true);
		
		if (curr == dest) {
			path = currPath;
			return;
		}
		
		for (int i = 0; i < graph.get(curr).size(); i++)
			if (visited.get(graph.get(curr).get(i)) != true)
				dfs(graph.get(curr).get(i), dest, new ArrayList<Short>(currPath));
	}
}