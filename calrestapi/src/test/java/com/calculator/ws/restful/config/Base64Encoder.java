package com.calculator.ws.restful.config;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Base64Encoder {

	private static final Logger log = Logger.getLogger(Base64Encoder.class);
			
	public static void main(String [] args) {
		String input = "dsptodsauat";
		String encodedString = Base64.encodeBase64String(input.getBytes());
		log.info("Encoding of "+input+" : "+encodedString);
		String output = new String(Base64.decodeBase64(encodedString));
		log.info("Decoding of "+encodedString+" : "+output);
	}
	
}
