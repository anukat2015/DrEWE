package es.upm.dit.gsi.DrEwe.Beans;

public class VirtualSensorEvent {
	private String virtualSensor;
	private String eventType;
	public String getVirtualSensor() {
		return virtualSensor;
	}
	public void setVirtualSensor(String virtualSensor) {
		this.virtualSensor = virtualSensor;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public VirtualSensorEvent(String virtualSensor, String eventType) {
		super();
		this.virtualSensor = virtualSensor;
		this.eventType = eventType;
	}
}
