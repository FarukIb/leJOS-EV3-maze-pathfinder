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
		Navigator navigator = new Navigator(pilot);
		
		Waypoint a = new Waypoint(0, 25.0);
		Waypoint b = new Waypoint(25.0, 25.0);
		Waypoint c = new Waypoint(25.0, 0);
		Waypoint d = new Waypoint(0, 0);
		
		pilot.forward();
		Delay.msDelay(500);
		pilot.stop();
		
		navigator.goTo(a);
		navigator.goTo(b);
		navigator.goTo(c);
		navigator.goTo(d);
	}
}
