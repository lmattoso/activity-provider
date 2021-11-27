package com.m2w.sispj.dto.customer;

import com.m2w.sispj.dto.BaseAuditDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class CustomerNoteDTO extends BaseAuditDTO {
	private Long customerId;
	private String description;
}