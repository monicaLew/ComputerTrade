package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.UploadedFile;

public interface DaoUploadedFile {

	List<UploadedFile> showAllUploadedFileInfoList() throws DaoException;

	UploadedFile showUploadedFileInfoById(Integer uploadedFiletId) throws DaoException;

	void createUploadedFileInfo(UploadedFile file) throws DaoException;  //+

	void deleteUploadedFileInfo(Integer uploadedFiletId) throws DaoException;

	void clearTable() throws DaoException;

}
