package com.ssamz.jblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class JBlogWebApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void passwordEncode() {
	    // given
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "abc123@@";
		String encodedPassword = encoder.encode(rawPassword);

	    // then
		System.out.println("암호화 전 비밀번호 : " + rawPassword);
		System.out.println("암호화 이후 비밀번호 : " + encodedPassword);
		System.out.println("비밀번호가 동일한지 비교 : " + encoder.matches(rawPassword, encodedPassword));

	}

}
