package com.rpgzonewebrest.dao;

import com.rpgzonewebrest.models.user.Normal;
import com.rpgzonewebrest.models.user.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.ArrayList;

public class NormalDAO implements DAO<Normal, Long>{
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;
	
	public NormalDAO() {
		try {
			file = new File("users.dat");
			fos = new FileOutputStream("users.dat", true);
			outputFile = new ObjectOutputStream(fos);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public Normal get(Long id) {
		Normal user = null;
		if(id == null) {//se o id for null já sai do método de uma vez
			return null;
		}
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)){
			while(fis.available() > 0) {// 
			 	user = (Normal) inputFile.readObject();
			 	if(id.equals(user.getID())) {
			 		return user;
			 	}
			 	
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Normal> getAll(){
		List<Normal> users = new ArrayList<Normal>();
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)){
			while(fis.available() > 0) {
				users.add( (Normal) inputFile.readObject() );//adicionando usu�rio na lista de retorno
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	@Override
	public void add(Normal user) {
		try {
			user.setID(User.generateUserID());
			this.outputFile.writeObject(user);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Normal user) {
		List<Normal> users = getAll();
		int index = users.indexOf(user);
		if(index != -1) {
			users.set(index, user);
		}
		saveToFile(users);
	}
	@Override
	public void delete(Normal user) {
		List<Normal> users = getAll();
		int index = users.indexOf(user);
		if(index != -1) {
			users.remove(index);
		}
		saveToFile(users);
	}
	
	private void saveToFile(List<Normal> users) {
		try {
			close();
			file.delete();//deletar base de dados para regravar os dados atualizados
			fos = new FileOutputStream(file.getName(), true);
			outputFile = new ObjectOutputStream(fos);
			for(Normal user : users) {
				outputFile.writeObject(user);
			}
			outputFile.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
	}
}
