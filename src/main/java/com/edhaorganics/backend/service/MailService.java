package com.edhaorganics.backend.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import com.edhaorganics.backend.beans.Order;
import com.edhaorganics.backend.repo.OrderRepository;
import com.edhaorganics.backend.util.MailContentBuilder;

@Service
public class MailService {

	private static final String ORDER_EDHAORGANICS_COM = "mail4mycompany@gmail.com";

	private static final String ORDER_COPY_EMAIL = "kishorekumarirtt@gmail.com";

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private MailContentBuilder builder;

	@Async
	public void sendOrderConfirmation(Long orderId) {
		List<String> recipients = new ArrayList<>();
		Order orderPlaced = orderRepo.findByOrderId(orderId);
		if (orderPlaced != null && !StringUtils.isEmpty(orderPlaced.getUser().getEmailId())) {
			recipients.add(orderPlaced.getUser().getEmailId());
		}
		if (orderPlaced != null && !StringUtils.isEmpty(orderPlaced.getCustomer().getEmailId())) {
			recipients.add(orderPlaced.getCustomer().getEmailId());
		}
		recipients.add(ORDER_COPY_EMAIL);
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(ORDER_EDHAORGANICS_COM);
			helper.setTo(recipients.toArray(new String[recipients.size()]));
			helper.setSubject("Order Confirmation");
			Context context = new Context();
			context.setVariable("order", orderPlaced);
			helper.setText(builder.build(context, "invoice.html"), true);
			mailSender.send(message);
		} catch (MessagingException | MailException e) {
			System.out.println("Exception in mailing:" + e.getMessage());
		}

	}
}
