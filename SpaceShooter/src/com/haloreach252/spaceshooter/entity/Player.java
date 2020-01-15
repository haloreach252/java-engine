package com.haloreach252.spaceshooter.entity;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.haloreach252.spaceshooter.io.Keys;
import com.haloreach252.spaceshooter.io.Window;
import com.haloreach252.spaceshooter.render.Animation;
import com.haloreach252.spaceshooter.render.Camera;
import com.haloreach252.spaceshooter.world.World;

public class Player extends Entity{

	public static final int ANIM_THRUST = 0;
	public static final int ANIM_IDLE = 1;
	public static final int ANIM_SIZE = 2;
	
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_THRUST, new Animation(4, 2, "player/thrust"));
		setAnimation(ANIM_IDLE, new Animation(4, 2, "player/idle"));
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		if(window.getInput().isKeyDown(Keys.A)) movement.add(-10 * delta, 0);
		if(window.getInput().isKeyDown(Keys.D)) movement.add(10 * delta, 0);
		if(window.getInput().isKeyDown(Keys.S)) movement.add(0, -10 * delta);
		if(window.getInput().isKeyDown(Keys.W)) movement.add(0, 10 * delta);
		
		move(movement);
		
		if(movement.x != 0 || movement.y != 0) {
			useAnimation(ANIM_THRUST);
		} else {
			useAnimation(ANIM_IDLE);
		}
		
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
		
	}	
	
}
