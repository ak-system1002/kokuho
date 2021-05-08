package com.example.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlayerTest {

	@Autowired
	Validator validator;

	private Player player = new Player();
	private BindingResult bindingResult = new BindException(player, "Player");

	@Test
	public void nameIsNull() {

		// Arrange
		player.setName(null);
		player.setPoint(new Long(0));
		player.setTeam("xxx");

		// Act
		validator.validate(player, bindingResult);

		// Assert
		assertThat(bindingResult.getFieldError().getField(),is("name"));
		assertThat(bindingResult.getFieldError().getDefaultMessage(), is("Name : 必須項目です"));
	}
}
