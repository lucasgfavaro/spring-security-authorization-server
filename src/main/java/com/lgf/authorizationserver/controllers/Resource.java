package com.lgf.authorizationserver.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Resource {

	@RequestMapping(value = "/resource")
	public String get() {
		return "1";
	}
}
