package com.m2w.sispj.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class ContactDTO extends BaseAuditDTO {
	private String name;
	private String phone;
	private String email;
}