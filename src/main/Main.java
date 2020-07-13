package main;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Main {
	static public void main(String args[])
	{
		Delay.msDelay(5000);
		EV3IRSensor IRSensor = new EV3IRSensor(SensorPort.S1);
		
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);
		EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		
		Wheel leftWheel = WheeledChassis.modelWheel(LEFT_MOTOR, 4.2).offset(-5.5);
		Wheel rightWheel = WheeledChassis.modelWheel(RIGHT_MOTOR, 4.2).offset(5.5);
		
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		
		MovePilot pilot = new MovePilot(chassis);
		
		WallChecker wallChecker = new WallChecker(pilot, IRSensor);
		ArrayList<Boolean> walls = wallChecker.check(0);
		if (walls.get(0) == true)
			LCD.drawString("North: yes", 0, 0);
		else
			LCD.drawString("North: no", 0, 0);
		if (walls.get(1) == true)
			LCD.drawString("East: yes", 0, 15);
		else
			LCD.drawString("East: no", 0, 15);
		if (walls.get(2) == true)
			LCD.drawString("South: yes", 0, 30);
		else
			LCD.drawString("South: no", 0, 30);
		if (walls.get(3) == true)
			LCD.drawString("West: yes", 0, 45);
		else
			LCD.drawString("West: no", 0, 45);
		Delay.msDelay(10000);
	}
}
