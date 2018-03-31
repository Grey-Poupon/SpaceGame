package com.project;

import java.awt.Graphics;

public interface Phase {
		public void tick();
		public void render(Graphics g);
		public MouseInput getMouseInput();
		public KeyInput  getKeyInput();
		public void addListeners(Main main);
}
