package com.werqout.werqout.tests;

import static org.mockito.Matchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.anyInt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.werqout.werqout.repository.AthleteRepository;
import com.werqout.werqout.models.Athlete;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AthleteControllerTests {
	
	@Autowired
	private MockMvc controller;
	
	@MockBean
	AthleteRepository r;
	
	@Mock 
	Athlete a1, a2, a3, a4, a5, a6;
	
	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testAthleteController() throws Exception {
		List<Athlete> l = new ArrayList<Athlete>();
		List<Athlete> ret;
		
		when(r.findAll()).thenReturn(l);
		
		when(r.save((Athlete)any(Athlete.class))).thenAnswer(x -> {
			Athlete a = x.getArgument(0);
			a.setId(l.size());
			l.add(a);
			return a;
		});
		
		doAnswer(x-> {
			l.remove(x.getArgument(0));
			return null;
		}).when(r).deleteById((long) anyInt());
		
		doAnswer(x -> {
			long id = x.getArgument(0);
			return l.get((int) id);
		}).when(r).findById((long) anyInt());
		
		controller.perform(post("/athletes").contentType(MediaType.APPLICATION_JSON).content(
				"{\"userName\":\"test0\","
				+ "\"email\":\"government@us.gov\","
				+ "\"password\":\"mypassword2\"}"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(0)))
		.andExpect(jsonPath("$.userName", is("test0")))
		.andExpect(jsonPath("$.email", is("government@us.gov")))
		.andExpect(jsonPath("$.password", is("mypassword2")));
				
				/*"{\"id\":0,\"userName\":\"test0\","
				+ "\"email\":\"government@us.gov\","
				+ "\"password\":\"mypassword2\"}")));*/
		
		controller.perform(post("/athletes").contentType(MediaType.APPLICATION_JSON).content(
				"{\"userName\":\"test1\","
				+ "\"email\":\"government@us.gov\","
				+ "\"password\":\"mypassword2\"}"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.userName", is("test1")))
		.andExpect(jsonPath("$.email", is("government@us.gov")))
		.andExpect(jsonPath("$.password", is("mypassword2")));
		
		controller.perform(post("/athletes").contentType(MediaType.APPLICATION_JSON).content(
				"{\"userName\":\"test2\","
			  + "\"email\":\"government@us.gov\","
			  + "\"password\":\"mypassword2\"}"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(2)))
		.andExpect(jsonPath("$.userName", is("test2")))
		.andExpect(jsonPath("$.email", is("government@us.gov")))
		.andExpect(jsonPath("$.password", is("mypassword2")));
		
		controller.perform(get("/athletes/all"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id", is(0)))
		.andExpect(jsonPath("$[0].userName", is("test0")))
		.andExpect(jsonPath("$[0].email", is("government@us.gov")))
		.andExpect(jsonPath("$[0].password", is("mypassword2")))
		.andExpect(jsonPath("$[1].id", is(1)))
		.andExpect(jsonPath("$[1].userName", is("test1")))
		.andExpect(jsonPath("$[1].email", is("government@us.gov")))
		.andExpect(jsonPath("$[1].password", is("mypassword2")))
		.andExpect(jsonPath("$[2].id", is(2)))
		.andExpect(jsonPath("$[2].userName", is("test2")))
		.andExpect(jsonPath("$[2].email", is("government@us.gov")))
		.andExpect(jsonPath("$[2].password", is("mypassword2")));
	}

}
