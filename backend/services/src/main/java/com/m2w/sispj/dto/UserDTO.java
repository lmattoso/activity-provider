package com.m2w.sispj.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class UserDTO extends BaseDTO {
	private Long id;
	private String name;
	private String email;
	private boolean changePassword;
	private ProfileDTO profile;
	private Long version;
}