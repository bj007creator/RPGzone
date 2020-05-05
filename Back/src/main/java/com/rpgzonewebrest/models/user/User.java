package com.rpgzonewebrest.models.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rpgzonewebrest.models.room.Room;
//import utils.Reader;

public abstract class User implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//User is an abstract concept into app in which he have methods and attributes that are shared with his children and also have methods that are setted howspec comportament to your children
	private Long userID;
	private String password;
	private String type;
	private String nickName;
	private String email;
	private boolean pro;
	private Date lastLogin;
	private String profilePicture;
	private String bio;
	private List<Long> rooms = new ArrayList<Long>();
	private static Long userCounting = new Long(0);//this counter never be same, always to staying auto increment he never gonna be the same
	
	public static Long generateUserID() {
		userCounting = new Long(userCounting.longValue() + 1);
		return userCounting;//auto increment
	}
	
	public abstract void leaveRoom(long roomID);
	
	public abstract void addUser(Room room, Normal user);
	
	public void enterRoom(long roomID) {
		System.out.println("Enter to the room");
	}
	public Object fetchRoom(long roomID) {
		System.out.println("Finding room...");
		return null;
	}
	/*public Room createRoom() {
		Admin adm = new Admin(this.getEmail(), this.getPassword(), this.getnickName(), this.getType(), this.getPro(), this.getID());
		Room room = new Room(adm , Room.generateRoomID(), Reader.readString("room Nick"));
		adm.addRoom(room);
		room.addUser((Normal) this);//adicionando o usu�rio que criou a sala na lista de usu�rios da sala
		this.addRoom(room);//adicionando esta nova sala na lista de salas do usu�rio
		return room;//retorna sala que ser� colocada no RoomDAO no app;
	}
	*/
	public static Long getUserCounting() {
		return new Long(userCounting.longValue());
	}
	public Long getID() {
		return userID;
	}
	public void setID(Long iD) {
		userID = iD;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void getRoomId(Room room) {
		room.getRoomID();
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickname(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getPro() {
		return pro;
	}
	public void setPro(Boolean pro) {
		this.pro = pro;
	}
	public List<Long> getRooms() {
		return this.rooms;
	}
	public void setRooms(List<Long> rooms) {
		this.rooms = rooms;
	}
	@Override
	public boolean equals(Object obj) {
		return ( this.getID().equals(  ( (User) obj ).getID() ) );
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
