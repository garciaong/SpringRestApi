package com.calculator.ws.restful.service;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.calculator.ws.restful.constants.PropertiesConstants;
import com.calculator.ws.restful.enums.ErrorEnum;
import com.calculator.ws.restful.exception.CapitalAlphabetsException;
import com.calculator.ws.restful.exception.DateFormatException;
import com.calculator.ws.restful.exception.DigitsException;
import com.calculator.ws.restful.exception.GenderException;
import com.calculator.ws.restful.exception.InvalidValueReferenceException;
import com.calculator.ws.restful.exception.NumericException;
import com.calculator.ws.restful.exception.PropertiesException;
import com.calculator.ws.restful.exception.StaffException;
import com.calculator.ws.restful.exception.VpmsValidationException;
import com.calculator.ws.restful.exception.YesNoException;
import com.calculator.ws.restful.model.Data;
import com.calculator.ws.restful.model.LoanInterestRate;
import com.calculator.ws.restful.model.PrtaRequest;
import com.calculator.ws.restful.model.PrtaResponse;
import com.calculator.ws.restful.model.Response;
import com.calculator.ws.restful.model.WebServiceFormat;
import com.csc.dip.jvpms.runtime.base.IVpmsBaseSession;
import com.csc.dip.jvpms.runtime.base.IVpmsBaseSessionFactory;
import com.csc.dip.jvpms.runtime.base.VpmsComputeResult;
import com.csc.dip.jvpms.runtime.base.VpmsJniSessionFactory;
import com.csc.dip.jvpms.runtime.base.VpmsLoadFailedException;
import com.google.gson.Gson;

@Service
public class PrtaServiceImpl implements PrtaService {

	private static final Logger log = Logger.getLogger(PrtaServiceImpl.class);

	private void fieldsVpmsValidation(PrtaRequest request, IVpmsBaseSession vpmsession)
			throws VpmsValidationException {
		String[] inputToValidate = { "A_Quotation_Date", "A_Language","A_DOB", "A_Gender", "A_Smoker", "A_Occ",
				"A_LoanAmount", "A_LoanPeriod"  , "A_CurrentBLR", "A_Financing", "A_Servicing", "A_StampDuty_IND"};
		String errMsg = "";
		for (String input : inputToValidate) {
			VpmsComputeResult result = vpmsession.computeUsingDefaults(input);
			if (result.isError()) {
				errMsg = result.getMessage();
				throw new VpmsValidationException(ErrorEnum.VPMS_VALIDATION_ERROR.getCode(),
						"VPMS " + input + ": " + errMsg);
			}
		}
	}
	
	public WebServiceFormat getPrtaFormat() throws Exception {
		WebServiceFormat response = new WebServiceFormat();
		Response respFormat = new Response();
		Data data = new Data();

		// Request
		PrtaRequest prtaReq = new PrtaRequest();
		prtaReq.setQuotationDate("2018-10-01");
		prtaReq.setLanguage("E");
		prtaReq.setDateOfBirth("1990-10-01");
		prtaReq.setGender("M");
		prtaReq.setSmokerIndicator("N");
		prtaReq.setStaffIndicator("C");
		prtaReq.setOccupationClass("1");
		prtaReq.setLoanAmount("1500000");
		prtaReq.setLoanPeriod("20");
		prtaReq.setCoverPeriod("15");
		prtaReq.setCurrentBlr("35.0");
		prtaReq.setFinancing("N");
		prtaReq.setServicing("5");
		prtaReq.setStampDutyIndicator("N");
		response.setPrtaRequest(prtaReq);

		// Response
		PrtaResponse prtaResp = new PrtaResponse();
		prtaResp.setVersion("2.15");
		prtaResp.setRevisionDate("V1.0_14-Jan-2013");
		prtaResp.setGender("Male");
		prtaResp.setDateOfBirth("1990-10-01");
		prtaResp.setNextBirthdayAge("29");
		prtaResp.setStaffIndicator("Customer");
		prtaResp.setSmokerIndicator("No");
		prtaResp.setOccupationClass("1");
		prtaResp.setLoanAmount("1500000.00");
		prtaResp.setLoanPeriod("20");
		prtaResp.setLoanInterest("35.00%");
		prtaResp.setFinancing("No");
		prtaResp.setStampDuty("No");
		prtaResp.setServicing("5");
		LoanInterestRate loanInterestRate = new LoanInterestRate();
		loanInterestRate.setYear1("35.0");
		loanInterestRate.setYear2("35.0");
		loanInterestRate.setYear3("35.0");
		loanInterestRate.setYear4("35.0");
		loanInterestRate.setYear5("35.0");
		loanInterestRate.setYear6("35.0");
		loanInterestRate.setYear7("35.0");
		loanInterestRate.setYear7Above("35.0");
		prtaResp.setLoanInterestRate(loanInterestRate);
		prtaResp.setInitialSumInsured("1500000.00");
		prtaResp.setTermOfCoverage("20");
		prtaResp.setPreUnderwritingResult("");
		prtaResp.setPremiumAmount("36975");
		prtaResp.setPolicyYear("1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20");
		prtaResp.setGuaranteedPremium("36975|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0");
		prtaResp.setGuaranteedReducingSa(
				"1500000|1500000|1500000|1500000|1500000|1500000|1494112|1486162|1475431|1460943|1441385|1414981|1379336|1331215|1266252|1178552|1060156|900323|684547|393251");
		prtaResp.setGuaranteedSurrenderValue(
				"27150|26586|26020|25420|24745|23965|23063|22017|20810|19436|17887|16147|14205|12057|9719|7249|4769|2484|720|0");

		data.setPrtaResponse(prtaResp);
		respFormat.setStatus(PropertiesConstants.OK);
		respFormat.setData(data);
		response.setPrtaResponse(respFormat);

		return response;
	}

