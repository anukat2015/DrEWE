package es.upm.dit.gsi.DrEwe.Beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LightEvent extends Event{

	private int value;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public LightEvent(String source, Calendar timeStamp, int value) {
		super(source, timeStamp);
		this.value = value;
	}
	public LightEvent(String source, Calendar timeStamp) {
		super(source, timeStamp);
		// TODO Auto-generated constructor stub
	}
	public LightEvent(Node nNode){
		super("unknown", Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		super.setSource("LightEvent");
		Element element=(Element)nNode;
		NodeList childs=element.getElementsByTagName("field");
		
		for (int temp = 0; temp < childs.getLength(); temp++) {
			Node subnode=childs.item(temp);
			Element subelement = (Element)subnode;
			String attname=subelement.getAttribute("name");
			if(attname.equals("VALUE")){
				this.value=new Integer (subnode.getTextContent());
			}else if(attname.equals("timed")){
				Calendar cal = Calendar.getInstance();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
			    try {
					cal.setTime(sdf.parse(subnode.getTextContent()));
					super.setTimeStamp(cal);
					
				} catch (DOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
