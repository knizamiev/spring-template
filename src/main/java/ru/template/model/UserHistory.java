package ru.template.model;

public class UserHistory {

	public UserHistory() {
	}

	public UserHistory(long userId, Status status) {
		this.userId = userId;
		this.status = status;
	}

	public enum Status{
		CREATE,
		UPDATE,
		DELETE
	}

	private long id;
	private long userId;
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
