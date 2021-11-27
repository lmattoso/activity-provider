package com.m2w.sispj.dto.budget;

import com.m2w.sispj.dto.BaseAuditDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@SuperBuilder
public class BudgetDTO extends BaseAuditDTO {
	private String name;
	private String requestDescription;
	private String serviceDescription;
	private String documentsDescription;
	private String phone;
	private String address;
	private String nif;
	private String niss;
	private String expirationDate;
	private List<BudgetItemDTO> items;
	private String comments;
}