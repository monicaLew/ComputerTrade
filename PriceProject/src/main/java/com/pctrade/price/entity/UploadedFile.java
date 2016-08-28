package com.pctrade.price.entity;

public class UploadedFile {

	private Integer id;
	private String fileName;
	private Integer sizeKb;
	private String uploadDate;
	private String created;
	private String updated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getSizeKb() {
		return sizeKb;
	}

	public void setSizeKb(Integer sizeKb) {
		this.sizeKb = sizeKb;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "UploadedFile [id=" + id + ", fileName=" + fileName + ", sizeKb=" + sizeKb + ", uploadDate=" + uploadDate
				+ ", created=" + created + ", updated=" + updated + "]";
	}
}
