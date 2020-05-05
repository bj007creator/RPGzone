package com.rpgzonewebrest.rpgzonewebrest.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rpgzonewebrest.authExceptions.ExpiredTokenException;
import com.rpgzonewebrest.authExceptions.InvalidTokenException;
import com.rpgzonewebrest.dao.DAO;
import com.rpgzonewebrest.dto.UserDTO;
import com.rpgzonewebrest.models.user.Normal;
import com.rpgzonewebrest.models.user.User;
import com.rpgzonewebrest.repository.DataBaseFake;
import com.rpgzonewebrest.service.AuthServices;
import com.rpgzonewebrest.util.UrlGravatarGenerator;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserResource {
	
	private DAO<Normal, Long> normalDAO = DataBaseFake.getUserData();
	
	@PostMapping(produces="application/json")
	public ResponseEntity<UserDTO> add(@RequestBody Normal user) {
		//Rota de registro de usuário fazer validação de usuários iguais na base de dados e verificar validade de email
		user.setProfilePicture( UrlGravatarGenerator.urlGravatar(user.getEmail()) );
		user.setPro( false);
		user.setLastLogin(new Date(System.currentTimeMillis()));
		user.setType("Normal");//Definindo as características do usuário default;
		user.setBio("Nothing yet!!! For to update your bio in your profile click on my profile after in edit profile");
		normalDAO.add(user);
		
		UserDTO userDTO = new UserDTO(user);
		//retornando o usuário para verificar
		return ResponseEntity.ok(userDTO);
	}//Deu Certo
	
	@GetMapping(produces="application/json")//@RequestHeader String Authorization colocar dessa maneira se der certo
	public @ResponseBody ResponseEntity<List<UserDTO>> getAll(@RequestHeader String Authorization){//Retorna todos os usuários
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
		List<Normal> list = normalDAO.getAll();
		List<UserDTO> listDTO = new ArrayList<UserDTO>();
		for(Iterator<Normal> iterator = list.iterator(); iterator.hasNext();) {
			listDTO.add(new UserDTO(iterator.next()));
		}
		return ResponseEntity.ok(listDTO);
	}//Deu certo
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO>  get(@PathVariable Long id, @RequestHeader String Authorization) {
		Long idUserLogged;
		try {
			idUserLogged = AuthServices.requireDecryption(Authorization);
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Normal user = normalDAO.get(id);
		if(user == null) {//usuário não encontrado
			return ResponseEntity.notFound().build();
		}
		Normal userLogged = normalDAO.get(idUserLogged);//recuperando os dados do usuário logado caso não esteja logado retorna null
		if(userLogged == null) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		UserDTO userDTO = new UserDTO(user);
		return ResponseEntity.ok(userDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader String Authorization ) {
		Long idUserLogged;
		try {
			idUserLogged = AuthServices.requireDecryption(Authorization);
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Normal user = normalDAO.get(id);
		if(user == null) {//Se o usuário passar um id inexistente na base de dados já bloqueia antes mesmo de verificar se ele está logado ou não
			return ResponseEntity.notFound().build();
		}
		
		Normal userLogged = normalDAO.get(idUserLogged);//recuperando os dados do usuário logado caso não esteja logado retorna null
		if(userLogged == null) {//Opção inacessível se o usuário não estiver logado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		if(!userLogged.getID().equals(id)) {//Não pode dar deletar a conta de outro usuário
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		normalDAO.delete(user);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(produces="application/json")
	public ResponseEntity<UserDTO> update(@RequestBody Normal newUser, @RequestHeader String Authorization){
		Long idUserLogged;
		try {
			idUserLogged = AuthServices.requireDecryption(Authorization);
		} catch(ExpiredTokenException | InvalidTokenException e) {
			e.printStackTrace();//debug
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Normal userLogged = normalDAO.get(idUserLogged);//recuperando os dados do usuário logado caso não esteja logado retorna null
		if(userLogged == null) {//Opção inacessível se o usuário não estiver logado
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Date lastLoginRestored = userLogged.getLastLogin();
		String typeRestored = userLogged.getType();
		
		BeanUtils.copyProperties(newUser, userLogged);
		userLogged.setID(idUserLogged);
		userLogged.setLastLogin(lastLoginRestored);
		userLogged.setType(typeRestored);
		userLogged.setNickname(newUser.getNickName());
		userLogged.setProfilePicture( UrlGravatarGenerator.urlGravatar(userLogged.getEmail()) );
		
		UserDTO userDTO = new UserDTO(userLogged);
		
		normalDAO.update(userLogged);
		
		return ResponseEntity.ok(userDTO);
	}
	
	@GetMapping(value="account/quantity", produces="application/json")
	public ResponseEntity<Long> quantityOfPlayers(@RequestHeader String Authorization){
		return ResponseEntity.ok(User.getUserCounting());
	}
	
}

