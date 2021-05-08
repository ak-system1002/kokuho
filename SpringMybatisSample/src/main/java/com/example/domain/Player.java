package com.example.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

	private Long id;

	@NotBlank(message="Name : 必須項目です")
	private String name;

	@Size(min=3, max=3, message="Team : 3文字で入力して下さい")
	private String team;

	@Max(value=999, message="Point : 0～999を入力して下さい")
	private Long point;
}
