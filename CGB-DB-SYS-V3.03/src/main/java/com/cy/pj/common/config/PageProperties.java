package com.cy.pj.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "db.page")
public class PageProperties {
	private Integer pageSize;
}
