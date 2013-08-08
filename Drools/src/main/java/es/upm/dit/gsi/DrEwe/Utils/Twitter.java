package es.upm.dit.gsi.DrEwe.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;


public class Twitter {
	

	public void send(String message){
		HttpClient httpclient = new DefaultHttpClient();

		URIBuilder builder = new URIBuilder();
		Properties prop = new Properties();
		 try {
			prop.load(new FileInputStream("conf/gsn.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		builder.setScheme("http").setHost(prop.getProperty("twitter_url"))
		.setParameter("tweet", message);
		URI uri;
		try {
			uri = builder.build();
			HttpGet httpget = new HttpGet(uri);
			httpclient.execute(httpget);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public static void main (String []args){
		Twitter twitter = new Twitter();
		twitter.send("holiiiiii");
		twitter.send("holiiiiii");
		twitter.send("holiiiiii");
		twitter.send("holiiiiii");
	}
}