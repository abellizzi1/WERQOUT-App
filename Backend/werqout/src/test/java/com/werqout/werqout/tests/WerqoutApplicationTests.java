package com.werqout.werqout.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.werqout.werqout.repository.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.werqout.werqout.models.*;

@SpringBootTest
class WerqoutApplicationTests {

	//athlete test
	@Mock
	private AthleteRepository repo;

	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	public void getAccountByIdTest() {
		when(repo.findById(1)).thenReturn(new Athlete(1, "jDoe", "jDoe@gmail.com", "123456"));

		Athlete ath = repo.findById(1);

		assertEquals("jDoe", ath.getId());
		assertEquals("123456", ath.getPassword());
		assertEquals("jDoe@gmail.com", ath.getEmail());
	}


}
