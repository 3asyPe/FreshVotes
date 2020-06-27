package com.freshvotes.service;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

class EmailServiceTest {

	@Test
	void test() {
		String str1 = ";lkafsdjdf;slkj";
		String str2 = ";lkafsdjdf;slkj1";
		System.out.println(DigestUtils.sha256Hex(str1).equals(DigestUtils.sha256Hex(str2)));
		System.out.println(DigestUtils.sha256Hex(str1));
		System.out.println(DigestUtils.sha256Hex(str2));
	}

}
