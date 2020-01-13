package testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import website.notifier.*;

public class RestoreTest {

	WebsiteNotifierCreator webNotifier;
	
	@Before
	public void setUp() {
		webNotifier = mock(WebsiteNotifierCreator.class);
		webNotifier.main(null);
	}
	
	@Test
	public void test() {
		verify(webNotifier,times(4)).restore();
	}
}
