package com.rpgzonewebrest.models.game;

import java.io.Serializable;

public class Game  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private int year;
	private String publisher;
	private String language;
	private String gameImage;
	private String pdfGuide;
	
	public Game(String title, int year, String publisher, String language, String gameImage, String pdfGuide) {
		this.title = title;
		this.year = year;
		this.publisher = publisher;
		this.language = language;
		this.gameImage = gameImage;
		this.pdfGuide = pdfGuide;
	}
	public Game() {
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public String getGameImage() {
		return gameImage;
	}

	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}
	public String pdfGuide() {
		return gameImage;
	}

	public void pdfGuide(String pdfGuide) {
		this.pdfGuide = pdfGuide;
	}
	
}
