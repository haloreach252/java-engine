package com.haloreach252.spaceshooter.io;

import static org.lwjgl.glfw.GLFW.*;
import org.joml.Vector2f;

public class Input {

private long window;
	
	private boolean[] keys;
	private boolean[] buttons;
	
	private static Vector2f mousePos = new Vector2f();
	private static double[] x = new double[1], y = new double[1];
	private static int[] winWidth = new int[1], winHeight = new int[1];
	
	public Input(long window) {
		this.window = window;
		keys = new boolean[GLFW_KEY_LAST];
		buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
	}
	
	public boolean isKeyDown(int key) {
		return glfwGetKey(window, key) == 1;
	}
	
	public boolean isKeyPressed(int key) {
		return isKeyDown(key) && !keys[key];
	}
	
	public boolean isKeyReleased(int key) {
		return !isKeyDown(key) && keys[key];
	}
	
	public boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}
	
	public boolean isMouseButtonPressed(int button) {
		return isMouseButtonDown(button) && !buttons[button];
	}
	
	public boolean isMouseButtonReleased(int button) {
		return !isMouseButtonDown(button) && buttons[button];
	}
	
	public Vector2f getMousePosition() {
		glfwGetCursorPos(window, x, y);
		
		glfwGetWindowSize(window, winWidth, winHeight);
		
		mousePos.set((float) x[0] - (winWidth[0] / 2.0f), -((float) y[0] - (winHeight[0] / 2.0f)));
		
		return mousePos;
	}
	
	public void update() {
		for(int i = 32; i < GLFW_KEY_LAST; i++) {
			keys[i] = isKeyDown(i);
		}
		for(int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) {
			buttons[i] = isMouseButtonDown(i);
		}
	}
	
}
