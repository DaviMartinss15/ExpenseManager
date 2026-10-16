package expenses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import expenses.Enum.ExpenseType;

public class Spent {
	private int id;
	private BigDecimal amount;
	private String description;
	private LocalDate expenseDate;
	private ExpenseType expenseType;
	DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Spent(int id, BigDecimal amount, String description, LocalDate expenseDate, ExpenseType expenseType) {
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.expenseDate = expenseDate;
		this.expenseType = expenseType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expense_date) {
		this.expenseDate = expense_date;
	}
	
	
	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public void showDetails() {
		System.out.printf("%d - %s - R$ %.2f - %s - %s%n", id, expenseDate.format(fmt), amount, description, expenseType);
	}
}
