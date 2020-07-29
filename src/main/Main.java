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
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.utility.Delay;

public class Main {
	static public void main(String args[]) {
		Delay.msDelay(1000);
		
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);
		EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		
		Wheel leftWheel = WheeledChassis.modelWheel(LEFT_MOTOR, 4.2).offset(-5.3);
		Wheel rightWheel = WheeledChassis.modelWheel(RIGHT_MOTOR, 4.2).offset(5.3);
		
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		
		MovePilot pilot = new MovePilot(chassis);
		MyNavigator nav = new MyNavigator(pilot);
		
		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S1);
		WallChecker wallChecker = new WallChecker(pilot, irSensor);
		
		InterNavigator interNavigator = new InterNavigator(nav);
		
		PathFinder pathFinder = new PathFinder(interNavigator, wallChecker);
		pathFinder.start();
	}
}
