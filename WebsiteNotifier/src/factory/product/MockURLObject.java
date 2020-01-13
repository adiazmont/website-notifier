package factory.product;

import java.net.URL;
import java.util.Date;

/*
 * Simulate an URLObject in order to be able to
 * access a previously-set new lastDateModified.
 */
public class MockURLObject {
	Date lastModifiedDate;
	URL url;
	
	public MockURLObject(URL url) {
		this.url = url;
	}
	/*
	 * Here it is the MOCK SIMULATION, instead of returning
	 * the real value, it returns a default value that
	 * will state the change.
	 */
	public long getLastDate() {	
		return System.currentTimeMillis(); 
	}
	public URL getURL() { return url; }

}
