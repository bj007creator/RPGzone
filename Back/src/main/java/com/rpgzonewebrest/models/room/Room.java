package com.rpgzonewebrest.models.room;

//import utils.Local;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import java.sql.Date;

import com.rpgzonewebrest.models.user.Admin;
import com.rpgzonewebrest.rpgzonewebrest.config.RoomConfig;

public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Admin admin;
	private Long roomID;
	private String roomNick;
	private RoomConfig roomConfig;
	private String roomDescription;
	private List<Long> users = new ArrayList<Long>();
	private static Long roomCounting = new Long(0);
	
	public static Long generateRoomID() {
		roomCounting = new Long( roomCounting + 1 );
		return new Long( roomCounting.longValue() );
	}
	public Room() {
		//construtor vazio necessário
	}
	public Admin getAdmin() {
		return admin;
	}
	
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Long getRoomID() {
		return roomID;
	}
	public void setRoomID(Long iD) {
		roomID = iD;
	}
	public String getRoomNick() {
		return roomNick;
	}
	public void setRoomNick(String roomNick) {
		this.roomNick = roomNick;
	}
	public List<Long> getUsers() {
		return users;
	}
	public void setUsers(List<Long> users) {
		this.users = users;
	}
	
	public String getRoomDescription() {
		return roomDescription;
	}
	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}
	
	public RoomConfig getRoomConfig() {
		return roomConfig;
	}
	public void setRoomConfig(RoomConfig roomConfig) {
		this.roomConfig = roomConfig;
	}
	
	@Override
	public boolean equals(Object room) {
		return this.getRoomID().longValue() == ( (Room) room ).getRoomID().longValue();
	}
	
	@Override
	public String toString() {
		return "admin : " + this.admin + "\n" +
			   "room ID : " + this.roomID + "\n" +
			   "roomNick : " + this.roomNick + "\n";
	}

}
