package model;

import model.dao.ProductDAO;

public class Administrator extends User {

	private String nickname;

	public Administrator(String nickname, String username, String password, String email, int age) {
		super(nickname, null, username, password, email, age);	
	}

	public void setNickname(String nickname) {
		if (nickname != null && !nickname.isEmpty()) {
			this.nickname = nickname;
		}
	}

//	boolean isAdmin() {
//		return true;
//	}
	
}