package com.example.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.example.domain.Player;
import com.example.mapper.PlayerMapper;

@RunWith(Enclosed.class)
public class PlayerServiceTest {

	private static Connection connection = null;
	private static Statement statement = null;
	private final static String url="jdbc:h2:./h2db/sampledb";
	private final static String id="username";
	private final static String pw="password";

	@RunWith(SpringRunner.class)
	@SpringBootTest
	public static class playerMapper1 {

		@Autowired
		private PlayerMapper playerMapper;

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

			String sql = "DELETE from player";
			statement.execute(sql);
		}

		@AfterClass
		public static void afterClass() throws Exception {
			statement.close();
			connection.close();
		}

		@Before
		public void init() throws Exception {
			// Arrange
			String sql = "";
			sql += "INSERT INTO player VALUES (100,'test100','100','100');";
			sql += "INSERT INTO player VALUES (101,'test101','101','200');";
			sql += "INSERT INTO player VALUES (102,'test102','102','300');";
			statement.executeUpdate(sql);
		}

		@Test
		public void playerMapper_findAll() throws Exception {

			// Act
			List<Player> players = playerMapper.findAll();

			// Assert
			assertThat(3,is(players.size()));
		}
	}

	@RunWith(Theories.class)
	@SpringBootTest
	public static class playerMapper2 {

		// @RunWith(SpringJUnit4ClassRunner.class) を指定していると JUnit4 で @Theory とか @Parameterized を指定できない
		// SpringClassRule と SpringMethodRule を使えば、RunWithにTheories.classとかを指定できる。
	    @ClassRule
	    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	    @Rule
	    public final SpringMethodRule springMethodRule = new SpringMethodRule();

		@Autowired
		private PlayerMapper playerMapper;

		@DataPoints
	    public static Integer[] ids = new Integer[] {
	    		100,101,102
	    };

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

			String sql = "DELETE from player";
			statement.execute(sql);

			sql = "";
			sql += "INSERT INTO player VALUES (100,'test100','100','100');";
			sql += "INSERT INTO player VALUES (101,'test101','101','200');";
			sql += "INSERT INTO player VALUES (102,'test102','102','300');";
			statement.executeUpdate(sql);
		}

		@AfterClass
		public static void afterClass() throws Exception {
			statement.close();
			connection.close();
		}

		@Before
		public void init() throws Exception {
		}

		@Theory
		public void playerMapper_findOne(Integer ids) throws Exception {

	        // Act
			String st = ids.toString();
			Player player = playerMapper.findOne(ids.longValue());

			// Assert
			assertThat(ids.longValue(),is(player.getId()));
			assertThat("test"+ st,is(player.getName()));
			assertThat(st,is(player.getTeam()));
//			assertThat(ids.longValue(),is(player.getPoint()));
		}
	}
}
