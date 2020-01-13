package factory.creator;

import java.io.IOException;
import java.net.URL;
import factory.product.MockURLObject;

public abstract class URLObjectCreator {
	
	URL url;
	long lastDate;
	
	public void setMockObject(URL url) throws IOException {
		MockURLObject mockObject = createURLObject(url);
		lastDate = mockObject.getLastDate();
	}
	/*
	 * This is the factory method
	 */
	public MockURLObject createURLObject(URL url) {
		return new MockURLObject(url);
	}
}
