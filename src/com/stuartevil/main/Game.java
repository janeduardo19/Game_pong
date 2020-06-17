package com.stuartevil.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.stuartevil.entities.Ball;
import com.stuartevil.entities.Enemy;
import com.stuartevil.entities.Player;

public class Game extends Canvas implements Runnable,KeyListener{
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static int WIDTH = 160;
	public static int HEIGHT = 120;
	public static int SCALE = 3;
	private Thread thread;
	private boolean isRunning;
	
	private BufferedImage image;
	public static Player player;
	public static Enemy enemy;
	public static Ball ball;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.addKeyListener(this);
		player = new Player(100, HEIGHT-5, 40, 5);
		enemy = new Enemy(100, 0, 40, 5);
		ball = new Ball(100, HEIGHT/2 - 1, 4, 4);
	}
	
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		frame = new JFrame("Game Pong");
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
	}
	
	public void update() {
		player.update();
		enemy.update();
		ball.update();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		player.render(g);
		enemy.render(g);
		ball.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}

	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfUpdates = 60.0;
		double ns = 1000000000 / amountOfUpdates;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " +frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
