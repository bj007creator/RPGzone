package com.rpgzonewebrest.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rpgzonewebrest.dao.DAO;
import com.rpgzonewebrest.dto.RoomDTO;
import com.rpgzonewebrest.models.room.Room;
import com.rpgzonewebrest.models.user.Normal;
import com.rpgzonewebrest.repository.DataBaseFake;

public class RoomServices {
	
	private static DAO<Room, Long> roomDAO = DataBaseFake.getRoomData();
	private static DAO<Normal, Long> normalDAO = DataBaseFake.getUserData();
	
	public static boolean wasPossibleRemoveUser(Long roomID, Long userID, Long idUserLogged) {
		Room room = roomDAO.get(roomID);
		Normal user = normalDAO.get(userID);
		Normal userLogged = normalDAO.get(idUserLogged);
		
		if(room == null || user  == null || userLogged == null || !room.getAdmin().getID().equals( userLogged.getID() ) ) {
			return false;
		}
		List<Long> rooms = user.getRooms();
		List<Long> users = room.getUsers();
		int indexInUser =  rooms.indexOf(room.getRoomID());
		int indexInRoom = users.indexOf(user.getID());
		
		if(indexInUser == -1 || indexInRoom == -1) {
			return false;
		}
		
		rooms.remove(indexInUser);//removendo a sala do usuário
		users.remove(indexInRoom);//removendo o usuário da sala
		
		user.setRooms(rooms);
		room.setUsers(users);
		
		normalDAO.update(user);
		roomDAO.update(room);
		
		return true;
	}
	public static boolean wasPossibleAddUser(Long roomID, Long idUserLogged) {
		Room room = roomDAO.get(roomID);
		Normal userLogged = normalDAO.get(idUserLogged);
		if(room == null ||  userLogged == null || room.getAdmin().getID().equals( userLogged.getID() ) ) {
			return false;
		}
		List<Long> rooms = userLogged.getRooms();//salas do usuário logado
		List<Long> users = room.getUsers();//usuários presentes na sala
		
		if( users.indexOf(userLogged.getID()) != -1  || users.size() > 5 || rooms.size() > 5) {
			return false;
		}
		
		rooms.add(room.getRoomID());
		users.add(userLogged.getID());
		
		userLogged.setRooms(rooms);
		room.setUsers(users);
		
		normalDAO.update(userLogged);
		roomDAO.update(room);
		
		return true;
	}
	
	public static List<RoomDTO> getMyRooms(Normal userLogged) {
		List<Long> rooms = userLogged.getRooms();
		List<RoomDTO> roomsDTO = new ArrayList<RoomDTO>();
		for(Iterator<Long> iterator = rooms.iterator(); iterator.hasNext(); ) {
			roomsDTO.add(new RoomDTO( roomDAO.get( iterator.next() ) ) );
		}
		
		return roomsDTO;
	}
}
