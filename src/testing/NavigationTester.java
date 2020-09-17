package testing;

import lejos.robotics.navigation.Waypoint;
import main.MyNavigator;

public class NavigationTester {
	private MyNavigator nav;
	
	public NavigationTester(MyNavigator a)
	{
		nav = a;
	}
	
	public void start() throws InterruptedException
	{
		Waypoint a = new Waypoint(0, 30);
		Waypoint b = new Waypoint(30, 30);
		Waypoint c = new Waypoint(30, 0);
		Waypoint d = new Waypoint(0, 0);
		nav.goTo(a);
		nav.goTo(b);
		nav.goTo(c);
		nav.goTo(d);
	}
}
