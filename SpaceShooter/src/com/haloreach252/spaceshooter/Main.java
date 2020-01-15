package com.haloreach252.spaceshooter;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import com.haloreach252.spaceshooter.assets.Assets;
import com.haloreach252.spaceshooter.gui.Gui;
import com.haloreach252.spaceshooter.io.Timer;
import com.haloreach252.spaceshooter.io.Window;
import com.haloreach252.spaceshooter.render.Camera;
import com.haloreach252.spaceshooter.render.Font;
import com.haloreach252.spaceshooter.render.Shader;
import com.haloreach252.spaceshooter.util.Color;
import com.haloreach252.spaceshooter.util.Debug;
import com.haloreach252.spaceshooter.world.World;

public class Main {

	private Window window;
	
	private final int width = 640;
	private final int height = 640;
	
	private Camera camera;
	
	private Font font;
	
	public void run() {
		init();
		loop();
		
		window.destroy();
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	private void init() {
		Window.setCallbacks();
		
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		window = new Window();
		window.setSize(width, height);
		window.setFullscreen(false);
		window.createWindow("Space Shooter");
	}
	
	private void loop() {
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		camera = new Camera(window.getWidth(), window.getHeight());
		camera.setPosition(new Vector3f(window.getWidth() / 2, window.getHeight() / 2, 0));
		Debug.Log("Camera is at (" + camera.getPosition().x + "," + camera.getPosition().y + ")");
		glEnable(GL_TEXTURE_2D);
		
		try {
			font = new Font("./resources/fonts/times.ttf", 72);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assets.initAsset();
		
		Shader shader = new Shader("shader");
		
		World world = new World(16, window.getWidth(), window.getHeight());
		
		Gui gui = new Gui(window);
		
		Color bgColor = Color.Gray;
		glClearColor(bgColor.red, bgColor.green, bgColor.blue, bgColor.alpha);
		
		double frame_cap = 1.0 / 60.0;
		double frame_time = 0;
		int frames = 0;
		double time = Timer.getTime();
		double unprocessed = 0;
		
		int fpsCounter = 0;
		
		while(!window.shouldClose()) {
			boolean can_render = false;
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			
			time = time_2;
			
			// Everything except rendering
			while(unprocessed >= frame_cap) {
				
				if(window.hasResized()) {
					camera.setProjection(window.getWidth(), window.getHeight());
					gui.resizeCamera(window);
					glViewport(0, 0, window.getWidth(), window.getHeight());
				}
				
				unprocessed -= frame_cap;
				can_render = true;
				
				gui.update(window.getInput());
				
				world.update((float) frame_cap, window, camera);
				
				window.update();
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					fpsCounter++;
					
					
					if(fpsCounter >= 5) {
						System.out.println("FPS: " + frames);
						fpsCounter = 0;
					}
					frames = 0;
				}
			}
			
			// Rendering code
			if(can_render) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer
				
				world.render(shader, camera);
				
				//font.drawText("TEST", 0, 0);
				
				gui.render();
				
				window.swapBuffers();
				frames++;
			}
		}
		
		Assets.deleteAsset();
		
	}

	public static void main(String[] args) {
		new Main().run();
	}

}
