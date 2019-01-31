package com.calculator.ws.restful.enums;

public enum ErrorEnum {

	INVALID_OBJECT_REFERENCE("E001","Invalid object reference (null pointer exception)"),
	VPM_FILE_LOAD_ERROR("E002","VPM file loading exception. Please check configuration/file path"),
	PROPERTIES_ERROR("E003","Properties file initialization error detected"),
	FILE_ERROR("E004","File reading/writing error detected"),
	INVALID_VALUE_REFERENCE("E005","Invalid value reference (value is empty)"),
	DATE_FORMAT_ERROR("E006","Date format error detected"),
	CAPITAL_ALPHABETS_ERROR("E007","Value must be capital alphabets"),
	ALPHABETS_ERROR("E008","Value must be alphabets"),
	NUMERIC_ERROR("E009","Value must be numeric"),
	YES_NO_ERROR("E010","Value must be Y or N"),
	DIGITS_ERROR("E011","Value must be digits"),
	STAFF_INDICATION_ERROR("E012","Wrong staff indicator specified"),
	INVALID_GENDER_ERROR("E013","Wrong gender specified"),
	VPMS_VALIDATION_ERROR("E014","VPMS VALIDATION ERROR"),
	UNHANDLED_ERROR("E999","Unhandled exception");
	
	private final String code;
	private final String message;
	
	ErrorEnum(String code, String message){
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
