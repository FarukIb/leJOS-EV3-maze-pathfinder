package main;

import java.util.ArrayList;
import java.util.Vector;

import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;

public class WallChecker {
	private MovePilot pilot;
	private EV3IRSensor irSensor;
	private SampleProvider sampleProvider;
	private float [] sample;
	
	public WallChecker(MovePilot setPilot, EV3IRSensor setSens)
	{
		pilot = setPilot;
		irSensor = setSens;
		sampleProvider = irSensor.getDistanceMode();
	}
	
	public ArrayList<Boolean> check(int direction)
	{
		int directionT = direction;
		Boolean a = false, b = false, c = false, d = false;
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for (; directionT < 4; directionT++)
		{
			irSensor.fetchSample(sample, 0);
			if (sample[0] <= 5)
			{
				if (direction == 0)
					a = true;
				if (direction == 1)
					b = true;
				if (direction == 2)
					c = true;
				if (direction == 3)
					d = true;
			}
			pilot.rotate(90.0);
		}
		for (int i = 0; i < direction; i++)
		{
			irSensor.fetchSample(sample, 0);
			if (sample[0] <= 5)
			{
				if (direction == 0)
					a = true;
				if (direction == 1)
					b = true;
				if (direction == 2)
					c = true;
				if (direction == 3)
					d = true;
			}
			pilot.rotate(90.0);
		}
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		
		return list;
	}
}
