package com.online.module.master.people.model;

import java.util.Date;

import com.online.module.common.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class People extends BaseEntity {

	private Long peopleId;
	private String fullName;
	private String identityCard;
	private String email;
	private String mobile;
	private Date dateOfBirth;
	private String placeOfBirth;
	private String password;
	private String peopleNumber;
	private String address;
	private String gender;
	private Long responsibilityId;
	
	// helper
	private String responsibilityName;
	
}
