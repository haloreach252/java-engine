package com.haloreach252.shooter2d.world;

public class Tile {

	public static Tile tiles[] = new Tile[255];
	public static byte not = 0;
	
	public static final Tile tileSpace = new Tile("background");
	public static final Tile tileWall = new Tile("sideWall").setSolid();
	
	private byte id;
	private boolean solid;
	private String texture;
	
	public Tile(String texture) {
		this.id = not;
		not++;
		this.texture = texture;
		this.solid = false;
		if(tiles[id] != null) throw new IllegalStateException("Tiles at [" + id + "] is already being used!");
		tiles[id] = this;
	}
	
	public Tile setSolid() {
		this.solid = true;
		return this;
	}
	
	public boolean isSolid() { return solid; }
	public byte getId() { return id; }
	public String getTexture() { return texture; }
	
}
