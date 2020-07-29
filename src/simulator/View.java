package simulator;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class View extends JFrame {
	
	public View() {
		setTitle("Maze man");
		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	public void paint(Graphics g)
	{
		Color color = Color.BLACK;
		g.setColor(color);
		g.fillRect(30, 30, 50, 50);
	}
	
	public static void main(String args[])
	{
		View view = new View();
		view.setVisible(true);
		
	}
}
