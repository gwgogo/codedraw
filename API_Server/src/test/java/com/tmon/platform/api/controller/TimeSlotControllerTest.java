package com.tmon.platform.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * TimeSlotControllerTest
 * 
 * @author 구도원
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/test/resources/testServlet-context.xml",
		"file:src/test/resources/testDataSource-context.xml", "file:src/test/resources/testDateFormat-context.xml" })
public class TimeSlotControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private TimeSlotController timeSlotController;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(timeSlotController).build();
	}

	@Test
	public void insert_timeslot() throws Exception {

		// Request Parameters setting
		Map<String, String> requestBodymap = new HashMap<String, String>();
		requestBodymap.put("start_time", "09:00:00");
		requestBodymap.put("end_time", "09:59:00");
		requestBodymap.put("delivery_date", "9999-12-31");
		requestBodymap.put("count", "0");
		JSONObject requestBody = new JSONObject(requestBodymap);

		// Request to MockMVC
		mockMvc.perform(post("/timeslots").contentType(MediaType.APPLICATION_JSON).content(requestBody.toJSONString()))
				.andExpect(status().isOk());
	}

	@Test
	public void update_timeslot() throws Exception {

		// Request Parameters setting
		int timeslot_id = 270;
		Map<String, String> requestBodymap = new HashMap<String, String>();
		requestBodymap.put("start_time", "10:00:00");
		requestBodymap.put("end_time", "10:59:00");
		JSONObject requestBody = new JSONObject(requestBodymap);

		// Request to MockMVC
		mockMvc.perform(put("/timeslots/" + timeslot_id).contentType(MediaType.APPLICATION_JSON)
				.content(requestBody.toJSONString())).andExpect(status().isOk());

	}

	@Test
	public void delete_timeslot() throws Exception {

		// Request Parameters setting
		int timeslot_id = 270;

		// Request to MockMVC
		mockMvc.perform(delete("/timeslots/" + timeslot_id)).andExpect(status().isOk());
	}

	@Test
	public void select_timeslot_validDate() throws Exception {
		String search_init_date = "2017-07-20";
		int validDays = 3;
		mockMvc.perform(get("/timeslots/" + validDays + "?search_init_date=" + search_init_date))
				.andExpect(status().isOk());
	}

	@Test
	public void select_timeslot_delivery_date() throws Exception {
		mockMvc.perform(get("/timeslots?search_init_date=2017-07-20&search_finish_date=2017-07-20"))
				.andExpect(status().isOk());
	}

	@After
	public void after() {

	}
}
