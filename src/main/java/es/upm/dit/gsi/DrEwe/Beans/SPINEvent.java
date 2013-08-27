package es.upm.dit.gsi.DrEwe.Beans;

import java.util.Calendar;
import java.util.TimeZone;

public class SPINEvent extends Event {

	private String title;
	
	public SPINEvent(String source, Calendar timeStamp) {
		super(source, timeStamp);
		// TODO Auto-generated constructor stub
	}

	public SPINEvent(String source, Calendar timeStamp, String description) {
		super(source, timeStamp, description);
		// TODO Auto-generated constructor stub
	}

	public SPINEvent(String title, String description) {
		super("SPIN", Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		this.title=title;
		this.description=description;
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SPINEvent(String source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
