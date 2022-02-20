package com.meli.mutants.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

	@Value("${application.version}")
	private String appVersion;

	@GetMapping("/version")
	public String version() {
		return "Versión: " + appVersion;
	}

}
