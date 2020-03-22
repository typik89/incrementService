package ru.typik.springboot.rest.incrementService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class IncrementServiceApplicationTests {

	private static final String INCREMENTS = "/increments";

	protected MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void init() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test() throws Exception {
		MvcResult res1 = perform("{ \"request\" : \"12 33\" }");
		assertEquals(200, res1.getResponse().getStatus());
		assertEquals("{\"response\":\"13 34\"}", res1.getResponse().getContentAsString());

		MvcResult res2 = perform("{ \"request\" : \"1\" }");
		assertEquals(200, res2.getResponse().getStatus());
		assertEquals("{\"response\":\"2\"}", res2.getResponse().getContentAsString());

		MvcResult res3 = perform("{ \"request\" : \"101 33 21\" }");
		assertEquals(200, res3.getResponse().getStatus());
		assertEquals("{\"response\":\"102 34 22\"}", res3.getResponse().getContentAsString());

		MvcResult res4 = perform("{ \"request\" : \"101 33 2d1\" }");
		assertEquals(502, res4.getResponse().getStatus());

		MvcResult res5 = perform("{}");
		assertEquals(502, res5.getResponse().getStatus());

		MvcResult res6 = perform("");
		assertEquals(400, res6.getResponse().getStatus());
	}

	private MvcResult perform(String inputJson) throws Exception {
		return mvc.perform(MockMvcRequestBuilders.post(INCREMENTS).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();
	}

}
