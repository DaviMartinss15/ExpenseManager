package menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import exception.DbException;
import expenses.Spent;
import expenses.Enum.ExpenseType;
import service.Service;

public class Menu {
	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(java.time.format.ResolverStyle.STRICT);

	public static void menu() {
		Scanner sc = new Scanner(System.in);
		Service service = new Service();
		int choice;

		do {
			System.out.println();
			System.out.println("////");
			System.out.println("What do you want to do with your expenses?");
			System.out.println("1 - List all expenses");
			System.out.println("2 - Insert an expense");
			System.out.println("3 - Delete an expense");
			System.out.println("4 - Update an expense");
			System.out.println("5 - Search expenses by date");
			System.out.println("6 - List fixed expenses");
			System.out.println("7 - List variable expenses");
			System.out.println("8 - Show totals");
			System.out.println("9 - Delete all expenses");
			System.out.println("0 - Exit");

			choice = readInt(sc, "Choice: ");

			try {
				switch (choice) {
				case 1:
					service.getExpenseList().showExpenses();
					break;
				case 2:
					BigDecimal amount = readAmount(sc, "Amount: ");
					System.out.print("Description: ");
					String description = sc.nextLine();
					LocalDate date = readDate(sc, "Date (dd/MM/yyyy): ");
					ExpenseType type = readType(sc);
					service.insertExpense(new Spent(0, amount, description, date, type));
					break;
				case 3:
					service.deleteExpense(readInt(sc, "Id of the expense to delete: "));
					break;
				case 4:
					updateExpense(sc, service);
					break;
				case 5:
					LocalDate start = readDate(sc, "Start date (dd/MM/yyyy): ");
					LocalDate end = readDate(sc, "End date (dd/MM/yyyy): ");
					service.searchWithDate(start, end).showExpenses();
					break;
				case 6:
					service.getFixedExpenses().showExpenses();
					break;
				case 7:
					service.getVariableExpenses().showExpenses();
					break;
				case 8:
					service.getExpenseList().cauculateExpensesFinalPrice();
					break;
				case 9:
					System.out.print("All expenses will be deleted. Continue? (Y/N): ");
					if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
						service.deleteAllExpenses();
					} else {
						System.out.println("Operation canceled!");
					}
					break;
				case 0:
					System.out.println("Closing expense list!");
					break;
				default:
					System.out.println("Invalid option!");
				}
			} catch (DbException e) {
				System.out.println("Database error: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid value: " + e.getMessage());
			}
		} while (choice != 0);

		sc.close();
	}

	private static void updateExpense(Scanner sc, Service service) {
		int id = readInt(sc, "Id of the expense to update: ");
		System.out.println("What do you want to change? 1 - amount | 2 - description | 3 - date | 4 - type");

		switch (readInt(sc, "Choice: ")) {
		case 1:
			service.updateAmount(id, readAmount(sc, "New amount: "));
			break;
		case 2:
			System.out.print("New description: ");
			service.updateDescription(id, sc.nextLine());
			break;
		case 3:
			service.updateExpenseDate(id, readDate(sc, "New date (dd/MM/yyyy): "));
			break;
		case 4:
			service.updateExpenseType(id, readType(sc));
			break;
		default:
			System.out.println("Operation canceled!");
		}
	}

	private static int readInt(Scanner sc, String msg) {
		while (true) {
			System.out.print(msg);
			try {
				return Integer.parseInt(sc.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Please type a whole number.");
			}
		}
	}

	private static BigDecimal readAmount(Scanner sc, String msg) {
		while (true) {
			System.out.print(msg);
			try {
				return new BigDecimal(sc.nextLine().trim().replace(",", "."));
			} catch (NumberFormatException e) {
				System.out.println("Please type a valid amount (e.g. 49.90).");
			}
		}
	}

	private static LocalDate readDate(Scanner sc, String msg) {
		while (true) {
			System.out.print(msg);
			try {
				return LocalDate.parse(sc.nextLine().trim(), FMT);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date, use dd/MM/yyyy.");
			}
		}
	}

	private static ExpenseType readType(Scanner sc) {
		while (true) {
			int t = readInt(sc, "Type (1 - fixed | 2 - variable): ");
			if (t == 1)
				return ExpenseType.FIXED;
			if (t == 2)
				return ExpenseType.VARIABLE;
			System.out.println("Choose 1 or 2.");
		}
	}
}
