package com.rpgzonewebrest.dto;

import com.rpgzonewebrest.models.game.Game;

public class RoomConfigProDTO extends RoomConfigDTO {
	
	public RoomConfigProDTO() {
		
		super();
		
		super.getGames().add(new Game("Star Wars: Fronteira do Império", 1987, "Wizards of The Coast", "English", "photo.png"));
		
		super.getGameRules().add("- teste de regras de jogo Pro 1  - teste de regras de jogo Pro 1  - teste de regras de jogo Pro 1  - teste de regras de jogo Pro 1  ");
		
		super.getPlots().add("teste de enredo Pro 1  teste de enredo Pro 1  teste de enredo Pro 1  teste de enredo Pro 1  teste de enredo Pro 1  teste de enredo Pro 1  teste de enredo Pro 1  teste de enredo Pro 1  ");
		
		super.getGames().add(new Game("RuneQuest: Roleplaying in Glorantha", 2018, "Chaosium", "English", "https://upload.wikimedia.org/wikipedia/en/a/a4/Cyberpunk2020.jpg"));
		
		super.getGameRules().add("- teste de regras de jogo Pro 2 - teste de regras de jogo Pro 2 - teste de regras de jogo Pro 2 - teste de regras de jogo Pro 2 - teste de regras de jogo Pro 2   !!! ");
		
		super.getPlots().add("teste de enredo Pro 2 teste de enredo Pro 2 teste de enredo Pro 2 teste de enredo Pro 2 teste de enredo Pro 2 teste de enredo Pro 2 ");
	}
}
