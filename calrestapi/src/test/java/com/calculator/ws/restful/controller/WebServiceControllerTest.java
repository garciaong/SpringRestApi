package com.calculator.ws.restful.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.calculator.ws.restful.config.SecurityConfig;
import com.calculator.ws.restful.config.SecurityWebInitializer;
import com.calculator.ws.restful.config.SpringConfig;
import com.calculator.ws.restful.config.SpringInitializer;
import com.calculator.ws.restful.config.TokenAuthenticationFilter;
import com.calculator.ws.restful.constants.PropertiesConstants;
import com.calculator.ws.restful.controller.WebServiceController;
import com.calculator.ws.restful.model.Data;
import com.calculator.ws.restful.model.LoanInterestRate;
import com.calculator.ws.restful.model.PrtaRequest;
import com.calculator.ws.restful.model.PrtaResponse;
import com.calculator.ws.restful.model.Response;
import com.calculator.ws.restful.service.PrtaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class, SpringInitializer.class, TokenAuthenticationFilter.class,
		SecurityConfig.class, SecurityWebInitializer.class })
@WebAppConfiguration
public class WebServiceControllerTest {

	private static final Logger log = Logger.getLogger(WebServiceControllerTest.class);

	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;

	@Mock
	private PrtaService mockService;

	@InjectMocks
	private WebServiceController webServiceController;
	
	@Before
	public void beforeTest() {
		log.info("Preparing unit test mock and configuration");
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(webServiceController).build();
        //Encapsulates all web application beans and make them available for testing
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void checkPrtaControllerReadiness() {
	    ServletContext servletContext = wac.getServletContext();
	     
	    Assert.assertNotNull(servletContext);
	    Assert.assertTrue(servletContext instanceof MockServletContext);
	    Assert.assertNotNull(wac.getBean("webServiceController"));
	}
	
	@Test
    public void testPrtaRequest() throws Exception {
		log.info("PRTA Testing...");
		when(mockService.getPrtaResult(getPrtaRequest(),getProperties())).thenReturn(getPrtaResponse());
		MvcResult mvcResult = mockMvc.perform(post("/ws/prta").contentType(MediaType.APPLICATION_JSON)
	    		.header("REMOTE_TOKEN", "ZHNwdG9kc2F1YXQ=")
                .content(convertObjectToJson(getPrtaRequest())))
	    		.andDo(print())
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$..data.version").value("2.15"))
	    		.andReturn();
	    Assert.assertNotEquals("application//json;charset=UTF-8", mvcResult.getResponse().getHeaderValue("Content-Type"));
	    
	}
	
	@After
	public void afterTest() {
		log.info("Unit test end");
	}
	
	private String convertObjectToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
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
	
	private Response getPrtaResponse() {
		Response response = new Response();
		Data data = new Data();
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
		response.setStatus(PropertiesConstants.OK);
		response.setData(data);
		
		return response;
	}
	
}
