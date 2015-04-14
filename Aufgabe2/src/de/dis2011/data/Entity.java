package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Makler-Bean
 * 
 * Beispiel-Tabelle:
 * CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
 * name varchar(255),
 * address varchar(255),
 * login varchar(40) UNIQUE,
 * password varchar(40));
 */
public abstract class Entity {

	/**
	 * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 *
	 * @return if saving was successful
	 */
	public boolean save() {
		PreparedStatement preparedStatement;

		try {
			// Insert entity if it wasn't persisted yet.
			if (getId() == -1) {
				preparedStatement = createInsertStatement();
				preparedStatement.executeUpdate();

				// Save ID.
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}
				rs.close();

			} else {
				// Update entity if it exists already.
				preparedStatement = createUpdateStatement();
				preparedStatement.executeUpdate();
			}
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return if loading was successful
	 */
	public boolean load(int id) {
		try {
			PreparedStatement selectStatement = createSelectStatement();
			
			// Execute select query.
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next()) {
				setId(id);
				applyResultSet(resultSet);

				resultSet.close();
				selectStatement.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	abstract public void applyResultSet(ResultSet resultSet) throws SQLException;

	abstract public PreparedStatement createSelectStatement() throws SQLException;

	abstract public PreparedStatement createInsertStatement() throws SQLException;

	abstract public PreparedStatement createUpdateStatement() throws SQLException;

	abstract public int getId();

	abstract public void setId(int id);

	protected static Connection getConnection() {
		return DB2ConnectionManager.getInstance().getConnection();
	}
}
