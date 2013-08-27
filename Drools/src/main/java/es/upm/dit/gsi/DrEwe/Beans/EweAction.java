package es.upm.dit.gsi.DrEwe.Beans;

import es.upm.dit.gsi.DrEwe.Utils.Bot;

public class EweAction {
	private String title;
	private String description;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesription() {
		return description;
	}
	public void setDesription(String desription) {
		this.description = desription;
	}
	public EweAction(String title, String desription) {
		super();
		this.title = title;
		this.description = desription;
	}
	public void execute(){
		//Inappropriate code, just because of the few cases
		System.out.println("Executing action type: "+this.title+" value: "+this.description);
		if(this.title.equals("bot")){
			Bot bot=new Bot();
			
			bot.send(this.description);
		}else{
			System.out.println("Unidentified action");
		}
	}
}
