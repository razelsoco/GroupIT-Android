
package com.singtel.groupit.model;


public class Status {

   	private int code;
   	private String message;

 	public Status(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public Status(int code) {
		super();
		this.code = code;
	}

	@Override
	public String toString() {
		return new StringBuffer()
				.append("Status [ code: ").append(code)
				.append(", message: ").append(message).append("]")
				.toString();
	}

	public int getCode(){
		return this.code;
	}
	public void setCode(int code){
		this.code = code;
	}
 	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}

}
