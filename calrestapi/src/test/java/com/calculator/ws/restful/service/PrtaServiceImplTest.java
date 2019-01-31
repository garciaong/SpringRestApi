package com.calculator.ws.restful.service;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.calculator.ws.restful.config.SecurityConfig;
import com.calculator.ws.restful.config.SecurityWebInitializer;
import com.calculator.ws.restful.config.SpringConfig;
import com.calculator.ws.restful.config.SpringInitializer;
import com.calculator.ws.restful.config.TokenAuthenticationFilter;
import com.calculator.ws.restful.constants.PropertiesConstants;
import com.calculator.ws.restful.enums.ErrorEnum;
import com.calculator.ws.restful.exception.CapitalAlphabetsException;
import com.calculator.ws.restful.exception.DateFormatException;
import com.calculator.ws.restful.exception.FileAccessException;
import com.calculator.ws.restful.exception.GenderException;
import com.calculator.ws.restful.exception.NumericException;
import com.calculator.ws.restful.exception.PropertiesException;
import com.calculator.ws.restful.exception.StaffException;
import com.calculator.ws.restful.exception.VpmsValidationException;
import com.calculator.ws.restful.exception.YesNoException;
import com.calculator.ws.restful.model.PrtaRequest;
import com.calculator.ws.restful.model.Response;
import com.calculator.ws.restful.service.PrtaService;
import com.csc.dip.jvpms.runtime.base.VpmsLoadFailedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class,SpringInitializer.class,
		TokenAuthenticationFilter.class,SecurityConfig.class,SecurityWebInitializer.class})
@WebAppConfiguration
public class PrtaServiceImplTest {

	private static final Logger log = Logger.getLogger(PrtaServiceImplTest.class);
	
	@Autowired
    private PrtaService prtaService;
	
	@Before
	public void beforeTest() {
		log.info("Start Unit Testing");
	}
	
    @Test
	public void testPrtaErrorResult() throws Exception{
    	PrtaRequest request = getPrtaRequest();
    	request.setLoanAmount("1000000");
    	Response response = new Response();
    	try {
			response = prtaService.getPrtaResult(request, getProperties());
		} catch (PropertiesException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		} catch (FileAccessException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		} catch (DateFormatException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		} catch (CapitalAlphabetsException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		} catch (GenderException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		} catch (YesNoException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		} catch (StaffException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		} catch (NumericException e) {
			response.setCode(e.getErrorCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getErrorMessage());
		}catch (VpmsValidationException e) {
			response.setCode(ErrorEnum.VPMS_VALIDATION_ERROR.getCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(e.getMessage());
		} catch (NullPointerException e) {
			response.setCode(ErrorEnum.INVALID_OBJECT_REFERENCE.getCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(ErrorEnum.INVALID_OBJECT_REFERENCE.getMessage());
		} catch (VpmsLoadFailedException e) {
			response.setCode(ErrorEnum.VPM_FILE_LOAD_ERROR.getCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(ErrorEnum.VPM_FILE_LOAD_ERROR.getMessage());
		} catch (Exception e) {
			response.setCode(ErrorEnum.UNHANDLED_ERROR.getCode());
			response.setStatus(PropertiesConstants.ERROR);
			response.setMessage(ErrorEnum.UNHANDLED_ERROR.getMessage());
		}
    	Assert.assertEquals("ERROR",response.getStatus());
    	Assert.assertEquals("VPMS A_LoanAmount: Min Loan Amt is RM 2,000 & Max Loan Amt is RM 450,000 (per Life Insured).",response.getMessage());
	}
    
	@Test
	public void testPrtaSuccessResult() throws Exception{
		Response reponse = prtaService.getPrtaResult(getPrtaRequest(),getProperties());
		Assert.assertEquals("OK",reponse.getStatus());
		Assert.assertEquals("2.15",reponse.getData().getPrtaResponse().getVersion());
	}
	
	@After
	public void afterTest() {
		log.info("End Unit Testing");
	}
	
	private Properties getProperties() {
		Properties prop = new Properties();
		prop.setProperty("vpm.file.path.prta", "D:/workspace/DeTariff/vpm/PRTA.vpm");
		prop.setProperty("vpm.file.path.prtt", "D:/workspace/DeTariff/vpm/PRTT.vpm");
		
		return prop;
	}
	
	private PrtaRequest getPrtaRequest() {
		PrtaRequest request = new PrtaRequest();
		request.setQuotationDate("2018-10-01");
		request.setLanguage("E");
		request.setDateOfBirth("1990-10-01");
		request.setGender("M");
		request.setSmokerIndicator("N");
		request.setStaffIndicator("C");
		request.setOccupationClass("1");
		request.setLoanAmount("150000");
		request.setLoanPeriod("20");
		request.setCoverPeriod("15");
		request.setCurrentBlr("35.0");
		request.setFinancing("N");
		request.setServicing("5");
		request.setStampDutyIndicator("N");
		
		return request;
	}
}
