package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import utillities.DBConnection;
import model.User;

public class UserDao {

	private DBConnection dbConnection;

	public DBConnection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public UserDao(DBConnection dbConnection) {
		super();
		this.dbConnection = dbConnection;
	}

	private User createUser(ResultSet resultSet) throws SQLException {
		return new User(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("phoneNumber"),
				resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("city"),
				resultSet.getString("district"), resultSet.getString("address"));

	}

	public int addUser(User employee) throws ClassNotFoundException {/////////////////
		String INSERT_USERS_SQL = "INSERT INTO users"
				+ "  (name, surnamename, phoneNumber, email, password, city, district, address) VALUES "
				+ " (?, ?, ?, ?, ?, ?, ?, ?);";

		int result = 0;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getSurname());
			preparedStatement.setString(3, employee.getPhoneNumber());
			preparedStatement.setString(4, employee.getEmail());
			preparedStatement.setString(5, employee.getPassword());
			preparedStatement.setString(6, employee.getCity());
			preparedStatement.setString(7, employee.getDistrict());
			preparedStatement.setString(8, employee.getAddress());

			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			DBConnection.printSQLException(e);
		}
		return result;
	}

	public Optional<User> getById(int id) throws Exception {

		ResultSet resultSet = null;

		try (var connection = DBConnection.getConnection();
				var statement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID = ?")) {

			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(createUser(resultSet));
			} else {
				return Optional.empty();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

}
