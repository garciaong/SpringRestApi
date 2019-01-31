package com.calculator.ws.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrtaResponse {

	@JsonProperty(value = "version")
    private String version;
	@JsonProperty(value = "revision_date")
    private String revisionDate;
	@JsonProperty(value = "gender")
    private String gender;
	@JsonProperty(value = "birth_date")
    private String dateOfBirth;
	@JsonProperty(value = "next_birthday_age")
    private String nextBirthdayAge;
	@JsonProperty(value = "staff_indicator")
    private String staffIndicator;
	@JsonProperty(value = "smoker_indicator")
    private String smokerIndicator;
	@JsonProperty(value = "occupation_class")
    private String occupationClass;
	@JsonProperty(value = "loan_amount")
    private String loanAmount;
	@JsonProperty(value = "loan_period")
    private String loanPeriod;
	@JsonProperty(value = "loan_interest")
    private String loanInterest;
	@JsonProperty(value = "financing")
    private String financing;
	@JsonProperty(value = "stamp_duty")
    private String stampDuty;
	@JsonProperty(value = "servicing")
    private String servicing;
	@JsonProperty("loan_interest_rate")
	private LoanInterestRate loanInterestRate;
	@JsonProperty(value = "initial_sum_insured")
    private String initialSumInsured;
	@JsonProperty(value = "term_of_coverage")
    private String termOfCoverage;
	@JsonProperty(value = "pre_underwriting_result")
    private String preUnderwritingResult;
	@JsonProperty(value = "premium_amount")
    private String premiumAmount;
	@JsonProperty(value = "policy_year")
    private String policyYear;
	@JsonProperty(value = "guaranteed_premium")
    private String guaranteedPremium;
	@JsonProperty(value = "guaranteed_reducing_sa")
    private String guaranteedReducingSa;
	@JsonProperty(value = "guaranteed_surrender_value")
    private String guaranteedSurrenderValue;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getNextBirthdayAge() {
		return nextBirthdayAge;
	}
	public void setNextBirthdayAge(String nextBirthdayAge) {
		this.nextBirthdayAge = nextBirthdayAge;
	}
	public String getStaffIndicator() {
		return staffIndicator;
	}
	public void setStaffIndicator(String staffIndicator) {
		this.staffIndicator = staffIndicator;
	}
	public String getSmokerIndicator() {
		return smokerIndicator;
	}
	public void setSmokerIndicator(String smokerIndicator) {
		this.smokerIndicator = smokerIndicator;
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
	public String getLoanInterest() {
		return loanInterest;
	}
	public void setLoanInterest(String loanInterest) {
		this.loanInterest = loanInterest;
	}
	public String getFinancing() {
		return financing;
	}
	public void setFinancing(String financing) {
		this.financing = financing;
	}
	public String getStampDuty() {
		return stampDuty;
	}
	public void setStampDuty(String stampDuty) {
		this.stampDuty = stampDuty;
	}
	public String getServicing() {
		return servicing;
	}
	public void setServicing(String servicing) {
		this.servicing = servicing;
	}
	public LoanInterestRate getLoanInterestRate() {
		return loanInterestRate;
	}
	public void setLoanInterestRate(LoanInterestRate loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}
	public String getInitialSumInsured() {
		return initialSumInsured;
	}
	public void setInitialSumInsured(String initialSumInsured) {
		this.initialSumInsured = initialSumInsured;
	}
	public String getTermOfCoverage() {
		return termOfCoverage;
	}
	public void setTermOfCoverage(String termOfCoverage) {
		this.termOfCoverage = termOfCoverage;
	}
	public String getPreUnderwritingResult() {
		return preUnderwritingResult;
	}
	public void setPreUnderwritingResult(String preUnderwritingResult) {
		this.preUnderwritingResult = preUnderwritingResult;
	}
	public String getPremiumAmount() {
		return premiumAmount;
	}
	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}
	public String getPolicyYear() {
		return policyYear;
	}
	public void setPolicyYear(String policyYear) {
		this.policyYear = policyYear;
	}
	public String getGuaranteedPremium() {
		return guaranteedPremium;
	}
	public void setGuaranteedPremium(String guaranteedPremium) {
		this.guaranteedPremium = guaranteedPremium;
	}
	public String getGuaranteedReducingSa() {
		return guaranteedReducingSa;
	}
	public void setGuaranteedReducingSa(String guaranteedReducingSa) {
		this.guaranteedReducingSa = guaranteedReducingSa;
	}
	public String getGuaranteedSurrenderValue() {
		return guaranteedSurrenderValue;
	}
	public void setGuaranteedSurrenderValue(String guaranteedSurrenderValue) {
		this.guaranteedSurrenderValue = guaranteedSurrenderValue;
	}
	
}
