package factory.creator;

import java.net.URL;
import factory.product.MockURLObject;

public class MockURLObjectCreator extends URLObjectCreator {
	/*
	 * This is the factory method
	 */
	@Override
	public MockURLObject createURLObject(URL url) {
		return new MockURLObject(url);
	}
}
