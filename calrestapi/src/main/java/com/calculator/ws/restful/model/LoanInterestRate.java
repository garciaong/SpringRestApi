package com.calculator.ws.restful.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoanInterestRate {

	@JsonProperty(value = "year_1")
    private String year1;
	@JsonProperty(value = "year_2")
    private String year2;
	@JsonProperty(value = "year_3")
    private String year3;
	@JsonProperty(value = "year_4")
    private String year4;
	@JsonProperty(value = "year_5")
    private String year5;
	@JsonProperty(value = "year_6")
    private String year6;
	@JsonProperty(value = "year_7")
    private String year7;
	@JsonProperty(value = "year_7_above")
    private String year7Above;
	
	public String getYear1() {
		return year1;
	}
	public void setYear1(String year1) {
		this.year1 = year1;
	}
	public String getYear2() {
		return year2;
	}
	public void setYear2(String year2) {
		this.year2 = year2;
	}
	public String getYear3() {
		return year3;
	}
	public void setYear3(String year3) {
		this.year3 = year3;
	}
	public String getYear4() {
		return year4;
	}
	public void setYear4(String year4) {
		this.year4 = year4;
	}
	public String getYear5() {
		return year5;
	}
	public void setYear5(String year5) {
		this.year5 = year5;
	}
	public String getYear6() {
		return year6;
	}
	public void setYear6(String year6) {
		this.year6 = year6;
	}
	public String getYear7() {
		return year7;
	}
	public void setYear7(String year7) {
		this.year7 = year7;
	}
	public String getYear7Above() {
		return year7Above;
	}
	public void setYear7Above(String year7Above) {
		this.year7Above = year7Above;
	}
}
