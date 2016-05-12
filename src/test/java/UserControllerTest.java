

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import spark.utils.IOUtils;


public class UserControllerTest{
	
	//id set to 
	private static String id = "5733de81d4c6c63c98a34433";
	private Date d;
	
//	//Calling POST method
//	@Test
//	public void addNewUser() {
//        
//		User reqbody = new User();
//		d = new Date();
//	    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
//	    String dateCreated = format.format(d);
//		reqbody.setFirstName("coutinho");
//		reqbody.setDateCreated(dateCreated);
//		reqbody.setProfilePic("http://lorempixel.com/640/480/people");
//		reqbody.setLastName("soornahalii");
//		reqbody.setEmail("akashgram@gmail.com");
//		
//		Address address = new Address();
//		address.setStreet("193 Talon Valley");
//		address.setCity("South Tate furt");		
//		address.setState("47069");
//		address.setZip("IA");
//		address.setCountry("US");		
//		reqbody.setAddress(address);
//		
//		
//		Company company = new Company();
//		company.setName("Denesik Group");
//		company.setWebsite("http://jodie.org");
//		reqbody.setCompany(company);
//		
//		
//		
//		
//		String  res = request("POST", "/user", reqbody);
//		System.out.println("Res: " + reqbody);
//        User responseUser = JacksonUtil.jsonToObject(res);
//		assertNotNull(responseUser.getId());
//		id = responseUser.getId();
//	}
	
	//Calling PUT method
	@Test
	public void updateUser() {
		
		User reqbody = new User();
		reqbody.setId(id);
		reqbody.setFirstName("Akash");
		d = new Date();
	    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
	    String dateCreated = format.format(d);
		reqbody.setProfilePic("http://lorempixel.com/640/480/people");
		reqbody.setLastName("gopal");
		reqbody.setDateCreated(dateCreated);
		Address address = new Address();
		
		address.setStreet("Richmond Avenue");
		address.setCity("Bangalore");		
		address.setState("47069");
		address.setZip("karantaka");
		address.setCountry("india");		
		reqbody.setAddress(address);
		
		Company company = new Company();
		company.setName("ooooooooooooooooo");
		company.setWebsite("http://google.org");
		reqbody.setCompany(company);
		reqbody.setEmail("Darby_Leffler68@gmail.com");
		String result = request("PUT","/UpdateUser",reqbody);
	
		assertNotNull(result);
	}

	
	private String request(String method, String path,User user) {
		try {
			URL url = new URL("http://localhost:4567" + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.connect();
			if(method != null && (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))) {
				OutputStream os = connection.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
				osw.write(JacksonUtil.objectToJson(user));
				osw.flush();
				osw.close();
			}
			String body = IOUtils.toString(connection.getInputStream());
			return body;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Sending request failed: " + e.getMessage());
			return null;
		}
	}

}
