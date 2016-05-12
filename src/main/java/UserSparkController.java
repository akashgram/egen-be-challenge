import spark.Spark;

public class UserSparkController {

	private static final int HTTP_BAD_REQUEST = 404;
	private static final int HTTP_GOOD_RESPONSE = 200;

	public static void main(String[] args) {
		
		// display all the users in the collection.
		Spark.get("/users", (request, response) -> {
			return UserService.getUsers();
			});


		// create new user into the collection.
		Spark.post("/user", (request, response) -> {
			response.type("application/json");
			response.status(HTTP_GOOD_RESPONSE);
			return UserService.createUser(JacksonUtil
					.jsonToObject(request.body()));
		});

		Spark.put("/UpdateUser", (request, response) -> {
					String result = UserService.updateUser(JacksonUtil
							.jsonToObject(request.body()));
					response.type("application/json");
					if (result.equalsIgnoreCase("success")) {
						response.status(HTTP_GOOD_RESPONSE);
						return "User Updated Successfully";
					} else {
						response.status(HTTP_BAD_REQUEST);
						return "User Not Found.";
					}
				}
		);
		
	}

}
