package es.upm.dit.gsi.DrEwe.Beans;

import java.util.Calendar;
import java.util.TimeZone;

public class Event {
	protected String source;
	protected Calendar timeStamp;
	public Event(String source, Calendar timeStamp) {
		super();
		this.source = source;
		this.timeStamp = timeStamp;
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
