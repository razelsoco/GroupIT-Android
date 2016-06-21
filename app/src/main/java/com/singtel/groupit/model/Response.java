package com.singtel.groupit.model;


public class Response {
	
	Status status;
	
	public Response() { }
	
	public Response(Status status) {
		super();
		this.status = status;
	}
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status == null ? "null" : status.toString();
	}
	
}
