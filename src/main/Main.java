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
		
		Wheel leftWheel = WheeledChassis.modelWheel(LEFT_MOTOR, 123.123).offset(123.123);
		Wheel rightWheel = WheeledChassis.modelWheel(RIGHT_MOTOR, 123.123).offset(123.123);
		
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		
		MovePilot pilot = new MovePilot(chassis);
		
		WallChecker wallChecker = new WallChecker(pilot, IRSensor);
		ArrayList<Boolean> walls = wallChecker.check(0);
		if (walls.get(0) == true)
			LCD.drawString("North: yes", 0, 0);
		if (walls.get(1) == true)
			LCD.drawString("East: yes", 10, 0);
		if (walls.get(2) == true)
			LCD.drawString("South: yes", 20, 0);
		if (walls.get(3) == true)
			LCD.drawString("West: yes", 30, 0);
		Delay.msDelay(10000);
	}
}
