package com.rpgzonewebrest.repository;

import com.rpgzonewebrest.dao.DAO;
import com.rpgzonewebrest.dao.NormalDAO;
import com.rpgzonewebrest.dao.RoomDAO;
import com.rpgzonewebrest.models.room.Room;
import com.rpgzonewebrest.models.user.Normal;

public class DataBaseFake {
	private static DAO<Normal, Long> userData = new NormalDAO();
	private static DAO<Room, Long> roomData = new RoomDAO();
	
	public static DAO<Normal, Long> getUserData() {
		return userData;
	}
	public static DAO<Room, Long> getRoomData() {
		return roomData;
	}
	
}
