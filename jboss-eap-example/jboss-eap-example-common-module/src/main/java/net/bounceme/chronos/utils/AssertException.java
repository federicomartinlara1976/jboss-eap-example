package net.bounceme.chronos.utils;

public class AssertException extends IllegalArgumentException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	public AssertException() {
		super();
	}
	
	public AssertException(String s) {
		super(s);
	}

	public AssertException(String s, String code) {
		super(s);
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

}
