package main.com.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
	public static void sendSMS(String[] args) {
	String accountSid = "ACf045d2083187c10f23928555d9d4a908"; // Your Account SID from
										// www.twilio.com/user/account
		String authToken = "5527887a4f219059a2a25caa7ca062de"; // Your Auth Token from
										// www.twilio.com/user/account

		Twilio.init(accountSid, authToken);

		Message message = Message.creator(new PhoneNumber("+918605605202"), // To
																			// number
				new PhoneNumber("+14243245880"), // From number
				"Hello world!" // SMS body
		).create();

		System.out.println(message.getSid());

	}
}
