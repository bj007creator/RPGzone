package com.rpgzonewebrest.rpgzonewebrest.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rpgzonewebrest.authExceptions.ExpiredTokenException;
import com.rpgzonewebrest.authExceptions.InvalidTokenException;
import com.rpgzonewebrest.dao.DAO;
import com.rpgzonewebrest.dto.RoomDTO;
import com.rpgzonewebrest.models.room.Room;
import com.rpgzonewebrest.models.user.Admin;
import com.rpgzonewebrest.models.user.Normal;
import com.rpgzonewebrest.repository.DataBaseFake;
import com.rpgzonewebrest.service.AuthServices;
import com.rpgzonewebrest.service.RoomServices;

@RestController
@RequestMapping("/rooms")
public class RoomResource {
	
	private DAO<Room, Long> roomDAO = DataBaseFake.getRoomData();
	private DAO<Normal, Long> normalDAO = DataBaseFake.getUserData();
	
	@GetMapping(produces="application/json")
	public ResponseEntity<List<RoomDTO>> getAll(@RequestHeader String Authorization){
		try {
			AuthServices.requireDecryption(Authorization);//testando se o token está válido
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<Room> rooms = roomDAO.getAll();
		System.out.println(rooms);
		List<RoomDTO> roomsDTO = new ArrayList<RoomDTO>();
		for(Iterator<Room> iterator = rooms.iterator(); iterator.hasNext();) {
			roomsDTO.add( new RoomDTO( iterator.next() ) );
		}
		return ResponseEntity.ok(roomsDTO);
	}
	
	@PostMapping(produces="application/json")
	public ResponseEntity<RoomDTO> registerRoom(@RequestHeader String Authorization, @RequestBody Room room){
		Long idUserLogged;
		try {
			idUserLogged = AuthServices.requireDecryption(Authorization);
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Normal userLogged = normalDAO.get(idUserLogged);//recuperando os dados do usuário logado caso não esteja logado retorna null
		if(userLogged == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Admin admin = new Admin();
		
		BeanUtils.copyProperties(userLogged, admin);
		
		admin.setID( userLogged.getID() );
		
		room.setAdmin(admin);
		//Posteriormente que o usuário irá definir o jogo, o enredo e as regras do jogo em outro processo
		
		List<Long> rooms = userLogged.getRooms();
		if( rooms.size() <= 5 ) {
			roomDAO.add(room);
			
			rooms.add(room.getRoomID());
			
			userLogged.setRooms(rooms);
			
			normalDAO.update(userLogged);
			
			return ResponseEntity.ok( new RoomDTO(room) );
		}
		return ResponseEntity.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED).build();
		
	}
	
	@PostMapping("/{roomID}")
	public ResponseEntity<Void> addUserInRoom(@RequestHeader String Authorization,  @PathVariable Long roomID ){
		Long idUserLogged;
		try {
			idUserLogged = AuthServices.requireDecryption(Authorization);
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		if( RoomServices.wasPossibleAddUser(roomID,  idUserLogged) ) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	} 
	
	@GetMapping("/myrooms")
	public ResponseEntity<List<RoomDTO>> myRooms(@RequestHeader String Authorization){
		Long idUserLogged;
		try {
			idUserLogged = AuthServices.requireDecryption(Authorization);
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Normal userLogged = normalDAO.get(idUserLogged);//recuperando os dados do usuário logado caso não esteja logado retorna null
		if(userLogged == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok( RoomServices.getMyRooms(userLogged) );
		
	}
	
	@DeleteMapping("/{roomID}/{userID}")
	public ResponseEntity<Void> deleteUser(@RequestHeader String Authorization, @PathVariable Long roomID, @PathVariable Long userID){
		Long idUserLogged;
		try {
			idUserLogged = AuthServices.requireDecryption(Authorization);
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		if( RoomServices.wasPossibleRemoveUser(roomID, userID, idUserLogged) ) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
