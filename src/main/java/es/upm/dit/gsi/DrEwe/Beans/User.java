package es.upm.dit.gsi.DrEwe.Beans;

public class User {

	private String user;
	private String email;
	private String dni_name;
	private String twitter;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDni_name() {
		return dni_name;
	}
	public void setDni_name(String dni_name) {
		this.dni_name = dni_name;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public User(String user, String email, String dni_name, String twitter) {
		super();
		this.user = user;
		this.email = email;
		this.dni_name = dni_name;
		this.twitter = twitter;
	}
	public User() {
		super();
		this.user = "unknown user";
		this.email = "unknown@email.com";
		this.dni_name = "unknown dni_name";
		this.twitter = "unknown_twitter";
	}
}
