package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.UploadedFile;

public interface DaoUploadedFile {

	public List<UploadedFile> loadAllUploadedFileInfoList();

	public UploadedFile loadUploadedFileInfoById(Integer uploadedFiletId);

	public void createUploadedFileInfo(UploadedFile file); // INSERT

	public void updateUploadedFileInfo(UploadedFile file);

	public void deleteUploadedFileInfo(Integer uploadedFiletId);

}
