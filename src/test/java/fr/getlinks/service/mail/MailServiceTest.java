package fr.getlinks.service.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import org.testng.annotations.Test;

import fr.getlinks.AbstractGetlinksTest;

public class MailServiceTest extends AbstractGetlinksTest
{

	@Test
	public void testSendHTMLMail() throws MessagingException
	{
		// String subject, String addresse, String mailTemplate, Map<String, String> emailVariables, Locale locale
		String subject = "Test Getlinks Account Creation";
		String[] addresses = new String[]
		{
				"doanduyhai@gmail.com",
				"magdou.thiam@outlook.com",
				"getlinkfrance@gmail.com"
		};
		String mailTemplate = "welcomeMail.html";

		Map<String, String> variables = new HashMap<String, String>();
		variables.put("userName", "Duy Hai");
		StringBuilder msg = new StringBuilder();
		msg.append("Thank you for registering to Getlinks.<br/>");
		msg.append("Your new password is <strong>Aj14Zf!sd </strong>. Please do not forget to change it.<br/><br/>");
		msg.append("Enjoy jour network experience with <strong>Getlinks</strong>");
		variables.put("message", msg.toString());

		Locale locale = new Locale("fr");

		this.mailService.sendMail(subject, addresses, mailTemplate, variables, locale);
	}
}
