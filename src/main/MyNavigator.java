package main;

import dummies.DummyPilot;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Waypoint;
import lejos.utility.Delay;

public class MyNavigator {
	private MovePilot pilot;
	private int direction;
	private Waypoint currWp;
	private float toBe;
	
	//FOR TURNING
	private RegulatedMotor LEFT_MOTOR;
	private RegulatedMotor RIGHT_MOTOR;
	private EV3GyroSensor sensor;
	
	private SampleProvider provider;
	private float[] sample;
	
	//FOR GO FORWARD
	private EV3ColorSensor color;
	private float[] sample1;
	private SampleProvider sampleP;
	
	public MyNavigator(MovePilot pilot2, RegulatedMotor LEFT_MOTOR2, RegulatedMotor RIGHT_MOTOR2, EV3GyroSensor gyro, EV3ColorSensor color1)
	{
		toBe = 0;
		
		sensor = gyro;
		provider = sensor.getAngleMode();
		sample = new float[provider.sampleSize()];
		
		LEFT_MOTOR = LEFT_MOTOR2;
		RIGHT_MOTOR = RIGHT_MOTOR2;
		
		currWp = new Waypoint(100, 100);
		pilot = pilot2;
		direction = 0;
		
		color = color1;
		sampleP = color.getColorIDMode();
		sample1 = new float[sampleP.sampleSize()];
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public void honeIn()
	{
		LEFT_MOTOR.setSpeed(30);;
		RIGHT_MOTOR.setSpeed(30);
		
		while (true)
		{
			provider.fetchSample(sample, 0);
			if (Math.abs(sample[0] - toBe) < 0.5)
				break;
			else if (sample[0] > toBe)
			{
				LEFT_MOTOR.startSynchronization();
				RIGHT_MOTOR.backward();
				LEFT_MOTOR.forward();
				LEFT_MOTOR.endSynchronization();
			}
			else if (sample[0] < toBe)
			{
				LEFT_MOTOR.startSynchronization();
				RIGHT_MOTOR.forward();
				LEFT_MOTOR.backward();
				LEFT_MOTOR.endSynchronization();
			}
			Delay.msDelay(1);
		}
		LEFT_MOTOR.startSynchronization();
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		LEFT_MOTOR.endSynchronization();
	}
	
	public void turnLeft() throws InterruptedException
	{	
		LEFT_MOTOR.setSpeed(175);
		RIGHT_MOTOR.setSpeed(175);
		
		toBe += 90;
		
		LEFT_MOTOR.startSynchronization();
		RIGHT_MOTOR.forward();
		LEFT_MOTOR.backward();
		LEFT_MOTOR.endSynchronization();
		while (true) 
		{
			provider.fetchSample(sample, 0);
			if (sample[0] >= toBe)
				break;
			Delay.msDelay(1);
		}
		LEFT_MOTOR.startSynchronization();
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		LEFT_MOTOR.endSynchronization();
		
		Delay.msDelay(500);
		
		direction--;
		if (direction == -1)
			direction = 3;
	}
	
	private int turnLeftSim(int toTurn)
	{
		int cnt = 0;
		int fakeDir = direction;
		while (fakeDir != toTurn)
		{
			fakeDir--;
			cnt++;
			if (fakeDir == -1)
				fakeDir = 3;
		}
		return cnt;
	}
	
	public void turnRight() throws InterruptedException
	{
		LEFT_MOTOR.setSpeed(175);
		RIGHT_MOTOR.setSpeed(175);
		
		toBe -= 90;
		
		LEFT_MOTOR.startSynchronization();
		RIGHT_MOTOR.backward();
		LEFT_MOTOR.forward();
		LEFT_MOTOR.endSynchronization();
		while (true)
		{
			provider.fetchSample(sample, 0);
			if (sample[0] <= toBe)
				break;
			Delay.msDelay(1);
		}
		LEFT_MOTOR.startSynchronization();
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		LEFT_MOTOR.endSynchronization();
		
		Delay.msDelay(500);
		
		direction++;
		if (direction == 4)
			direction = 0;
	}
	
	private int turnRightSim(int toTurn)
	{
		int cnt = 0;
		int fakeDir = direction;
		while (fakeDir != toTurn)
		{
			fakeDir++;
			cnt++;
			if (fakeDir == 4)
				fakeDir = 0;
		}
		return cnt;
	}
	
	private void goForward()
	{
		honeIn();
		
		Delay.msDelay(1000);
		
		LEFT_MOTOR.setSpeed(200);
		RIGHT_MOTOR.setSpeed(200);
		
		LEFT_MOTOR.startSynchronization();
		RIGHT_MOTOR.forward();
		LEFT_MOTOR.forward();
		LEFT_MOTOR.endSynchronization();
		
		while (true)
		{
			sampleP.fetchSample(sample1, 0);
			
			if (sample1[0] == Color.BLACK)
				break;
		}
		Sound.beep();
		 
		LEFT_MOTOR.startSynchronization();
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		LEFT_MOTOR.endSynchronization();
		
		pilot.setLinearSpeed(15);
		pilot.travel(8);
	}
	
	public int getDir(Waypoint a, Waypoint from)
	{
		if (a.y > from.y)
			return 0;
		else if (a.x > from.x)
			return 1;
		else if (a.y < from.y)
			return 2;
		else
			return 3;
	}
	
	public void goTo(Waypoint toGo) throws InterruptedException
	{
		int toTurn = getDir(toGo, currWp);
		if (turnRightSim(toTurn) < turnLeftSim(toTurn))
		{
			while (direction != toTurn)
				turnRight();
		}
		else
		{
			while (direction != toTurn)
				turnLeft();
		}
		
		goForward();
		currWp = toGo;
		Delay.msDelay(1000);
	}
}
