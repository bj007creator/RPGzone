package com.rpgzonewebrest.rpgzonewebrest.config;

import java.io.Serializable;

import com.rpgzonewebrest.models.game.Game;

public class RoomConfig  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	private String gameRules;
	private String plot;
	
	public RoomConfig() {
		
	}
	
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getGameRules() {
		return gameRules;
	}
	public void setGameRules(String gameRules) {
		this.gameRules = gameRules;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
}
