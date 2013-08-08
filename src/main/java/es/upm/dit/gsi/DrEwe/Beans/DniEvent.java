package es.upm.dit.gsi.DrEwe.Beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DniEvent extends Event {
	private String name;
	private int number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public DniEvent(String source, Calendar timeStamp, String name, int number) {
		super(source, timeStamp);
		this.name = name;
		this.number = number;
	}
	public DniEvent(String source, Calendar timeStamp) {
		super(source, timeStamp);
		// TODO Auto-generated constructor stub
	}
	public DniEvent(Node nNode){
		super("unknown", Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		super.setSource("DniEvent");

		Element element=(Element)nNode;
		NodeList childs=element.getElementsByTagName("field");

		for (int temp = 0; temp < childs.getLength(); temp++) {
			Node subnode=childs.item(temp);
			Element subelement = (Element)subnode;
			String attname=subelement.getAttribute("name");
			if(attname.equals("NUMBER")){
				this.number=new Integer (subnode.getTextContent());
			}else if(attname.equals("NAME")){
				this.name=subnode.getTextContent();
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
