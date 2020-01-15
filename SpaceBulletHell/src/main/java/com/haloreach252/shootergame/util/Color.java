package com.haloreach252.shootergame.util;

public class Color {

	public static final Color Red = new Color(1, 0, 0, 0);
	public static final Color Green = new Color(0, 1, 0, 0);
	public static final Color Blue = new Color(0, 0, 1, 0);
	public static final Color Black = new Color(0, 0, 0, 0);
	public static final Color Yellow = new Color(1, 1, 0, 0);
	public static final Color Purple = new Color(1, 0, 1, 0);
	public static final Color Orange  = new Color(1, 0.5f, 0, 0);
	public static final Color White  = new Color(1, 1, 1, 0);
	public static final Color Gray  = new Color(0.3f, 0.3f, 0.3f, 0);
	public static final Color Cyan  = new Color(0, 1, 1, 0);
	
	public float red = 0.0f;
	public float green = 0.0f;
	public float blue = 0.0f;
	public float alpha = 0.0f;
	
	public Color(float r, float g, float b, float a) {
		red = r;
		green = g;
		blue = b;
		alpha = a;
	}
	
}
