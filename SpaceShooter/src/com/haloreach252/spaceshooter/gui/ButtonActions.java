package com.haloreach252.spaceshooter.gui;

import com.haloreach252.spaceshooter.io.Window;

public class ButtonActions implements OnButtonClickEventListener {

	private Window window;
	
	public ButtonActions(Window window) {
		this.window = window;
	}
	
	@Override
	public void onButtonClick(String eventName) {
		switch(eventName) {
		case "closeGameEvent":
			closeGame();
			break;
		case "none":
			System.out.println("Either this button is for testing or useless");
			break;
		default:
			System.out.println("Event fired idk why");
			break;
		}
	}
	
	private void closeGame() {
		window.closeWindow();
	}
}
