package com.stuartevil.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.stuartevil.main.Game;

public class Ball extends Entity{
	public double dx, dy, speed = 1.7;
	
	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		int angle = new Random().nextInt(120 - 45) + 45;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void update() {
		if(x + (dx*speed) + width >= Game.WIDTH) {
			dx*=-1;
		} else if(x + (dx*speed) + width < 0) {
			dx*=-1;
		}
		
		if(y >= Game.HEIGHT) {
			//Ponto do Inimigo.
			new Game();
			return;
		} else if(y < 0) {
			//Ponto do Jogador.
			new Game();
			return;
		}
		
		Rectangle bounds = new Rectangle((int)(x+(dx*speed)), (int)(y+(dy*speed)), width, height);
		
		Rectangle boundsPlayer = new Rectangle(Game.player.getX(), Game.player.getY(), Game.player.getWidth(), Game.player.getHeight());
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);
		
		if(bounds.intersects(boundsPlayer)) {
			int angle = new Random().nextInt(120 - 45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy > 0)
				dy*=-1;
		} else if(bounds.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(120 - 45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy < 0)
				dy*=-1;
		}
		
		x+=dx*speed;
		y+=dy*speed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(this.getX(), this.getY(), width, height);
	}
}
