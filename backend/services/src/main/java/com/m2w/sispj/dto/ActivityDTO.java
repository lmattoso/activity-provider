package com.m2w.sispj.dto;

import com.m2w.sispj.domain.enums.ActivityType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class ActivityDTO extends BaseAuditDTO {
	private String description;
	private String typeDescription;
}