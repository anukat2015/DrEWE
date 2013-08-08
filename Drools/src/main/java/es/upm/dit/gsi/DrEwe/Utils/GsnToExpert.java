package es.upm.dit.gsi.DrEwe.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.drools.WorkingMemoryEntryPoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.upm.dit.gsi.DrEwe.Beans.Event;
import es.upm.dit.gsi.DrEwe.Beans.VirtualSensorEvent;



public class GsnToExpert {
	private Calendar lastCheck;
	private Properties prop;
	private static String BEANS_PACKAGE="es.upm.dit.gsi.DrEwe.Beans.";
	private static WorkingMemoryEntryPoint entryPoint;
	
	public GsnToExpert(WorkingMemoryEntryPoint entryPoint){
		this.entryPoint = entryPoint;
		this.lastCheck=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		this.lastCheck.set(Calendar.YEAR,0);
		this.prop=new Properties();
		try{
			this.prop.load(new FileInputStream("conf/gsn.properties"));
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public Calendar getLastCheck() {
		return lastCheck;
	}
	public void setLastCheck(Calendar lastCheck) {
		this.lastCheck = lastCheck;
	}
	public void updateLastCheck(){
		this.setLastCheck(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
	}
	private ArrayList<VirtualSensorEvent> getEventsType(){
		ArrayList<VirtualSensorEvent> types = new ArrayList<VirtualSensorEvent>();
		try{
			Document doc = parseXML(prop.getProperty("gsn-server"));
			doc.getDocumentElement().normalize();
			NodeList vs=doc.getElementsByTagName("virtual-sensor");

			for (int i=0;i<vs.getLength();i++){
				Node nNode = vs.item(i);
				//will crash if vs is not initialized
				Element element=(Element)nNode;
				NodeList childs=element.getElementsByTagName("field");

				String vsensor=element.getAttribute("name");
				String ev=BEANS_PACKAGE+childs.item(childs.getLength()-1).getTextContent();

				types.add(new VirtualSensorEvent(vsensor,ev));

				//System.out.println("Virtual sensor found: "+vsensor+ ", event: "+ev);

			}

		} catch (Exception e){
			e.printStackTrace();
		}
		return types;
	}
	public void updateEvents(int window){
		ArrayList<VirtualSensorEvent> types=getEventsType();
		for (VirtualSensorEvent vse : types){
			String urlString=prop.getProperty("gsn-server");
			urlString+="?REQUEST=114";
			urlString+="&name=";
			urlString+=vse.getVirtualSensor();
			urlString+="&window="+window;

			Document doc=parseXML(urlString);
			NodeList streamList=doc.getElementsByTagName("stream-element");

			try {
				Class c= Class.forName(vse.getEventType());
				Constructor con=c.getConstructor(Node.class);
				for (int i=0;i<streamList.getLength();i++){
					Node nNode =streamList.item(i);
					Object ev=con.newInstance(nNode);
					//checktime
					if(this.lastCheck.before(((Event) ev).getTimeStamp())){
						//System.out.println(this.lastCheck.toString());
						//System.out.println(((Event) ev).getTimeStamp());
						entryPoint.insert(ev);
						//updateLastCheck();
					}
					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		

	}
	private Document parseXML(String url){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(new URL(url).openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

}
