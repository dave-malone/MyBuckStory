package com.mybuckstory.core.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.mybuckstory.core.event.EmailEvent;

@Service
public class EmailService implements InitializingBean, ApplicationListener<EmailEvent>{

	private static final Logger logger = Logger.getLogger(EmailService.class);
	
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired @Value("${mail.from}")
	private String senderAddress;	

	public void afterPropertiesSet() throws Exception{
		if(velocityEngine == null){
			throw new IllegalStateException("velocityEngine was null");
		}

		if(sender == null){
			throw new IllegalStateException("JavaMailSender was null");
		}
		if(StringUtils.isNotBlank(senderAddress)){
			// My Buck Story<admin@mybuckstory.com>
			this.senderAddress = "MyBuckStory<" + senderAddress + ">";
		}else{
			throw new IllegalStateException("senderAddress was blank");
		}
	}
	
	@Override
	public void onApplicationEvent(EmailEvent event) {
		logger.info("Email event received: " + event);
		if(!event.sendEmail()){
			logger.info("email event is set to NOT SEND; returning");
			return;
		}		
		
		logger.info("sending email...");
		if(event.containsMultipleAddressees()){
			logger.info("sending email to multiple addresses");
			for(String addressee : event.getAddresses()){
				logger.debug("addressee: " + addressee);
				try{
					MimeMessagePreparator preparator = getMimeMessagePreparator(addressee, event.getMessageSubject(), event.getVelocityTemplate(), event.getMessageModel());
					this.sender.send(preparator);
				}catch(Exception e){
					logger.error("An error occurred while sending email to " + addressee, e);
				}
			}
		}else{
			logger.debug("sending email to single addressee");
			try{
				MimeMessagePreparator preparator = getMimeMessagePreparator(event.getTo(), event.getMessageSubject(), event.getVelocityTemplate(), event.getMessageModel());		
				this.sender.send(preparator);
			}catch(Exception e){
				logger.error("An error occurred while sending email to " + event.getTo(), e);
			}
		}
		
	}
		
	public MimeMessagePreparator getMimeMessagePreparator(final String addressee, final String subject, final String velocityTemplate, final Map<String, Object> model){
		return new MimeMessagePreparator(){
			
			public void prepare(MimeMessage mimeMessage) throws Exception{
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplate, model);
				
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);				
				mimeMessageHelper.setTo(addressee);
				mimeMessageHelper.setFrom(senderAddress); 
				mimeMessageHelper.setReplyTo(senderAddress);
				mimeMessageHelper.setSubject(subject);			
				mimeMessageHelper.setText(text, true);
			}
			
		};		
	}

}