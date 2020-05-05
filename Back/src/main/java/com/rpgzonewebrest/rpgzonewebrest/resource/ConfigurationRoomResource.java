package com.rpgzonewebrest.rpgzonewebrest.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.rpgzonewebrest.dto.RoomConfigDTO;
import com.rpgzonewebrest.dto.RoomConfigProDTO;
import com.rpgzonewebrest.models.room.Room;
import com.rpgzonewebrest.models.user.Normal;
import com.rpgzonewebrest.repository.DataBaseFake;
import com.rpgzonewebrest.rpgzonewebrest.config.RoomConfig;
import com.rpgzonewebrest.service.AuthServices;

@RestController
@RequestMapping(value="configuration/room", produces="application/json")
public class ConfigurationRoomResource {
	
	private static DAO<Room, Long> roomDAO = DataBaseFake.getRoomData();
	private static DAO<Normal, Long> normalDAO = DataBaseFake.getUserData();
	private static RoomConfigDTO roomConfigDTO = new RoomConfigDTO();
	private static RoomConfigProDTO roomConfigProDTO = new RoomConfigProDTO();
	
	@GetMapping
	public ResponseEntity<RoomConfigDTO> configurationPatterns(@RequestHeader String Authorization){
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
		
		if(userLogged.getPro()) {
			return ResponseEntity.ok(roomConfigProDTO);//recursos pro
		}
		return ResponseEntity.ok(roomConfigDTO);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<RoomConfig> setConfigurationRoom(@RequestHeader String Authorization, @PathVariable Long id, @RequestBody RoomConfig roomConfig){
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
		Room room = roomDAO.get(id);
		if(room != null) {
			if( userLogged.getID().equals( room.getAdmin().getID() ) ) {
				room.setRoomConfig(roomConfig);
				roomDAO.update(room);
				return ResponseEntity.ok(roomConfig);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//usuário que não é administrador tentando setar configurações de outra Room
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//Room não encontrada
	}
	
}
