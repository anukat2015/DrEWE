package es.upm.dit.gsi.DrEwe.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.*;

import es.upm.dit.gsi.DrEwe.Beans.User;

public class UserFactory {
	private JSONObject jsonObject;

	public UserFactory(){
		try {
			String st=readFileAsString("conf/users.json");
			
				this.jsonObject=new JSONObject(st);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String readFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(
				new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead=0;
		while((numRead=reader.read(buf)) != -1){
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		System.out.println("Readed: \n"+fileData.toString());
		return fileData.toString();
	}
	
	public User getUser(String dni_name){
		User user=new User();
		try {
			JSONArray usersArray=this.jsonObject.getJSONArray("users");
			for (int i=0;i<usersArray.length();i++){
				JSONObject userObject=usersArray.getJSONObject(i);
				JSONObject mappingObject=userObject.getJSONObject("mapping");
				String mapping_dni_name=mappingObject.getString("dni_name");
				if(mapping_dni_name.equals(dni_name)){
					user.setDni_name(dni_name);
					user.setEmail(mappingObject.getString("email"));
					user.setTwitter(mappingObject.getString("twitter"));
					user.setUser(userObject.getString("user"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//JSONObject obj=(JSONObject)this.usersJSON;
		
		
		return user;
	}
	
	public static void main(String[] args){
		UserFactory u=new UserFactory();
	}

}
