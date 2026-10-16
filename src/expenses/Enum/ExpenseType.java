package expenses.Enum;
public enum ExpenseType {
	VARIABLE, FIXED;

	public String toDbValue() {
		switch (this) {
		case VARIABLE:
			return "variable";
		case FIXED:
			return "fixed";
		default:
			throw new IllegalArgumentException("Unknown progress: " + this);
		}
	}

	public static ExpenseType fromDbValue(String value) {
		switch (value) {
		case "variable":
			return VARIABLE;
		case "fixed":
			return FIXED;
		default:
			throw new IllegalArgumentException("Unknown db value: " + value);
		}
	}
}
