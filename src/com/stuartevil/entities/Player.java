package com.stuartevil.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.stuartevil.main.Game;

public class Player extends Entity{
	
	public boolean right, left;
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void update() {
		if(right) {
			x++;
		} else if(left) {
			x--;
		}
		
		if(x+width > Game.WIDTH) {
			x = Game.WIDTH - width;
		} else if(x < 0) {
			x = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(this.getX(), this.getY(), width, height);
	}
}
