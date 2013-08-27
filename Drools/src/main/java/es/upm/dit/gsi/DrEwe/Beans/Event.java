package es.upm.dit.gsi.DrEwe.Beans;

import java.util.Calendar;
import java.util.TimeZone;

public class Event {
	protected String source;
	protected Calendar timeStamp;
	protected String description;
	public Event(String source, Calendar timeStamp) {
		super();
		this.source = source;
		this.timeStamp = timeStamp;
		this.description = "unknown";
	}
	public Event(String source, Calendar timeStamp,String description) {
		super();
		this.source = source;
		this.timeStamp = timeStamp;
		this.description = description;
	}
//	public Event(String source,String description) {
//		super();
//		this.source = source;
//		this.timeStamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//		this.description = description;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Event(String source) {
		super();
		this.source = source;
		this.timeStamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Calendar getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}

}
