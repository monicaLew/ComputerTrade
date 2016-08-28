package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.UploadedFile;

public interface DaoUploadedFile {

	List<UploadedFile> loadAllUploadedFileInfoList();

	UploadedFile loadUploadedFileInfoById(Integer uploadedFiletId);

	void createUploadedFileInfo(UploadedFile file); // INSERT

	void updateUploadedFileInfo(UploadedFile file);

	void deleteUploadedFileInfo(Integer uploadedFiletId);

	void deleteTable();

}
