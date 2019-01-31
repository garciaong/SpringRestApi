package com.calculator.ws.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrtaRequest {

	@JsonProperty(value = "quotation_date")
    private String quotationDate;
	@JsonProperty(value = "year")
    private String year;
	@JsonProperty(value = "language")
    private String language;
	@JsonProperty(value = "birth_date")
    private String dateOfBirth;
	@JsonProperty(value = "gender")
    private String gender;
	@JsonProperty(value = "smoker_indicator")
    private String smokerIndicator;
	@JsonProperty(value = "staff_indicator")
    private String staffIndicator;
	@JsonProperty(value = "occupation_class")
    private String occupationClass;
	@JsonProperty(value = "loan_amount")
    private String loanAmount;
	@JsonProperty(value = "loan_period")
    private String loanPeriod;
	@JsonProperty(value = "cover_period")
    private String coverPeriod;
	@JsonProperty(value = "current_blr")
    private String currentBlr;
	@JsonProperty(value = "financing")
    private String financing;
	@JsonProperty(value = "servicing")
    private String servicing;
	@JsonProperty(value = "stamp_duty_indicator")
    private String stampDutyIndicator;
	
	public String getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(String quotationDate) {
		this.quotationDate = quotationDate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSmokerIndicator() {
		return smokerIndicator;
	}
	public void setSmokerIndicator(String smokerIndicator) {
		this.smokerIndicator = smokerIndicator;
	}
	public String getStaffIndicator() {
		return staffIndicator;
	}
	public void setStaffIndicator(String staffIndicator) {
		this.staffIndicator = staffIndicator;
	}
	public String getOccupationClass() {
		return occupationClass;
	}
	public void setOccupationClass(String occupationClass) {
		this.occupationClass = occupationClass;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getCoverPeriod() {
		return coverPeriod;
	}
	public void setCoverPeriod(String coverPeriod) {
		this.coverPeriod = coverPeriod;
	}
	public String getCurrentBlr() {
		return currentBlr;
	}
	public void setCurrentBlr(String currentBlr) {
		this.currentBlr = currentBlr;
	}
	public String getFinancing() {
		return financing;
	}
	public void setFinancing(String financing) {
		this.financing = financing;
	}
	public String getServicing() {
		return servicing;
	}
	public void setServicing(String servicing) {
		this.servicing = servicing;
	}
	public String getStampDutyIndicator() {
		return stampDutyIndicator;
	}
	public void setStampDutyIndicator(String stampDutyIndicator) {
		this.stampDutyIndicator = stampDutyIndicator;
	}
	
}
