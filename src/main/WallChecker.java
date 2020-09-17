package main;

import java.util.ArrayList;
import java.util.Vector;

import dummies.DummyPilot;
import dummies.DummySensor;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class WallChecker {
	private MovePilot pilot;
	private EV3IRSensor irSensor;
	private SampleProvider sampleProvider;
	private float [] sample;
	
	public WallChecker(MovePilot pilot2, EV3IRSensor irSensor2)
	{
		pilot = pilot2;
		irSensor = irSensor2;
		sampleProvider = irSensor.getDistanceMode(); //IRL TEST DECOMMENT
		sample = new float[sampleProvider.sampleSize()];
	}
	
	public ArrayList<Boolean> check(int direction) throws InterruptedException
	{
		int directionT = direction;
		Boolean a = false, b = false, c = false, d = false;
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for (int i = 0; i < 4; i++)
			list.add(false);
		for (; directionT < 4; directionT++)
		{
			sampleProvider.fetchSample(sample, 0);
			//sample = irSensor.fetchSample();
			if (sample[0] <= 35)
			{
				if (directionT == 0)
				{
					a = true;
					Sound.beep();
				}
				if (directionT == 1)
				{
					b = true;
					Sound.beep();
				}
				if (directionT == 2)
				{
					c = true;
					Sound.beep();
				}
				if (directionT == 3)
				{
					d = true;
					Sound.beep();
				}
			}
			pilot.rotate(90);
			Delay.msDelay(500);
		}
		for (int i = 0; i < direction; i++)
		{
			sampleProvider.fetchSample(sample, 0);
			//sample = irSensor.fetchSample();
			if (sample[0] <= 35)
			{
				if (i == 0)
				{
					a = true;
					Sound.beep();
				}
				if (i == 1)
				{
					b = true;
					Sound.beep();
				}
				if (i == 2)
				{
					c = true;
					Sound.beep();
				}
				if (i == 3)
				{
					d = true;
					Sound.beep();
				}
			}
			pilot.rotate(90);
			Delay.msDelay(100);
		}
		list.set(0, a);
		list.set(1, b);
		list.set(2, c);
		list.set(3, d);
		
		return list;
	}
}