	public Response getPrtaResult(PrtaRequest request, Properties vpmProp) throws Exception {
		IVpmsBaseSessionFactory factory = null;
		IVpmsBaseSession vpmsession = null;
		Response response = new Response();
		Data data = new Data();
		PrtaResponse prtaResp = new PrtaResponse();
		boolean hasVpmError = false;

		try {
			if (vpmProp == null) {
				throw new PropertiesException(ErrorEnum.PROPERTIES_ERROR.getCode(),
						"VPM properties object initializing failed");
			}
			if (vpmProp.getProperty(PropertiesConstants.VPM_FILE_PRTT) == null
					|| "".equals(vpmProp.getProperty(PropertiesConstants.VPM_FILE_PRTT).trim())) {
				throw new InvalidValueReferenceException(ErrorEnum.INVALID_VALUE_REFERENCE.getCode(),
						"PRTT file path not detected");
			}
			//Perform request fields validation
			fieldsValidation(request);

			log.info("VPM PRTA file path = " + vpmProp.getProperty(PropertiesConstants.VPM_FILE_PRTA));
			log.info("PRTA request [Quotation Date=" + request.getQuotationDate() + "], [Year=" + request.getYear()
					+ "], [Language=" + request.getLanguage() + "], [Date of Birth=" + request.getDateOfBirth()
					+ "], [Gender=" + request.getGender() + "], [Smoker Indicator=" + request.getSmokerIndicator()
					+ "], [Staff Indicator=" + request.getStaffIndicator() + "], [OCC=" + request.getOccupationClass()
					+ "], [Loan Amount=" + request.getLoanAmount() + "], [Loan Period=" + request.getLoanPeriod()
					+ "], [Cover Period=" + request.getCoverPeriod() + "], [Current BLR=" + request.getCurrentBlr()
					+ "], [Financing=" + request.getFinancing() + "], [Servicing=" + request.getServicing()
					+ "], [Stamp Duty Indicator=" + request.getStampDutyIndicator() + "]");
			
			//Convert input date format to VPM date format
			convertToVpmDateFormat(request);
			
			factory = new VpmsJniSessionFactory();
			vpmsession = factory.create(vpmProp.getProperty(PropertiesConstants.VPM_FILE_PRTA));
			vpmsession.setAttribute("A_Quotation_Date", request.getQuotationDate());
			vpmsession.setAttribute("A_Year", request.getYear());
			vpmsession.setAttribute("A_Language", request.getLanguage());
			vpmsession.setAttribute("A_DOB", request.getDateOfBirth());
			vpmsession.setAttribute("A_Gender", request.getGender());
			vpmsession.setAttribute("A_Smoker", request.getSmokerIndicator());
			vpmsession.setAttribute("A_Staff", request.getStaffIndicator());
			vpmsession.setAttribute("A_Occ", request.getOccupationClass());
			vpmsession.setAttribute("A_LoanAmount", request.getLoanAmount());
			vpmsession.setAttribute("A_LoanPeriod", request.getLoanPeriod());
			vpmsession.setAttribute("A_CoverPeriod", request.getCoverPeriod());
			vpmsession.setAttribute("A_CurrentBLR", request.getCurrentBlr());
			vpmsession.setAttribute("A_Financing", request.getFinancing());
			vpmsession.setAttribute("A_Servicing", request.getServicing());
			vpmsession.setAttribute("A_StampDuty_IND", request.getStampDutyIndicator());

			//validate input from vpms
			fieldsVpmsValidation(request, vpmsession);
			
			// Individual Output
			VpmsComputeResult vpmVersion = vpmsession.computeUsingDefaults("P_Version");
			String version;
			if (vpmVersion.isError()) {
				hasVpmError = true;
				version = "Error on [P_Version] : " + vpmVersion.getMessage();
			} else {
				version = vpmVersion.getResult();
			}
			VpmsComputeResult vpmRevisionDate = vpmsession.computeUsingDefaults("P_Revision_Date");
			String revisionDate;
			if (vpmRevisionDate.isError()) {
				hasVpmError = true;
				revisionDate = "Error on [P_Revision_Date] : " + vpmRevisionDate.getMessage();
			} else {
				revisionDate = vpmRevisionDate.getResult();
			}

			// For Life Insured Table
			VpmsComputeResult vpmGender = vpmsession.computeUsingDefaults("P_Gender");
			String gender;
			if (vpmGender.isError()) {
				hasVpmError = true;
				gender = "Error on [P_Gender] : " + vpmGender.getMessage();
			} else {
				gender = vpmGender.getResult();
			}
			VpmsComputeResult vpmDateOfBirth = vpmsession.computeUsingDefaults("P_DOB");
			String dateOfBirth;
			if (vpmDateOfBirth.isError()) {
				hasVpmError = true;
				dateOfBirth = "Error on [P_DOB] : " + vpmDateOfBirth.getMessage();
			} else {
				dateOfBirth = vpmDateOfBirth.getResult();
			}
			VpmsComputeResult vpmAgeNB = vpmsession.computeUsingDefaults("P_AgeNB");
			String nextBirthdayAge;
			if (vpmAgeNB.isError()) {
				hasVpmError = true;
				nextBirthdayAge = "Error on [P_AgeNB] : " + vpmAgeNB.getMessage();
			} else {
				nextBirthdayAge = vpmAgeNB.getResult();
			}
			VpmsComputeResult vpmStaff = vpmsession.computeUsingDefaults("P_Staff");
			String staffIndicator;
			if (vpmStaff.isError()) {
				hasVpmError = true;
				staffIndicator = "Error on [P_Staff] : " + vpmStaff.getMessage();
			} else {
				staffIndicator = vpmStaff.getResult();
			}
			VpmsComputeResult vpmSmoker = vpmsession.computeUsingDefaults("P_Smoker");
			String smokerIndicator;
			if (vpmStaff.isError()) {
				hasVpmError = true;
				smokerIndicator = "Error on [P_Smoker] : " + vpmSmoker.getMessage();
			} else {
				smokerIndicator = vpmSmoker.getResult();
			}
			VpmsComputeResult vpmOcc = vpmsession.computeUsingDefaults("P_Occ");
			String occupationClass;
			if (vpmOcc.isError()) {
				hasVpmError = true;
				occupationClass = "Error on [P_Occ] : " + vpmOcc.getMessage();
			} else {
				occupationClass = vpmOcc.getResult();
			}

			// Loan Details
			VpmsComputeResult vpmLoanAmount = vpmsession.computeUsingDefaults("P_LoanAmount");
			String loanAmount;
			if (vpmLoanAmount.isError()) {
				hasVpmError = true;
				loanAmount = "Error on [P_LoanAmount] : " + vpmLoanAmount.getMessage();
			} else {
				loanAmount = vpmLoanAmount.getResult();
			}
			VpmsComputeResult vpmLoanPeriod = vpmsession.computeUsingDefaults("P_LoanPeriod");
			String loanPeriod;
			if (vpmLoanPeriod.isError()) {
				hasVpmError = true;
				loanPeriod = "Error on [P_LoanPeriod] : " + vpmLoanPeriod.getMessage();
			} else {
				loanPeriod = vpmLoanPeriod.getResult();
			}
			VpmsComputeResult vpmLoanInt = vpmsession.computeUsingDefaults("P_LoanInt");
			String loanInterest;
			if (vpmLoanInt.isError()) {
				hasVpmError = true;
				loanInterest = "Error on [P_LoanInt] : " + vpmLoanInt.getMessage();
			} else {
				loanInterest = vpmLoanInt.getResult();
			}
			VpmsComputeResult vpmFinancing = vpmsession.computeUsingDefaults("P_Financing");
			String financingIndicator;
			if (vpmFinancing.isError()) {
				hasVpmError = true;
				financingIndicator = "Error on [P_Financing] : " + vpmFinancing.getMessage();
			} else {
				financingIndicator = vpmFinancing.getResult();
			}
			VpmsComputeResult vpmStampDuty = vpmsession.computeUsingDefaults("P_StampDuty");
			String stampDuty;
			if (vpmStampDuty.isError()) {
				hasVpmError = true;
				stampDuty = "Error on [P_StampDuty] : " + vpmStampDuty.getMessage();
			} else {
				stampDuty = vpmStampDuty.getResult();
			}
			VpmsComputeResult vpmServicing = vpmsession.computeUsingDefaults("P_Servicing");
			String servicingIndicator;
			if (vpmServicing.isError()) {
				hasVpmError = true;
				servicingIndicator = "Error on [P_Servicing] : " + vpmServicing.getMessage();
			} else {
				servicingIndicator = vpmServicing.getResult();
			}

			// Input Sheet
			VpmsComputeResult vpmEffFinIntRate1 = vpmsession.computeUsingDefaults("P_EffFinIntRate1");
			String loanInterestRateYear1;
			if (vpmEffFinIntRate1.isError()) {
				hasVpmError = true;
				loanInterestRateYear1 = "Error on [P_EffFinIntRate1] : " + vpmEffFinIntRate1.getMessage();
			} else {
				loanInterestRateYear1 = vpmEffFinIntRate1.getResult();
			}
			VpmsComputeResult vpmEffFinIntRate2 = vpmsession.computeUsingDefaults("P_EffFinIntRate2");
			String loanInterestRateYear2;
			if (vpmEffFinIntRate2.isError()) {
				hasVpmError = true;
				loanInterestRateYear2 = "Error on [P_EffFinIntRate2] : " + vpmEffFinIntRate2.getMessage();
			} else {
				loanInterestRateYear2 = vpmEffFinIntRate2.getResult();
			}
			VpmsComputeResult vpmEffFinIntRate3 = vpmsession.computeUsingDefaults("P_EffFinIntRate3");
			String loanInterestRateYear3;
			if (vpmEffFinIntRate3.isError()) {
				hasVpmError = true;
				loanInterestRateYear3 = "Error on [P_EffFinIntRate3] : " + vpmEffFinIntRate3.getMessage();
			} else {
				loanInterestRateYear3 = vpmEffFinIntRate3.getResult();
			}
			VpmsComputeResult vpmEffFinIntRate4 = vpmsession.computeUsingDefaults("P_EffFinIntRate4");
			String loanInterestRateYear4;
			if (vpmEffFinIntRate4.isError()) {
				hasVpmError = true;
				loanInterestRateYear4 = "Error on [P_EffFinIntRate4] : " + vpmEffFinIntRate4.getMessage();
			} else {
				loanInterestRateYear4 = vpmEffFinIntRate4.getResult();
			}
			VpmsComputeResult vpmEffFinIntRate5 = vpmsession.computeUsingDefaults("P_EffFinIntRate5");
			String loanInterestRateYear5;
			if (vpmEffFinIntRate5.isError()) {
				hasVpmError = true;
				loanInterestRateYear5 = "Error on [P_EffFinIntRate5] : " + vpmEffFinIntRate5.getMessage();
			} else {
				loanInterestRateYear5 = vpmEffFinIntRate5.getResult();
			}
			VpmsComputeResult vpmEffFinIntRate6 = vpmsession.computeUsingDefaults("P_EffFinIntRate6");
			String loanInterestRateYear6;
			if (vpmEffFinIntRate6.isError()) {
				hasVpmError = true;
				loanInterestRateYear6 = "Error on [P_EffFinIntRate6] : " + vpmEffFinIntRate6.getMessage();
			} else {
				loanInterestRateYear6 = vpmEffFinIntRate6.getResult();
			}
			VpmsComputeResult vpmEffFinIntRate7 = vpmsession.computeUsingDefaults("P_EffFinIntRate7");
			String loanInterestRateYear7;
			if (vpmEffFinIntRate7.isError()) {
				hasVpmError = true;
				loanInterestRateYear7 = "Error on [P_EffFinIntRate7] : " + vpmEffFinIntRate7.getMessage();
			} else {
				loanInterestRateYear7 = vpmEffFinIntRate7.getResult();
			}
			VpmsComputeResult vpmEffFinIntRate8 = vpmsession.computeUsingDefaults("P_EffFinIntRate8");
			String loanInterestRateYear7Above;
			if (vpmEffFinIntRate8.isError()) {
				hasVpmError = true;
				loanInterestRateYear7Above = "Error on [P_EffFinIntRate8] : " + vpmEffFinIntRate8.getMessage();
			} else {
				loanInterestRateYear7Above = vpmEffFinIntRate8.getResult();
			}

			// Insurance Details
			VpmsComputeResult vpmInitialSumInsured = vpmsession.computeUsingDefaults("P_InitialSumInsured");
			String initialSumInsured;
			if (vpmInitialSumInsured.isError()) {
				hasVpmError = true;
				initialSumInsured = "Error on [P_InitialSumInsured] : " + vpmInitialSumInsured.getMessage();
			} else {
				initialSumInsured = vpmInitialSumInsured.getResult();
			}
			VpmsComputeResult vpmTermofCoverage = vpmsession.computeUsingDefaults("P_TermofCoverage");
			String termOfCoverage;
			if (vpmTermofCoverage.isError()) {
				hasVpmError = true;
				termOfCoverage = "Error on [P_TermofCoverage] : " + vpmTermofCoverage.getMessage();
			} else {
				termOfCoverage = vpmTermofCoverage.getResult();
			}
			VpmsComputeResult vpmPreUnderwritingResult = vpmsession.computeUsingDefaults("P_PreUnderwritingResult");
			String preUnderwritingResult;
			if (vpmPreUnderwritingResult.isError()) {
				hasVpmError = true;
				preUnderwritingResult = "Error on [P_PreUnderwritingResult] : " + vpmPreUnderwritingResult.getMessage();
			} else {
				preUnderwritingResult = vpmPreUnderwritingResult.getResult();
			}

			// Premium Details
			VpmsComputeResult vpmPremiumAmount = vpmsession.computeUsingDefaults("P_PremiumAmount");
			String premiumAmount = vpmPremiumAmount.getResult();
			if (vpmPremiumAmount.isError()) {
				hasVpmError = true;
				premiumAmount = "Error on [P_PremiumAmount] : " + vpmPremiumAmount.getMessage();
			} else {
				premiumAmount = vpmPremiumAmount.getResult();
			}

			// Illustration Table
			VpmsComputeResult vpmPolYear = vpmsession.computeUsingDefaults("P_O_PolYear");
			String policyYear;
			if (vpmPolYear.isError()) {
				hasVpmError = true;
				policyYear = "Error on [P_O_PolYear] : " + vpmPolYear.getMessage();
			} else {
				policyYear = vpmPolYear.getResult();
			}
			VpmsComputeResult vpmGuaranteedPremium = vpmsession.computeUsingDefaults("P_O_GuaranteedPremium");
			String guaranteedPremium;
			if (vpmGuaranteedPremium.isError()) {
				hasVpmError = true;
				guaranteedPremium = "Error on [P_O_GuaranteedPremium] : " + vpmGuaranteedPremium.getMessage();
			} else {
				guaranteedPremium = vpmGuaranteedPremium.getResult();
			}
			VpmsComputeResult vpmGuaranteedReducingSA = vpmsession.computeUsingDefaults("P_O_GuaranteedReducingSA");
			String guaranteedReducingSA;
			if (vpmGuaranteedReducingSA.isError()) {
				hasVpmError = true;
				guaranteedReducingSA = "Error on [P_O_GuaranteedReducingSA] : " + vpmGuaranteedReducingSA.getMessage();
			} else {
				guaranteedReducingSA = vpmGuaranteedReducingSA.getResult();
			}
			VpmsComputeResult vpmGuaranteedSurrenderValue = vpmsession
					.computeUsingDefaults("P_O_GuaranteedSurrenderValue");
			String guaranteedSurrenderValue;
			if (vpmGuaranteedSurrenderValue.isError()) {
				hasVpmError = true;
				guaranteedSurrenderValue = "Error on [P_O_GuaranteedSurrenderValue] : "
						+ vpmGuaranteedSurrenderValue.getMessage();
			} else {
				guaranteedSurrenderValue = vpmGuaranteedSurrenderValue.getResult();
			}

			prtaResp.setVersion(version);
			prtaResp.setRevisionDate(revisionDate);

			prtaResp.setGender(gender);
			prtaResp.setDateOfBirth(dateOfBirth);
			prtaResp.setNextBirthdayAge(nextBirthdayAge);
			prtaResp.setStaffIndicator(staffIndicator);
			prtaResp.setSmokerIndicator(smokerIndicator);
			prtaResp.setOccupationClass(occupationClass);

			prtaResp.setLoanAmount(loanAmount);
			prtaResp.setLoanInterest(loanInterest);
			prtaResp.setLoanPeriod(loanPeriod);
			prtaResp.setFinancing(financingIndicator);
			prtaResp.setStampDuty(stampDuty);
			prtaResp.setServicing(servicingIndicator);

			LoanInterestRate loanInterestRate = new LoanInterestRate();
			loanInterestRate.setYear1(loanInterestRateYear1);
			loanInterestRate.setYear2(loanInterestRateYear2);
			loanInterestRate.setYear3(loanInterestRateYear3);
			loanInterestRate.setYear4(loanInterestRateYear4);
			loanInterestRate.setYear5(loanInterestRateYear5);
			loanInterestRate.setYear6(loanInterestRateYear6);
			loanInterestRate.setYear7(loanInterestRateYear7);
			loanInterestRate.setYear7Above(loanInterestRateYear7Above);
			prtaResp.setLoanInterestRate(loanInterestRate);

			prtaResp.setInitialSumInsured(initialSumInsured);
			prtaResp.setTermOfCoverage(termOfCoverage);
			prtaResp.setPreUnderwritingResult(preUnderwritingResult);

			prtaResp.setPremiumAmount(premiumAmount);

			prtaResp.setPolicyYear(policyYear);
			prtaResp.setGuaranteedPremium(guaranteedPremium);
			prtaResp.setGuaranteedReducingSa(guaranteedReducingSA);
			prtaResp.setGuaranteedSurrenderValue(guaranteedSurrenderValue);

			//Convert VPM date format to output date format
			convertToRespDateFormat(prtaResp);
			
			data.setPrtaResponse(prtaResp);
			response.setStatus(PropertiesConstants.OK);
			response.setData(data);

			Gson gson = new Gson();
			log.info("PRTA Response : \n" + gson.toJson(response));

			if (hasVpmError) {
				log.warn("VPM calculation error found, please refer response message for detail");
			}

		} catch (PropertiesException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (InvalidValueReferenceException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (DateFormatException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (CapitalAlphabetsException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (GenderException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (YesNoException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (StaffException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (NumericException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (DigitsException e) {
			log.error(e.getErrorCode(), e);
			throw e;
		} catch (NullPointerException e) {
			log.error("Invalid object reference found when generating PRTT response", e);
			throw e;
		} catch (VpmsLoadFailedException e) {
			log.error("Input/Output error found when generating PRTT response (probably wrong file path/name)", e);
			throw e;
		} catch (Exception e) {
			log.error("Unhandled error found when generating PRTT response", e);
			throw e;
		} finally {
			if (vpmsession != null) {
				vpmsession.close();
			}
		}

		return response;
	}

	private void fieldsValidation(PrtaRequest request)
			throws DateFormatException, CapitalAlphabetsException, YesNoException, StaffException, NumericException,
			GenderException, DigitsException {
		if (request.getQuotationDate() == null || "".equals(request.getQuotationDate().trim())
				|| request.getQuotationDate().length() != 10 || request.getQuotationDate().charAt(4) != '-'
				|| request.getQuotationDate().charAt(7) != '-') {
			throw new DateFormatException(ErrorEnum.DATE_FORMAT_ERROR.getCode(),
					"Invalid date format defected, actual date format should be yyyy-mm-dd. Field : quotation_date");
		}

		if (request.getLanguage() != null && !request.getLanguage().matches("[A-Z]")) {
			throw new CapitalAlphabetsException(ErrorEnum.CAPITAL_ALPHABETS_ERROR.getCode(),
					"Invalid input value, alphabet expected. Field : language");
		}

		if (request.getDateOfBirth() == null || "".equals(request.getDateOfBirth().trim())
				|| request.getDateOfBirth().length() != 10 || request.getDateOfBirth().charAt(4) != '-'
				|| request.getDateOfBirth().charAt(7) != '-') {
			throw new DateFormatException(ErrorEnum.DATE_FORMAT_ERROR.getCode(),
					"Invalid date format defected, actual date format should be yyyy-mm-dd. Field : birth_date");
		}

		if (request.getGender() != null && !request.getGender().matches("M|F")) {
			throw new GenderException(ErrorEnum.INVALID_GENDER_ERROR.getCode(),
					"Invalid input value, M or F expected. Field : gender");
		}

		if (request.getSmokerIndicator() != null && !request.getSmokerIndicator().matches("Y|N")) {
			throw new YesNoException(ErrorEnum.YES_NO_ERROR.getCode(),
					"Invalid input value, Y or N expected. Field : smoker_indicator");
		}

		if (request.getStaffIndicator() != null && !request.getStaffIndicator().matches("C|S")) {
			throw new StaffException(ErrorEnum.STAFF_INDICATION_ERROR.getCode(),
					"Invalid input value, C or S expected. Field : staff_indicator");
		}

		if (request.getOccupationClass() != null && !request.getOccupationClass().matches("[0-9]+")) {
			throw new DigitsException(ErrorEnum.DIGITS_ERROR.getCode(),
					"Invalid input value, digits expected. Field : occupation_class");
		}
		// Any number with digits followed by optional dot plus digits
		if (request.getLoanAmount() != null && !request.getLoanAmount().matches("^\\d+(\\.\\d+)?")) {
			throw new NumericException(ErrorEnum.NUMERIC_ERROR.getCode(),
					"Invalid input value, number expected. Field : loan_amount");
		}

		if (request.getLoanPeriod() != null && !request.getLoanPeriod().matches("[0-9]+")) {
			throw new DigitsException(ErrorEnum.DIGITS_ERROR.getCode(),
					"Invalid input value, digits expected. Field : loan_period");
		}

		if (request.getCoverPeriod() != null && !request.getCoverPeriod().matches("[0-9]+")) {
			throw new DigitsException(ErrorEnum.DIGITS_ERROR.getCode(),
					"Invalid input value, digits expected. Field : cover_period");
		}

		if (request.getCurrentBlr() != null && !request.getCurrentBlr().matches("^\\d+(\\.\\d+)?")) {
			throw new NumericException(ErrorEnum.NUMERIC_ERROR.getCode(),
					"Invalid input value, number expected. Field : current_blr");
		}

		if (request.getFinancing() != null && !request.getFinancing().matches("Y|N")) {
			throw new YesNoException(ErrorEnum.YES_NO_ERROR.getCode(),
					"Invalid input value, Y or N expected. Field : financing");
		}

		if (request.getServicing() != null && !request.getServicing().matches("[0-9]+")) {
			throw new DigitsException(ErrorEnum.DIGITS_ERROR.getCode(),
					"Invalid input value, digits expected. Field : servicing");
		}

		if (request.getStampDutyIndicator() != null && !request.getStampDutyIndicator().matches("Y|N")) {
			throw new YesNoException(ErrorEnum.YES_NO_ERROR.getCode(),
					"Invalid input value, Y or N expected. Field : stamp_duty_indicator");
		}

	}
	
	private void convertToVpmDateFormat(PrtaRequest request) throws Exception {
		String[] quotationDateArray = request.getQuotationDate().split("-");
		String quotationDate = quotationDateArray[2] + "." + quotationDateArray[1] + "." + quotationDateArray[0];
		request.setQuotationDate(quotationDate);
		
		String[] dateOfBirthArray = request.getDateOfBirth().split("-");
		String dateOfBirth = dateOfBirthArray[2] + "." + dateOfBirthArray[1] + "." + dateOfBirthArray[0];
		request.setDateOfBirth(dateOfBirth);
	}
	
	private void convertToRespDateFormat(PrtaResponse response) throws Exception {	
		if(response.getDateOfBirth()!=null && !"".equals(response.getDateOfBirth()) && 
				response.getDateOfBirth().contains(".")) {
			String[] dateOfBirthArray = response.getDateOfBirth().split("\\.");
			String dateOfBirth = dateOfBirthArray[2] + "-" + dateOfBirthArray[1] + "-" + dateOfBirthArray[0];
			response.setDateOfBirth(dateOfBirth);
		}
	}

}
