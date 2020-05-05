package com.rpgzonewebrest.dto;

import com.rpgzonewebrest.models.room.Room;
import com.rpgzonewebrest.rpgzonewebrest.config.RoomConfig;

public class RoomDTO {
	private String roomID;
	private String roomNick;
	private String qtdeUsersInRoom;
	private RoomConfig roomConfig;
	private String roomDescription;
	
	public RoomDTO(Room room) {
		this.setRoomID( room.getRoomID().toString() );
		this.setRoomNick(room.getRoomNick());
		this.setQtdeUsersInRoom( String.valueOf(room.getUsers().size()) );
		this.setRoomConfig( room.getRoomConfig() );
		this.setRoomDescription( room.getRoomDescription() );
	}
	
	public String getRoomID() {
		return roomID;
	}
	public String getRoomNick() {
		return roomNick;
	}

	public String getQtdeUsersInRoom() {
		return qtdeUsersInRoom;
	}
	
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	public void setRoomNick(String roomNick) {
		this.roomNick = roomNick;
	}

	public void setQtdeUsersInRoom(String qtde) {
		this.qtdeUsersInRoom = qtde;
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
}
