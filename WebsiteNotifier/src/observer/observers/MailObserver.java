package observer.observers;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import memento.URLMemento;
import memento.URLOriginator;
import memento.interfaces.Caretaker;
import observer.interfaces.Action;
import observer.interfaces.Observer;
import observer.interfaces.Subject;
import observer.subject.WebsiteData;

/*
 * THIS IS A SIMPLE OBSERVER THAT HANDLES EMAIL NOTIFICATION.
 */
public class MailObserver implements Observer,Action {
	
	private Date modifiedDate;
	private URL url;
	private Subject websiteData;
	
	private String toEmailAddress;
	private String fromEmailAddress;
	private String subject;
	private String textMessage;
	private Caretaker caretaker;
	
	/*
	 * Attaches itself to the respective Subject class.
	 */
	public MailObserver(Subject websiteData) {
		this.websiteData = websiteData;
		this.websiteData.registerObserver(this);
	}

	@Override
	public void performAction() {
		// SENDS AN EMAIL
		String user = "adiazm10@gmail.com";
		String pass = "pu00zsw2"; 
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		
		/*
		 * Sets default values
		 */
		fromEmailAddress = "adiazm10@gmail.com";
		InternetAddress fromEmail =  null;
	
		try {
			fromEmail = new InternetAddress(
					fromEmailAddress, "Alan Díaz");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		toEmailAddress = "alan.diaz@cetys.edu.mx";
		subject = "Website Update";
		
		if(websiteData instanceof WebsiteData){
			url = ((WebsiteData) websiteData).getURL();
		}
	
		textMessage = "The website: " + url +
					" has been updated.\n"
					+ "Last modified date: " + modifiedDate;
		
		Session session = Session.getDefaultInstance(properties, 
				new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						"adiazm10@gmail.com","pu00zsw2");
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(fromEmail);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmailAddress));
			message.setSubject(subject);
			message.setText(textMessage);
			
			Transport transport = session.getTransport("smtps");
			transport.connect ("smtp.gmail.com", 465, user, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();  
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		/*
		 * Format for Caretaker:
		 * 1 URL LASTDATE
		 */
		URLOriginator originator = new URLOriginator();
		String mementoString = 1 + " " + url + " " + modifiedDate.getTime();
		URLMemento urlMemento = new URLMemento();
		urlMemento.setState(mementoString);
		originator.setMemento(urlMemento);
		caretaker.addMemento(originator);
	}

	@Override
	public void update(Date modifiedDate, Caretaker caretaker) {
		this.modifiedDate = modifiedDate;
		this.caretaker = caretaker;
		performAction();
	}
}
