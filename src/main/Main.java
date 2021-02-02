package main; //USE WHEN TESTING IRL

import java.util.ArrayList;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.utility.Delay;
import testing.NavigationTester;

public class Main {
	static public void main(String args[]) throws InterruptedException {
		EV3 EV3Brick = (EV3) BrickFinder.getLocal();
		Keys buttons = EV3Brick.getKeys();
		
		buttons.waitForAnyPress();
		
		RegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.D);
		RegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		
		Wheel leftWheel = WheeledChassis.modelWheel(LEFT_MOTOR, 4.2).offset(-5.54);
		Wheel rightWheel = WheeledChassis.modelWheel(RIGHT_MOTOR, 4.2).offset(5.54);
		
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		
		EV3GyroSensor Gsensor = new EV3GyroSensor(SensorPort.S4);
		Gsensor.reset();
		
		EV3ColorSensor color = new EV3ColorSensor(SensorPort.S1);
		
		MovePilot pilot = new MovePilot(chassis);
		pilot.setAngularSpeed(100);
		MyNavigator nav = new MyNavigator(pilot, LEFT_MOTOR, RIGHT_MOTOR, Gsensor, color);
		
		EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S2);
		WallChecker wallChecker = new WallChecker(nav, irSensor);
		
		InterNavigator interNavigator = new InterNavigator(nav);
		
		PathFinder pathFinder = new PathFinder(interNavigator, wallChecker);
		pathFinder.start();
		
	}
}