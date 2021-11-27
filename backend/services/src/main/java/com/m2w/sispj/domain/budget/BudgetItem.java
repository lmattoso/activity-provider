package com.m2w.sispj.domain.budget;

import com.m2w.sispj.domain.SimpleBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@FieldNameConstants
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = BudgetItem.TABLE)
@SequenceGenerator(name = "default_gen", sequenceName = BudgetItem.SEQUENCE, allocationSize = 1)
public class BudgetItem extends SimpleBaseEntity {

	public static final String TABLE = "budget_item";
	public static final String SEQUENCE = "budget_item_seq";
	public static final String NAME = "request";
	public static final String BUDGET_ID = "budget_id";
	public static final String VALUE = "value";
	public static final String IVA = "iva";
	public static final String ORDER_NUMBER = "order_number";

	@Column(name = BudgetItem.NAME)
	private String request;

	@ManyToOne
	@JoinColumn(name = BudgetItem.BUDGET_ID)
	private Budget budget;

	@Column(name = BudgetItem.VALUE)
	private BigDecimal value;

	@Column(name = BudgetItem.IVA)
	private BigDecimal iva;

	@Column(name = BudgetItem.ORDER_NUMBER)
	private Integer order;

	public BigDecimal getTotal() {
		return value.add(this.getIva()).setScale(2, RoundingMode.HALF_UP);
	}
}