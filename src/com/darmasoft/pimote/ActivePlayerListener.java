package com.darmasoft.pimote;

public interface ActivePlayerListener {

	public void activePlayerChanged(boolean status);
	
	public void pauseChanged(boolean paused);
}
