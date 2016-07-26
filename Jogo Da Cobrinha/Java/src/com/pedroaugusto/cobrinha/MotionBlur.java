/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedroaugusto.cobrinha;

/**
 *
 * @author Pedro
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class MotionBlur
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setTitle("Motion Blur with Graphics2D | Vallentin Source");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JComponent c = new JComponent()
		{
			int x = 0;
			
			@Override
			public void paint(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				
				g2d.setColor(new Color(0, 0, 0, 55));
				g2d.fillRect(0, 0, getWidth(), getHeight());
				//g2d.clearRect(0, 0, getWidth(), getHeight());
				
				g2d.setColor(Color.RED);
				g2d.fillOval(x - 40, /*getHeight() / 2 - 40 + (int) (Math.cos(Math.toRadians(x)) * 20)*/x, 80, 80);
				
				x += 10;
				
				if (x - 50 > getWidth())
				{
					x = -50;
				}
				
				try { Thread.sleep(10); } catch (Exception ex) {}
				
				repaint();
			}
		};
		
		c.setOpaque(true);
		
		frame.add(c);
		
		
		
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
