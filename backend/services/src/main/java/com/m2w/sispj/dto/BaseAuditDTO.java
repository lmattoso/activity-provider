package com.m2w.sispj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseAuditDTO {
	private Long id;
	private SimpleUserDTO createBy;
	private SimpleUserDTO updateBy;
	private String createDate;
	private String updateDate;
	private Long version;
}