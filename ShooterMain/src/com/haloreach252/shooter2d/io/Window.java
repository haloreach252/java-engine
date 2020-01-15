package com.haloreach252.shooter2d.io;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

//import com.haloreach252.shooter2d.game.Main;

public class Window {
	
	private long window;
	
	private int width, height;
	private boolean fullscreen;
	private boolean hasResized;
	private GLFWWindowSizeCallback windowSizeCallback;
	
	private Input input;
	
	public static void setCallbacks() {
		GLFWErrorCallback.createPrint(System.err).set();
	}
	
	private void setLocalCallbacks() {
		windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long argWindow, int argWidth, int argHeight) {
				width = argWidth;
				height = argHeight;
				hasResized = true;
			}
		};
		
		glfwSetWindowSizeCallback(window, windowSizeCallback);
	}
	
	public Window() {
		setSize(640, 640);
		setFullscreen(false);
		hasResized = false;
	}
	
	public void createWindow(String title) {
		
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		
		if(window == 0) {
			throw new IllegalStateException("Failed to create window!");
		}
		
		if(!fullscreen) {
			// Center the window
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		}
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		glfwSwapInterval(1);
		
		input = new Input(window);
		setLocalCallbacks();
	}
	
	public void destroy() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window); // swap the color buffers
	}
	
	private void getInputs() {
		if(input.isKeyPressed(Keys.ESCAPE)) {
			glfwSetWindowShouldClose(window, true);
		}
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public void update() {
		hasResized = false;
		getInputs();
		input.update();
		glfwPollEvents();
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public boolean getFullscreen() { return fullscreen; }
	public long getWindow() { return window; }
	public Input getInput() { return input; }
	public boolean hasResized() { return hasResized; }
	
}
