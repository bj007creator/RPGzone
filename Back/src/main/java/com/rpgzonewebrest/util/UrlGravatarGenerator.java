package com.rpgzonewebrest.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class UrlGravatarGenerator {
	public static String urlGravatar(String email)  {
		try {
			MessageDigest message = MessageDigest.getInstance("MD5");
	       	message.update(email.getBytes(),0,email.length());
	       	BigInteger tempMD5 = new BigInteger(1,message.digest());
	       	String FinalEmail = "https://www.gravatar.com/avatar/"+tempMD5.toString(16)+".jpg";
	       	return FinalEmail;
		} catch(Exception e) {
			e.printStackTrace();
			return "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2FTmRbxm_free-overlay-twitch-red-clipart-png-download-czowiek%2F&psig=AOvVaw1YtlghCnf4UhA9i41issFn&ust=1587773843190000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCNiutIPo_-gCFQAAAAAdAAAAABAK";
		}
	}
}
