package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlayerControllerTest {

	private MockMvc mockMvc;

	@Autowired
	PlayerController target;

	private static Connection connection = null;
	private static Statement statement = null;
	private final static String url="jdbc:h2:./h2db/sampledb;";
	private final static String id="username";
	private final static String pw="password";

	@BeforeClass
	public static void initTable() throws Exception {
		connection = DriverManager.getConnection(url,id,pw);
		statement = connection.createStatement();

		String sqlString="DROP TABLE player";
		statement.executeUpdate(sqlString);

		sqlString = "CREATE TABLE IF NOT EXISTS player ("
				+ "id bigint(20) NOT NULL AUTO_INCREMENT,"
				+ "name varchar(255) DEFAULT NULL,"
				+ "team varchar(255) DEFAULT NULL,"
				+ "point bigint(20) DEFAULT NULL,"
				+ "PRIMARY KEY (id),"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		statement.executeUpdate(sqlString);
	}

	@AfterClass
	public static void afterClass() throws Exception {
		statement.close();
		connection.close();
	}

	@Before
	public void init() throws Exception {
		String sql = "DELETE from player";
		statement.execute(sql);

		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@After
	public void close() throws Exception {
	}

	@Test
	public void getIndexTest() throws Exception {

		mockMvc.perform(get("/players"))
		.andExpect(status().isOk())
		.andExpect(view().name("players/index"));
	}

	@Test
	public void getNewTest() throws Exception {

		// Arrange
		String sql = "";
		sql += "INSERT INTO player VALUES (100,'test100','aaa','100');";
		statement.executeUpdate(sql);

		mockMvc.perform(get("/players/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("players/new"));
	}


	@Test
	public void getFindOneTest() throws Exception {

		String sql = "";
		sql += "INSERT INTO player VALUES (100,'test100','aaa','100');";
		statement.executeUpdate(sql);

		mockMvc.perform(get("/players/100"))
		.andExpect(status().isOk())
		.andExpect(view().name("players/show"))
		.andExpect(model().attribute("player.name", "test100"))
		.andExpect(model().attribute("player.team", "aaa"));
	}
}
