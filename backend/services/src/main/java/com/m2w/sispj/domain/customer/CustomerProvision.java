package com.m2w.sispj.domain.customer;

import com.m2w.sispj.domain.BaseAuditEntity;
import com.m2w.sispj.domain.enums.ProvisionType;
import com.m2w.sispj.util.SISPJUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = CustomerProvision.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = CustomerProvision.SEQUENCE, allocationSize = 1)
public class CustomerProvision extends BaseAuditEntity {

	public static final String TABLE = "customer_provision";
	public static final String SEQUENCE = "customer_provision_seq";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String CUSTOMER_PROCESS_TYPE_ID = "customer_process_type_id";
	public static final String DESCRIPTION = "description";
	public static final String TYPE = "type";
	public static final String SERVICE_VALUE = "service_value";
	public static final String PAYMENT_VALUE = "payment_value";
	public static final String PAYMENT_DATE = "payment_date";

	@ManyToOne
	@JoinColumn(name = CUSTOMER_ID)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = CUSTOMER_PROCESS_TYPE_ID)
	private CustomerProcess process;

	@Column(name = DESCRIPTION)
	private String description;

	@Column(name = TYPE)
	private ProvisionType type;

	@Column(name = SERVICE_VALUE)
	private BigDecimal serviceValue;

	@Column(name = PAYMENT_VALUE)
	private BigDecimal paymentValue;

	@Column(name = PAYMENT_DATE)
	private LocalDateTime paymentDate;

	public String getFormattedDescription() {
		List<String> info = new ArrayList<>();

		Optional.ofNullable(process).ifPresentOrElse(
			process -> info.add(String.format("Serviços: %s", process.getType().getName())),
			() -> {
				if(description != null) {
					info.add(String.format("Descrição: %s", description));
				}
			});

		Optional.ofNullable(serviceValue).ifPresent(value -> info.add(String.format("Provisão: %s", getFormattedProvision())));
		Optional.ofNullable(paymentValue).ifPresent(value -> info.add(String.format("Pagamento: %s", getFormattedPayment())));

		return info.stream().collect(Collectors.joining(" - "));
	}

	public String getFormattedPayment() {
		return SISPJUtil.formatCurrency(paymentValue, "pt", "PT");
	}

	public String getFormattedProvision() {
		return SISPJUtil.formatCurrency(serviceValue, "pt", "PT");
	}
}