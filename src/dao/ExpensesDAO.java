package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connection.ManageConnection;
import exception.DbException;
import expenses.Spent;
import expenses.Enum.ExpenseType;

public class ExpensesDAO {
	public void insertNewExpense(Spent spent) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO expenses (amount, description, expense_date, expense_type) VALUES (?, ?, ?, ?);";
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setBigDecimal(1, spent.getAmount());
			preparedStatement.setString(2, spent.getDescription());
			preparedStatement.setDate(3, java.sql.Date.valueOf(spent.getExpenseDate()));
			preparedStatement.setString(4, spent.getExpenseType().toDbValue());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public boolean deleteExpense(int id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String query = "DELETE FROM expenses WHERE id = (?);";
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, id);

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}

	}

	public void deleteAll() {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String query = "DELETE FROM expenses;";
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}

	}

	public List<Spent> listExpenses() {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM expenses;";
		List<Spent> expensesList = new ArrayList<>();
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				expensesList.add(generateSpent(resultSet));
			}
			return expensesList;
		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeResultSet(resultSet);
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public List<Spent> searchWithDate(LocalDate start, LocalDate end) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM expenses WHERE expense_date BETWEEN (?) and (?);";
		List<Spent> expensesList = new ArrayList<>();
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, java.sql.Date.valueOf(start));
			preparedStatement.setDate(2, java.sql.Date.valueOf(end));
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				expensesList.add(generateSpent(resultSet));
			}
			return expensesList;
		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeResultSet(resultSet);
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public boolean updateAmount(int id, BigDecimal newAmount) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE expenses SET amount = (?) WHERE id = (?);";
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);

			preparedStatement.setBigDecimal(1, newAmount);
			preparedStatement.setInt(2, id);

			return preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public boolean updateDescription(int id, String newDescription) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE expenses SET description = (?) WHERE id = (?);";
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);

			preparedStatement.setString(1, newDescription);
			preparedStatement.setInt(2, id);
			return preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public boolean updateExpenseDate(int id, LocalDate newDate) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE expenses SET expense_date = (?) WHERE id = (?);";
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);

			preparedStatement.setDate(1, java.sql.Date.valueOf(newDate));
			preparedStatement.setInt(2, id);

			return preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public boolean updateExpenseType(int id, ExpenseType newType) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String query = "UPDATE expenses SET expense_type = (?) WHERE id = (?);";
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);

			preparedStatement.setString(1, newType.toDbValue());
			preparedStatement.setInt(2, id);

			return preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public List<Spent> listVariableExpenses() {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM expenses WHERE expense_type = 'variable';";
		List<Spent> expensesList = new ArrayList<>();
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				expensesList.add(generateSpent(resultSet));
			}
			return expensesList;
		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeResultSet(resultSet);
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	public List<Spent> listFixedExpenses() {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM expenses WHERE expense_type = 'fixed';";
		List<Spent> expensesList = new ArrayList<>();
		try {
			conn = ManageConnection.openConnect();
			preparedStatement = conn.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				expensesList.add(generateSpent(resultSet));
			}
			return expensesList;
		} catch (SQLException e) {
			throw new DbException("An error was detected: " + e.getMessage());
		} finally {
			ManageConnection.closeResultSet(resultSet);
			ManageConnection.closeStatement(preparedStatement);
			ManageConnection.closeConnection(conn);
		}
	}

	private Spent generateSpent(ResultSet rs)throws SQLException {
		Spent spent = new Spent(
			rs.getInt("id"),
			rs.getBigDecimal("amount"),
			rs.getString("description"),
			rs.getDate("expense_date").toLocalDate(),
			ExpenseType.fromDbValue(rs.getString("expense_type")));
		return spent;
	}
}
