package com.example.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.domain.Player;
import com.example.mapper.PlayerMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlayerServiceTest {

	@Autowired
	private PlayerMapper playerMapper;

	private static Connection connection = null;
	private static Statement statement = null;
	private final static String url="jdbc:h2:C:/TOOL/workspace2/SpringMybatisSample/h2db/sampledb";
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
	}

	@After
	public void close() throws Exception {
	}

	@Test
	public void playerMapper_findAll() throws Exception {

		// Arrange
		String sql = "";
		sql += "INSERT INTO player VALUES (100,'test100','100','100');";
		sql += "INSERT INTO player VALUES (101,'test101','101','200');";
		sql += "INSERT INTO player VALUES (102,'test102','102','300');";
		statement.executeUpdate(sql);

		// Act
		List<Player> players = playerMapper.findAll();

		// Assert
		assertThat(3,is(players.size()));
	}

	@Test
	public void playerMapper_findOne() throws Exception {

		// Arrange
		String strInser = "INSERT INTO player VALUES (?,?,?,?)";
		PreparedStatement insertStmt = connection.prepareStatement(strInser);
		insertStmt.clearParameters();
		insertStmt.setLong(1, 100);
		insertStmt.setString(2,"test100");
		insertStmt.setString(3,"100");
		insertStmt.setString(4,"100");
		insertStmt.executeUpdate();
		insertStmt.close();

		// Act
		Player player = playerMapper.findOne(new Long(100));

		// Assert
		assertThat(new Long(100),is(player.getId()));
		assertThat("test100",is(player.getName()));
		assertThat("100",is(player.getTeam()));
		assertThat(new Long(100),is(player.getPoint()));
	}
}
