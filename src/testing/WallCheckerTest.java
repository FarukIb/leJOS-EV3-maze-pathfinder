/*package testing;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;
import main.WallChecker;

public class WallCheckerTest {
	private TextLCD lcd;
	private WallChecker wallChecker;
	
	public WallCheckerTest(WallChecker a, TextLCD textLCD) {
		lcd = textLCD;
		wallChecker = a;
	}
	
	public void start() throws InterruptedException
	{
		ArrayList<Boolean> walls = wallChecker.check(0);
		LCD.drawString("N: " + walls.get(0).toString(), 0, 0);
		Delay.msDelay(5000);
		LCD.clearDisplay();
		LCD.drawString("E: " + walls.get(1).toString(), 0, 0);
		Delay.msDelay(5000);
		LCD.clearDisplay();
		LCD.drawString("S: " + walls.get(2).toString(), 0, 0);
		Delay.msDelay(5000);
		LCD.clearDisplay();
		LCD.drawString("W: " + walls.get(3).toString(), 0, 0);
	}
}
*/