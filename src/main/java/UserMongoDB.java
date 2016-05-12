import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UserMongoDB {


	public static String insertUser(User user) {
		MongoClient mongo = null;
		
		try {
			// creating a new connection.
			mongo = new MongoClient("localhost", 27017);	
		
			// obtaining the particluar database.
			DB db = mongo.getDB("EgenChallenge");
		
			// obtaining the particular table/collection.
			DBCollection collection = db.getCollection("User");
		
			// creating document for insertion into database.
			BasicDBObject doc = new BasicDBObject("firstName", user.getFirstName())
				.append("lastName", user.getLastName())
				.append("email", user.getEmail())
				.append("address",
						new BasicDBObject("street", user.getAddress().getStreet())
								.append("city", user.getAddress().getCity())
								.append("zip", user.getAddress().getZip())
								.append("state", user.getAddress().getState())
								.append("country",user.getAddress().getCountry()))
				.append("dateCreated", user.getDateCreated())
				.append("company",
						new BasicDBObject("name", user.getCompany().getName())
								.append("website", user.getCompany().getWebsite()))
				.append("profilePic", user.getProfilePic());

			collection.insert(doc);
		
			// obtaining the id generated automatically and then assigned to the user.
			ObjectId id = (ObjectId)doc.get("_id");
			System.out.println("String: id" + id.toString());
			user.setId(id.toString());

			return JacksonUtil.objectToJson(user);
		
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if(mongo!=null) {
				mongo.close();
			}
		}
		return null;
	}

	public static List<String> getAllUsers() {
		MongoClient mongo = null;
		
		try {
			mongo = new MongoClient("localhost", 27017);		
			DB db = mongo.getDB("EgenChallenge");
			
			// obtaining the particular table/collection.
			DBCollection collection = db.getCollection("User");
			
			// obtaining all the documents from user collections
			DBCursor cursor = collection.find();

		List<String> result = new ArrayList<String>();

		while (cursor.hasNext()) {
			DBObject Document = cursor.next();
			String user = JacksonUtil.objectToJson(dbObjectToUser(Document));
			result.add(user);
		}
		return result;
	
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if(mongo != null) {
				mongo.close();
			}
		}
		return null;
	}
	
	
	public static String updateUser(User user) {
		MongoClient mongo = null;
		
		try {
			mongo = new MongoClient("localhost", 27017);		
			DB db = mongo.getDB("EgenChallenge");
			
			// obtaining the particular table/collection.
			DBCollection collection = db.getCollection("User");		
			ObjectId objId = new ObjectId(user.getId());
			
			// find the particular document using id.
			DBObject dbObject = collection.findOne(new BasicDBObject().append("_id",objId));

			// creating a new update document.
			BasicDBObject newDocument = new BasicDBObject("firstName",
					user.getFirstName())
					.append("lastName", user.getLastName())
					.append("email", user.getEmail())
					.append("address",
							new BasicDBObject("street", user.getAddress().getStreet())
									.append("city", user.getAddress().getCity())
									.append("zip", user.getAddress().getZip())
									.append("state",user.getAddress().getState())
									.append("country",user.getAddress().getCountry()))
					.append("dateCreated", user.getDateCreated())
					.append("company",
							new BasicDBObject("name", user.getCompany()
									.getName()).append("website", user
									.getCompany().getWebsite()))
					.append("profilePic", user.getProfilePic());

			collection.update(dbObject, newDocument);
			return "success";
		} catch (IllegalArgumentException e) {
			return "fail";
		} catch (NullPointerException e) {
			return "fail";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if(mongo != null) {
				mongo.close();
			}
		}
		return null;
	}

	public static User dbObjectToUser(DBObject updateDocument) {
		// obtaining the information.
		ObjectId id = (ObjectId) updateDocument.get("_id");
		String firstName = (String) updateDocument.get("firstName");
		String lastName = (String) updateDocument.get("lastName");
		String email = (String) updateDocument.get("email");
		String dateCreated = (String) updateDocument.get("dateCreated");
		String profilePic = (String) updateDocument.get("profilePic");

		DBObject addressDocument = (DBObject) updateDocument.get("address");
		String street = (String) addressDocument.get("street");
		String city = (String) addressDocument.get("city");
		String zip = (String) addressDocument.get("zip");
		String state = (String) addressDocument.get("state");
		String country = (String) addressDocument.get("country");

		// putting it into the user object.
		Address address = new Address();
		address.setCity(city);
		address.setCountry(country);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);

		DBObject companyDocument = (DBObject) updateDocument.get("company");
		String name = (String) companyDocument.get("name");
		String website = (String) companyDocument.get("website");

		Company company = new Company();
		company.setName(name);
		company.setWebsite(website);

		User user = new User();
		user.setId(id.toString());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setAddress(address);
		user.setCompany(company);
		user.setDateCreated(dateCreated);
		user.setProfilePic(profilePic);

		return user;
	}	
}
