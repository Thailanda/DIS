package de.dis2011.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Konstantin Simon Maria Moellers
 * @version 2015-04-07
 */
public class Contract extends Entity {
	private int id = -1;
	private String contractNo = "";
	private Date date = new Date(0);
	private String place = "";

	@Override
	protected String getFindAllSql() {
		return "SELECT * FROM CONTRACT";
	}

	@Override
	public void applyResultSet(ResultSet resultSet) throws SQLException {
		this.setId(resultSet.getInt("id"));
		this.setContractNo(resultSet.getString("contract_no"));
		this.setDate(resultSet.getDate("date"));
		this.setPlace(resultSet.getString("place"));
	}

	@Override
	public PreparedStatement createSelectStatement() throws SQLException {
		String selectSQL = "SELECT * FROM CONTRACT WHERE id = ?";
		PreparedStatement preparedStatement = getConnection().prepareStatement(
				selectSQL);
		preparedStatement.setInt(1, id);

		return preparedStatement;
	}

	@Override
	public PreparedStatement createInsertStatement() throws SQLException {
		String insertSQL = "INSERT INTO CONTRACT (CONTRACT_NO, DATE, PLACE) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = getConnection().prepareStatement(
				insertSQL, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setString(1, getContractNo());
		preparedStatement.setDate(2, getDate());
		preparedStatement.setString(3, getPlace());

		return preparedStatement;
	}

	@Override
	public PreparedStatement createUpdateStatement() throws SQLException {
		String updateSQL = "UPDATE CONTRACT SET CONTRACT_NO=?, DATE=?, PLACE=? WHERE ID=?";
		PreparedStatement preparedStatement = getConnection().prepareStatement(
				updateSQL);

		preparedStatement.setString(1, getContractNo());
		preparedStatement.setDate(2, getDate());
		preparedStatement.setString(3, getPlace());
		preparedStatement.setInt(4, getId());

		return preparedStatement;
	}

	public boolean drop() {
		try {
			PreparedStatement preparedStatement = getConnection()
					.prepareStatement("DELETE FROM CONTRACT WHERE ID=?");
			preparedStatement.setInt(1, getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public static List<Contract> findAllContracts() {
		ArrayList<Contract> contracts = new ArrayList<Contract>();

		try {
			String sql = "SELECT e.*, a.*, h.*, "
					+ "a.CONTRACT_ID as TENANCY_ID, h.CONTRACT_ID as PURCHASE_ID FROM CONTRACT e LEFT JOIN TENANCY_CONTRACT a ON e.ID = a.CONTRACT_ID"
					+ " LEFT JOIN PURCHASE_CONTRACT h ON e.ID = h.CONTRACT_ID";
			PreparedStatement statement = getConnection().prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				resultSet.getInt("purchase_id");
				if (!resultSet.wasNull()) {
					PurchaseContract e = new PurchaseContract();
					e.applyResultSet(resultSet);
					contracts.add(e);
					continue;
				}

				resultSet.getInt("tenancy_id");
				if (!resultSet.wasNull()) {
					TenancyContract e = new TenancyContract();
					e.applyResultSet(resultSet);
					contracts.add(e);
					continue;
				}

				throw new SQLException(
						"Inconsistent DB state: Found estate which is neither apartment nor house: #"
								+ resultSet.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contracts;
	}
}
