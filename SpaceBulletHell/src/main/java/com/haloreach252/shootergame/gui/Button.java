package com.haloreach252.shootergame.gui;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import com.haloreach252.shootergame.assets.Assets;
import com.haloreach252.shootergame.collision.AABB;
import com.haloreach252.shootergame.collision.Collision;
import com.haloreach252.shootergame.io.Input;
import com.haloreach252.shootergame.io.Keys;
import com.haloreach252.shootergame.render.Camera;
import com.haloreach252.shootergame.render.Shader;
import com.haloreach252.shootergame.render.TileSheet;

public class Button {

	private String eventName;
	private boolean shouldRender = true;
	
	public String eventName() { return eventName; }
	public boolean shouldRender() { return shouldRender; }
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_SELECTED = 1;
	public static final int STATE_CLICKED = 2;
	public static final int STATE_HELD = 3;
	
	private AABB boundingBox;
	
	private int selectedState;
	
	private OnButtonClickEventListener listener;
	
	private static Matrix4f transform = new Matrix4f();
	
	public Button(String buttonEventName, Vector2f position) {
		this.eventName = buttonEventName;
		this.boundingBox = new AABB(position, new Vector2f(64, 64));
		selectedState = STATE_IDLE;
		Gui.buttons.add(this);
	}
	
	public Button(String buttonEventName, Vector2f position, Vector2f scale) {
		this.eventName = buttonEventName;
		this.boundingBox = new AABB(position, scale);
		selectedState = STATE_IDLE;
		Gui.buttons.add(this);
	}
	
	public void resizeButton(Vector2f scale) {
		Vector2f pos = boundingBox.getCenter();
		this.boundingBox = new AABB(pos, scale);
	}
	
	public void registerButtonClickListener(OnButtonClickEventListener listener) {
		this.listener = listener;
	}
	
	public void update(Input input) {
		Collision data = boundingBox.getCollision(input.getMousePosition());
		
		if(data.isIntersecting) {
			selectedState = STATE_SELECTED;
			
			if(input.isMouseButtonReleased(Keys.MOUSE_LEFT)) {
				selectedState = STATE_CLICKED;
				if(listener != null) {
					listener.onButtonClick(eventName);
				}
			}
			
			if(input.isMouseButtonDown(Keys.MOUSE_LEFT)) {
				selectedState = STATE_HELD;
			}
		} else {
			selectedState = STATE_IDLE;
		}
	}
	
	public void render(Camera camera, TileSheet sheet, Shader shader) {
		Vector2f position = boundingBox.getCenter(), scale = boundingBox.getHalfExtent();
		
		transform.identity().translate(position.x, position.y, 0).scale(scale.x, scale.y, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 4, 1);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 7, 1);
			break;
		default:
			sheet.bindTile(shader, 1, 1);
			break;
		}
		Assets.getModel().render();
		
		renderSides(position, scale, camera, sheet, shader);
		renderCorners(position, scale, camera, sheet, shader);
	}
	
	private void renderSides(Vector2f position, Vector2f scale, Camera camera, TileSheet sheet, Shader shader) {
		transform.identity().translate(position.x, position.y + scale.y - 16, 0).scale(scale.x, 16, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 4, 0);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 7, 0);
			break;
		default:
			sheet.bindTile(shader, 1, 0);
			break;
		}
		Assets.getModel().render();
		
		transform.identity().translate(position.x, position.y - scale.y + 16, 0).scale(scale.x, 16, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 4, 2);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 7, 2);
			break;
		default:
			sheet.bindTile(shader, 1, 2);
			break;
		}
		Assets.getModel().render();
		
		transform.identity().translate(position.x - scale.x + 16, position.y, 0).scale(16, scale.y, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 3, 1);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 6, 1);
			break;
		default:
			sheet.bindTile(shader, 0, 1);
			break;
		}
		Assets.getModel().render();
		
		transform.identity().translate(position.x + scale.x - 16, position.y, 0).scale(16, scale.y, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 5, 1);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 8, 1);
			break;
		default:
			sheet.bindTile(shader, 2, 1);
			break;
		}
		Assets.getModel().render();
	}
	
	private void renderCorners(Vector2f position, Vector2f scale, Camera camera, TileSheet sheet, Shader shader) {
		transform.identity().translate(position.x - scale.x + 16, position.y + scale.y - 16, 0).scale(16, 16, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 3, 0);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 6, 0);
			break;
		default:
			sheet.bindTile(shader, 0, 0);
			break;
		}
		Assets.getModel().render();
		
		transform.identity().translate(position.x + scale.x - 16, position.y + scale.y - 16, 0).scale(16, 16, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 5, 0);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 8, 0);
			break;
		default:
			sheet.bindTile(shader, 2, 0);
			break;
		}
		Assets.getModel().render();
		
		transform.identity().translate(position.x - scale.x + 16, position.y - scale.y + 16, 0).scale(16, 16, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 3, 2);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 6, 2);
			break;
		default:
			sheet.bindTile(shader, 0, 2);
			break;
		}
		Assets.getModel().render();
		
		transform.identity().translate(position.x + scale.x - 16, position.y - scale.y + 16, 0).scale(16, 16, 1);
		
		shader.setUniform("projection", camera.getProjection().mul(transform));
		switch(selectedState) {
		case STATE_SELECTED:
			sheet.bindTile(shader, 5, 2);
			break;
		case STATE_CLICKED:
			sheet.bindTile(shader, 8, 2);
			break;
		default:
			sheet.bindTile(shader, 2, 2);
			break;
		}
		Assets.getModel().render();
	}
	
}
