package com.m2w.sispj.dto.customer;

import com.m2w.sispj.domain.enums.ProcessStatus;
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
public class CustomerProcessDocumentDTO extends BaseAuditDTO {
	private ProcessStatus status;
	private DocumentTypeDTO type;
	private String checkDate;

	public boolean getChecked() {
		return checkDate != null;
	}
}