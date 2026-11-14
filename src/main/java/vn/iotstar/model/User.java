package vn.iotstar.model;

import java.sql.Date;

public class User extends vn.iotstar.entity.User {

	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	public User(String email, String userName, String fullName, String passWord, String avatar, int roleid,
			String phone, Date createdDate) {
		super(email, userName, fullName, passWord, avatar, roleid, phone, createdDate);
	}

	public User(int id, String email, String userName, String fullName, String passWord, String avatar, int roleid,
			String phone, Date createdDate) {
		super(id, email, userName, fullName, passWord, avatar, roleid, phone, createdDate);
	}
}
