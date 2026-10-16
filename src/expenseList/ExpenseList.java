package expenseList;

import java.math.BigDecimal;
import java.util.List;

import expenses.Spent;
import expenses.Enum.ExpenseType;

public class ExpenseList {
	private List<Spent> expenseList;

	public ExpenseList(List<Spent> expenseList) {
		this.expenseList = expenseList;
	}
	
	public void showExpenses() {
		for(Spent spent : expenseList) {
			spent.showDetails();
		}
	}
	
	public void showFixedExpenses() {
		for(Spent spent : expenseList) {
			if(spent.getExpenseType() == ExpenseType.FIXED)
			spent.showDetails();
		}
	}
	
	public void showVariableExpenses() {
		for(Spent spent : expenseList) {
			if(spent.getExpenseType() == ExpenseType.VARIABLE)
			spent.showDetails();
		}
	}
	
	public void cauculateExpensesFinalPrice() {
		BigDecimal fixedAmount = BigDecimal.ZERO;
		BigDecimal variableAmount = BigDecimal.ZERO;
		for(Spent spent : expenseList) {
			if(spent.getExpenseType() == ExpenseType.FIXED) {
				fixedAmount = fixedAmount.add(spent.getAmount());
			}
			
			if(spent.getExpenseType() == ExpenseType.VARIABLE) {
				variableAmount = variableAmount.add(spent.getAmount());
			}
			
		}
		System.out.println("The final amount for fixed expenses was: " + fixedAmount);
		System.out.println("The final amount for variable expenses was: " + variableAmount);
		System.out.println("and the total was: " + fixedAmount.add(variableAmount));
	}
	
	public boolean verifyId(int id) {
		return expenseList.stream().anyMatch(obj -> obj.getId() == id);
	}
}
