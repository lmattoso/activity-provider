package com.m2w.sispj.dto.customer;

import com.m2w.sispj.dto.BaseAuditDTO;
import com.m2w.sispj.dto.DocumentTypeDTO;
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
public class CustomerDocumentDTO extends BaseAuditDTO {
	private Long customerId;
	private DocumentTypeDTO type;
	private String name;
	private String description;
	private String uploadLink;
	private boolean changedDocument;
}