

import java.util.List;


public class UserService {
	
	public static String createUser(User user) {
		return UserMongoDB.insertUser(user);
	}
	
	public static String updateUser(User user) {	
		return UserMongoDB.updateUser(user);
	}
	
	public static List<String> getUsers() {	
		return UserMongoDB.getAllUsers();
	}
}
