package service;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;

import dao.ExpensesDAO;
import expenseList.ExpenseList;
import expenses.Spent;
import expenses.Enum.ExpenseType;

public class Service {
	private ExpensesDAO dao = new ExpensesDAO();

	public ExpenseList getExpenseList() {
		List<Spent> spents = dao.listExpenses();
		return new ExpenseList(spents);
	}

	public void insertExpense(Spent spent) {
		if (spent.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("The value must be greater than zero");
		}

		if (spent.getDescription() == null || spent.getDescription().isBlank()) {
			throw new IllegalArgumentException("New description can't be empty!");
		}

		dao.insertNewExpense(spent);
	}

	public void deleteExpense(int id) {
		if (!dao.deleteExpense(id)) {
			throw new IllegalArgumentException("Id not found!");
		}
	}

	public void updateAmount(int id, BigDecimal newAmount) {
		if (newAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("The value must be greater than zero");
		}
		if (!dao.updateAmount(id, newAmount)) {
			throw new IllegalArgumentException("Id not found!");
		}
	}

	public void updateDescription(int id, String newDescription) {
		if (newDescription == null || newDescription.isBlank()) {
			throw new IllegalArgumentException("New description can't be empty!");
		}
		if (!dao.updateDescription(id, newDescription)) {
			throw new IllegalArgumentException("Id not found!");
		}
	}

	public void updateExpenseDate(int id, LocalDate newDAte) {
		if (!dao.updateExpenseDate(id, newDAte)) {
			throw new IllegalArgumentException("Id not found!");
		}
	}

	public void updateExpenseType(int id, ExpenseType newExpenseType) {
		if (!dao.updateExpenseType(id, newExpenseType)) {
			throw new IllegalArgumentException("Id not found!");
		}
	}

	public void deleteAllExpenses() {
		dao.deleteAll();
	}

	public ExpenseList searchWithDate(LocalDate start, LocalDate end) {
		if (start.isAfter(end)) {
			throw new IllegalArgumentException("The start date can't be greater than end date!");
		}
		return new ExpenseList(dao.searchWithDate(start, end));
	}

	public ExpenseList getVariableExpenses() {
		return new ExpenseList(dao.listVariableExpenses());
	}

	public ExpenseList getFixedExpenses() {
		return new ExpenseList(dao.listFixedExpenses());
	}
}
