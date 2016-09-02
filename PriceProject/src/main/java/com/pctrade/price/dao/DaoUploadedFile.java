package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.UploadedFile;

public interface DaoUploadedFile {

	List<UploadedFile> showAllUploadedFileInfoList();

	UploadedFile showUploadedFileInfoById(Integer uploadedFiletId);

	void createUploadedFileInfo(UploadedFile file); 

	void updateUploadedFileInfo(UploadedFile file);

	void deleteUploadedFileInfo(Integer uploadedFiletId);

	void clearTable();

}
