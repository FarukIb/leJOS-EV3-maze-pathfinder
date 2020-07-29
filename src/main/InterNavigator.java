package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;

public class InterNavigator {
	private Map<Short, Waypoint> translator;
	private MyNavigator pilot;
	private short currWp;
	private int direction;
	public final int NORTH = 0;
	public final int EAST = 1;
	public final int SOUTH = 2;
	public final int WEST = 3;
	
	public InterNavigator(MyNavigator nav) {
		translator = new HashMap<Short, Waypoint>();
		translator.put((short) 1, new Waypoint(0, 0));
		
		pilot = nav;
		currWp = 1;
		direction = 0;
	}
	
	// UTILITY
	
	public int getDirection() {
		return direction;
	}
	
	public short getCurrWp() {
		return currWp;
	}
	
	public short getNextWp() {
		return (short) (translator.size() + 1);
	}
	
	public Pair<Float, Float> getGridCords(short num)
	{
		Pair<Float, Float> cords = new Pair<Float, Float>(translator.get(num).x / 25 + 100, translator.get(num).y / 25 + 100);
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
		
		if (result.get(NORTH) == true)
			northWp = new Waypoint(translator.get(currWp).x, translator.get(currWp).y + 25);
		if (result.get(EAST) == true)
			westWp = new Waypoint(translator.get(currWp).x + 25, translator.get(currWp).y);
		if (result.get(SOUTH) == true)
			southWp = new Waypoint(translator.get(currWp).x, translator.get(currWp).y - 25);
		if (result.get(WEST) == true)
			eastWp = new Waypoint(translator.get(currWp).x - 25, translator.get(currWp).y);
		
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
	public void moveFromTo(short start, short end, Map<Short, ArrayList<Short> > graph) {
		path = new ArrayList<Short>();
		visited = new HashMap<Short, Boolean>();
		dfs(start, end, new ArrayList<Short>(), graph);
		move(graph);
		currWp = end;
	}
	
	private void updateDirection(int last, int penult)
	{
		direction = pilot.getDir(translator.get(last), translator.get(penult));
	}
	
	private void move(Map<Short, ArrayList<Short> > graph) {
		int n = 0, last = 0, penult = 0;
		
		for (int i : path) {
			if (n == 0)
			{
				n++;
				continue;
			}
			if (n == path.size() - 2)
				penult = i;
			else if (n == path.size() - 1)
				last = i;
			pilot.goTo(translator.get(i));
			n++;
		}
		
		if (last != 0 && penult != 0)
			updateDirection(last, penult);
	}
	
	private void dfs(short curr, short dest, ArrayList<Short> currPath, Map<Short, ArrayList<Short> > graph) {
		currPath.add(curr);
		visited.put(curr, true);
		
		if (curr == dest) {
			path = currPath;
			return;
		}
		
		for (int i = 0; i < graph.get(curr).size(); i++)
			if (visited.get(graph.get(curr).get(i)) != true)
				dfs(graph.get(curr).get(i), dest, new ArrayList<Short>(currPath), graph);
	}
}