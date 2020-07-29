package main;

import java.util.ArrayList;
import java.util.Vector;

import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

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
		sample = new float[sampleProvider.sampleSize()];
	}
	
	public ArrayList<Boolean> check(int direction)
	{
		int directionT = direction;
		Boolean a = false, b = false, c = false, d = false;
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for (int i = 0; i < 4; i++)
			list.add(false);
		for (; directionT < 4; directionT++)
		{
			sampleProvider.fetchSample(sample, 0);
			if (sample[0] >= 10)
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
			Delay.msDelay(500);
		}
		for (int i = 0; i < direction; i++)
		{
			sampleProvider.fetchSample(sample, 0);
			if (sample[0] >= 10)
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
			Delay.msDelay(500);
		}
		list.set(0, a);
		list.set(1, b);
		list.set(2, c);
		list.set(3, d);
		
		return list;
	}
}
