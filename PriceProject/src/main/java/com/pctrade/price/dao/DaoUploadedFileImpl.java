package com.pctrade.price.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pctrade.price.entity.UploadedFile;
import com.pctrade.price.mysql.ConnectionManager;

public class DaoUploadedFileImpl implements DaoUploadedFile {

	private static final String SELECT_ALL_FILE_INFO = "SELECT `ID`, `FILE_NAME`, `SIZE_KB`, `UPLOAD_DATE`, `CREATED`, `LAST_UPDATED` FROM FILE_INFO";
	private static final String SELECT_FILE_INFO_BY_ID = "SELECT `ID`, `FILE_NAME`, `SIZE_KB`, `UPLOAD_DATE`, `CREATED`, `LAST_UPDATED` FROM FILE_INFO WHERE ID =";
	private static final String INSERT_INTO_FILE_INFO = "INSERT INTO FILE_INFO(`FILE_NAME` ,`SIZE_KB` ,`UPLOAD_DATE`) VALUES (?,?,?)";
	private static final String DELETE_FILE_INFO_BY_ID = "DELETE FROM FILE_INFO WHERE ID =";
	private static final String DELETE = "DELETE FROM FILE_INFO";

	// private static final String vs private final static String ???

	public List<UploadedFile> loadAllUploadedFileInfoList() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<UploadedFile> uploadedFileInfoList = new ArrayList<UploadedFile>();

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ALL_FILE_INFO);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UploadedFile uploadedFile = new UploadedFile();
				uploadedFile.setId(resultSet.getInt("ID"));
				uploadedFile.setFileName(resultSet.getString("FILE_NAME"));
				uploadedFile.setSizeKb(resultSet.getInt("SIZE_KB"));
				uploadedFile.setUploadDate(resultSet.getString("UPLOAD_DATE"));
				uploadedFile.setCreated(resultSet.getString("CREATED"));
				uploadedFile.setCreated(resultSet.getString("LAST_UPDATED"));
				uploadedFileInfoList.add(uploadedFile);
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement, resultSet);
		}

		return uploadedFileInfoList;
	}

	public UploadedFile loadUploadedFileInfoById(Integer uploadedFiletId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UploadedFile uploadedFile = new UploadedFile();

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_FILE_INFO_BY_ID);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			uploadedFile.setId(resultSet.getInt("ID"));
			uploadedFile.setFileName(resultSet.getString("FILE_NAME"));
			uploadedFile.setSizeKb(resultSet.getInt("SIZE_KB"));
			uploadedFile.setUploadDate(resultSet.getString("UPLOAD_DATE"));
			uploadedFile.setCreated(resultSet.getString("CREATED"));
			uploadedFile.setCreated(resultSet.getString("LAST_UPDATED")); 

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement, resultSet);
		}
		return uploadedFile;
	}

	public void createUploadedFileInfo(UploadedFile file) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_INTO_FILE_INFO);
			preparedStatement.setString(1, file.getFileName());
			preparedStatement.setInt(2, file.getSizeKb());
			preparedStatement.setString(3, file.getUploadDate());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}

	}

	public void updateUploadedFileInfo(UploadedFile file) {
		System.out.println(" Empty method ( ");
	}

	public void deleteUploadedFileInfo(Integer uploadedFiletId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_FILE_INFO_BY_ID + uploadedFiletId);
			preparedStatement.executeUpdate();

			if (preparedStatement != null) { // как закрыть препСтэйтмент
				preparedStatement.close();
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}
	}

	public void deleteTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			ConnectionManager.closeDbResources(connection, preparedStatement);
		}
	}
}
