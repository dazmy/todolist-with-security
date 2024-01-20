package com.dazmy.todolist.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

@SpringBootTest
class TodolistWithSecurityApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testGenerateKey() {
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		String secretString = Encoders.BASE64.encode(key.getEncoded());
		System.out.println(secretString);
	}
}
