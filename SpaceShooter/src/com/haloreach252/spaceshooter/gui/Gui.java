package com.haloreach252.spaceshooter.gui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.haloreach252.spaceshooter.io.Input;
import com.haloreach252.spaceshooter.io.Window;
import com.haloreach252.spaceshooter.render.Camera;
import com.haloreach252.spaceshooter.render.Shader;
import com.haloreach252.spaceshooter.render.TileSheet;

public class Gui {
	
	private Shader shader;
	private Camera camera;
	private TileSheet sheet;
	
	private static OnButtonClickEventListener listener;
	
	public static List<Button> buttons;
	
	public Gui(Window window) {
		buttons = new ArrayList<>();
		
		shader = new Shader("gui");
		camera = new Camera(window.getWidth(), window.getHeight());
		sheet = new TileSheet("gui.png", 9);
		
		listener = new ButtonActions(window);
		
		initButtons(window);
		
		for(Button button : buttons) {
			button.registerButtonClickListener(listener);
		}
	}
	
	@SuppressWarnings("unused")
	private void initButtons(Window window) {
		Button closeGameButton = new Button("closeGameEvent", new Vector2f(window.getWidth() / 2 - 64, window.getHeight() / 2 - 64));
		Button testButton = new Button("none", new Vector2f(-window.getWidth() / 2 + 64, window.getHeight() / 2 - 64));
		Button testButton2 = new Button("none", new Vector2f(window.getWidth() / 2 - 64, -window.getHeight() / 2 + 64));
	}
	
	public void resizeCamera(Window window) {
		camera.setProjection(window.getWidth(), window.getHeight());
	}
	
	public void update(Input input) {
		for(Button button : buttons) {
			button.update(input);
		}
	}
	
	public void render() {
		shader.bind();
		for(Button button : buttons) {
			if(button.shouldRender()) {
				button.render(camera, sheet, shader);
			}
		}
	}
	
}
