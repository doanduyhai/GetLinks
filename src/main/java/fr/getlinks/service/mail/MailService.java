package fr.getlinks.service.mail;

import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class MailService
{
	private JavaMailSenderImpl mailSender;
	private TemplateEngine emailTemplateEngine;

	public void sendMail(String subject, String[] addresses, String mailTemplate, Map<String, String> emailVariables, Locale locale)
			throws MessagingException
	{
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		final Context ctx = new Context(locale);
		ctx.setVariables(emailVariables);

		helper.setSubject(subject);
		helper.setTo(addresses);

		String htmlContent = this.emailTemplateEngine.process(mailTemplate, ctx);
		helper.setText(htmlContent, true /* isHtml */);

		mailSender.send(mimeMessage);
	}

	public void setMailSender(JavaMailSenderImpl mailSender)
	{
		this.mailSender = mailSender;
	}

	public void setEmailTemplateEngine(TemplateEngine emailTemplateEngine)
	{
		this.emailTemplateEngine = emailTemplateEngine;
	}

}
