package testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import factory.creator.*;
import factory.product.MockURLObject;
import website.notifier.WebsiteNotifierCreator;

public class NotifyTest {
	
	WebsiteNotifierCreator webNotifier;
	URL url;
	String urlString;
	String lastDate;
	MockURLObject mockObject;
	
	
	@Before
	public void setUp() {
		webNotifier = mock(WebsiteNotifierCreator.class);
		webNotifier.main(null);
		
		urlString = "http://www.eli.sdsu.edu/index.html";
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		URLObjectCreator mockObjectCreator = new MockURLObjectCreator();
		mockObject = mockObjectCreator.createURLObject(url);
		long time = mockObject.getLastDate();
		lastDate = String.valueOf(time);
	}

	@Test
	public void test() {
		verify(webNotifier).notify(urlString,lastDate);
	}
}
