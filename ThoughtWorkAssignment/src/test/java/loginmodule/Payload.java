package loginmodule;

public class Payload {
	public static String Payload(String Email,String Password)
	{
		return "{\r\n"
				+ "    \"email\": \""+Email+"\",\r\n"
				+ "    \"password\": \""+Password+"\"\r\n"
				+ "}";
	}

}
