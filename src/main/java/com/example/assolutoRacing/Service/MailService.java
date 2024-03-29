package com.example.assolutoRacing.Service;

import org.springframework.stereotype.Service;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult; 


/**
 * 
 * @author nakagawa.so
 * メールサービス.
 */

@Service
public class MailService {
	/**
	 * メールを送信する.
	 * @param to 送信先
	 * @param from 送信元
	 * @param subject 件名
	 * @param textbody　本文
	 */
	public void send(String to,String from,String subject,String textbody) {
		
		SendEmailResult sendEmailResult = null;
		try {
		      AmazonSimpleEmailService client = 
		              AmazonSimpleEmailServiceClientBuilder.standard()
		                .withRegion(Regions.AP_NORTHEAST_1).build();
		      SendEmailRequest request = new SendEmailRequest()
		              .withDestination(
		                      new Destination().withToAddresses(to))
		              .withMessage(new Message()
		            		  .withBody(new Body()
		            		    .withHtml(new Content().withCharset("UTF-8").withData(textbody)))
		            		  .withSubject(new Content()
		            				  .withCharset("UTF-8").withData(subject)))
		              .withSource(from);
//		              .withConfigurationSetName(CONFIGSET);
		      sendEmailResult = client.sendEmail(request);
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * メール認証用のテキストボディを生成する.
	 * @param url 認証用URL
	 * @return 本文
	 */
	public String createMailAuthTextbody(String url) {
		String textBody = "<p>以下のリンクからパスワードの変更を行ってください。期限は24時間です。</p>"
				+ "<br>"
				+ "<br>"
				+ "<p><a href='"
				+ url
				+ "'>"
				+ url
				+ "</a></p>";
		return textBody;
	}
}
