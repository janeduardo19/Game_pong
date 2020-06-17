package com.stuartevil.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.stuartevil.main.Game;

public class Enemy extends Entity{
	
	public Enemy(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void update() {
		x += (Game.ball.x - x - 6) * 0.07;
		
		if(x+width > Game.WIDTH) {
			x = Game.WIDTH - width;
		} else if(x < 0) {
			x = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(this.getX(), this.getY(), width, height);
	}
}
