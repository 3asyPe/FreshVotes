package com.freshvotes.service;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

class EmailServiceTest {

	@Test
	void test() throws ParseException {
	         
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		long prevDay = System.currentTimeMillis() - 1000*60*60*24;
        Date dayBefore = new Date(prevDay);
        Date testDate = simpleDateFormat.parse("28-06-2020 20:15:57");
        if(testDate.before(dayBefore)){
            System.out.println("The date is older than current day");
        } else {
            System.out.println("The date is not expired");
        }
        System.out.println(testDate);
	}
	
}
