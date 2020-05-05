package com.rpgzonewebrest.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rpgzonewebrest.models.room.Room;

public class RoomDAO  implements DAO<Room, Long>{
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream objOut;
	
	public RoomDAO() {
		try {
			file = new File("rooms.dat");
			fos = new FileOutputStream(file);
			objOut = new ObjectOutputStream(fos);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public Room get(Long id) {
		List<Room> rooms = this.getAll();
		Room room = null;
		for(Iterator<Room> iterator = rooms.iterator(); iterator.hasNext();) {
			room = iterator.next();
			if(room.getRoomID().equals(id)) {
				return room;
			}
		}
		return null;
	}
	@Override
	public List<Room> getAll() {
		List<Room> rooms = new ArrayList<Room>();
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream objInput = new ObjectInputStream(fis)){
			while(fis.available() > 0) {
				rooms.add( (Room) objInput.readObject() );
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return rooms;
	}

	@Override
	public void add(Room room) {
		try {
			room.setRoomID(Room.generateRoomID());
			this.objOut.writeObject(room);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Room room) {
		List<Room> rooms = getAll();
		int index = rooms.indexOf(room);
		if(index != -1) {
			rooms.set(index, room);
		}
		saveToFile(rooms);
	}
	@Override
	public void delete(Room room) {
		List<Room> rooms = getAll();
		int index = rooms.indexOf(room);
		if(index != -1) {
			rooms.remove(index);
		}
		saveToFile(rooms);
	}
	
	private void saveToFile(List<Room> rooms) {
		try {
			close();
			file.delete();//deletar base de dados para regravar os dados atualizados
			fos = new FileOutputStream(file.getName(), true);
			objOut = new ObjectOutputStream(fos);
			for(Room room : rooms) {
				objOut.writeObject(room);
			}
			objOut.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void close() throws IOException {
		objOut.close();
		fos.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
	}
}
