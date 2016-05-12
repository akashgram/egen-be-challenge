

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;


public class JacksonUtil  {
	
	public static String objectToJson(User user)
	{
		ObjectMapper mapper = new ObjectMapper();		
		
		try {			
			// convert object to JSON string
			String jsonInString = mapper.writeValueAsString(user);
			return jsonInString;	
		} catch (IOException e) {
			e.printStackTrace();
		}
		   return null;
	}
	
	public static User jsonToObject(String jsonString)
	{
		ObjectMapper mapper = new ObjectMapper();

		try {
			// convert JSON string to Object.			
			User user = mapper.readValue(jsonString, User.class);
			
			return user;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	

}
