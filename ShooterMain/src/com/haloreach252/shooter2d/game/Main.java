package com.haloreach252.shooter2d.game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

import com.haloreach252.shooter2d.assets.Assets;
import com.haloreach252.shooter2d.gui.Gui;
import com.haloreach252.shooter2d.io.Timer;
import com.haloreach252.shooter2d.io.Window;
import com.haloreach252.shooter2d.render.Camera;
import com.haloreach252.shooter2d.render.Shader;
import com.haloreach252.shooter2d.utils.Color;
import com.haloreach252.shooter2d.utils.Colors;
import com.haloreach252.shooter2d.world.TileRenderer;
import com.haloreach252.shooter2d.world.World;

public class Main {

	public static Main instance;
	
	// The window handler
	private Window window;
	
	// Window size
	private final int width = 640;
	private final int height = 640;
	
	private Color bgColor;
	
	private Camera camera;
	
	public void run() {
		instance = this;
		
		init();
		loop();
		
		// Frees the window callbacks and destroy the window
		window.destroy();
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	private void init() {
		setBgColor(Colors.Black);
		
		Window.setCallbacks();
		
		// Initialize GLFW. Most GLFW functions will not work before doing this
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		window = new Window();
		window.setSize(width, height);
		window.setFullscreen(false);
		window.createWindow("Space Shooter");
	}
	
	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tiles = new TileRenderer();
		Assets.initAsset();
		
		Shader shader = new Shader("shader");
		
		World world = new World("level_02", camera);
		world.calculateView(window);
		
		Gui gui = new Gui(window);

		setClearColor(bgColor);
		
		double frame_cap = 1.0 / 60.0;
		double frame_time = 0;
		int frames = 0;
		double time = Timer.getTime();
		double unprocessed = 0;
		
		// Run the rendering loop until the user has attempted to close the window or has pressed the ESCAPE key
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
					world.calculateView(window);
					glViewport(0, 0, window.getWidth(), window.getHeight());
				}
				
				unprocessed -= frame_cap;
				can_render = true;
				
				gui.update(window.getInput());
				
				world.update((float) frame_cap, window, camera);
				
				world.correctCamera(camera, window);
				
				window.update();
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			// Rendering code
			if(can_render) {
				setClearColor(bgColor);
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer
				
				world.render(tiles, shader, camera);
				
				gui.render();
				
				window.swapBuffers();
				frames++;
			}
		}
		
		Assets.deleteAsset();
		
	}
	
	public void setBgColor(Color c) {
		bgColor = c;
	}
	
	private void setClearColor(Color c) {
		glClearColor(c.red, c.green, c.blue, c.alpha);
	}
	
	public static void main(String[] args) {
		new Main().run();
	}

}
