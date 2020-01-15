package com.haloreach252.shootergame.world;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.haloreach252.shootergame.entity.Entity;
import com.haloreach252.shootergame.entity.Player;
import com.haloreach252.shootergame.entity.Transform;
import com.haloreach252.shootergame.io.Window;
import com.haloreach252.shootergame.render.Camera;
import com.haloreach252.shootergame.render.Shader;

public class World {

	private int scale;
	private Matrix4f world;
	
	private List<Entity> entities;
	
	public World(int scale, int width, int height) {
		this.scale = scale;
		
		this.world = new Matrix4f().setTranslation(new Vector3f(0));
		this.world.scale(scale);
		
		entities = new ArrayList<>();
		
		Transform transform = new Transform();
		transform.pos.x = width / 2;
		transform.pos.y = height / 2;
		System.out.println("Player is at (" + transform.pos.x + "," + transform.pos.y + ")");
		Player player = new Player(transform);
		entities.add(player);
	}
	
	public void render(Shader shader, Camera camera) {
		for(Entity entity : entities) {
			entity.render(shader, camera, this);
		}
	}
	
	public void update(float delta, Window window, Camera camera) {
		for(Entity entity : entities) {
			entity.update(delta, window, camera, this);
		}
	}
	
	public int getScale() {
		return scale;
	}
	
	public Matrix4f getWorldMatrix() {
		return world;
	}
	
}
