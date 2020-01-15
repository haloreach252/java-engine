package com.haloreach252.shooter2d.gui;

import org.joml.Vector2f;

import com.haloreach252.shooter2d.io.Input;
import com.haloreach252.shooter2d.io.Window;
import com.haloreach252.shooter2d.render.*;

public class Gui {

	private Shader shader;
	private Camera camera;
	private TileSheet sheet;
	
	private Button temp;
	
	public Gui(Window window) {
		shader = new Shader("gui");
		camera = new Camera(window.getWidth(), window.getHeight());
		sheet = new TileSheet("gui.png", 9);
		temp = new Button(new Vector2f(-4, -4), new Vector2f(16, 16));
	}
	
	public void resizeCamera(Window window) {
		camera.setProjection(window.getWidth(), window.getHeight());
	}
	
	public void update(Input input) {
		temp.update(input);
	}
	
	public void render() {
		shader.bind();
		temp.render(camera, sheet, shader);
	}
	
}
