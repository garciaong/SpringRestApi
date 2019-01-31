package com.calculator.ws.restful.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.ws.restful.constants.PropertiesConstants;
import com.calculator.ws.restful.enums.ErrorEnum;
import com.calculator.ws.restful.exception.CapitalAlphabetsException;
import com.calculator.ws.restful.exception.DateFormatException;
import com.calculator.ws.restful.exception.DigitsException;
import com.calculator.ws.restful.exception.FileAccessException;
import com.calculator.ws.restful.exception.GenderException;
import com.calculator.ws.restful.exception.NumericException;
import com.calculator.ws.restful.exception.PropertiesException;
import com.calculator.ws.restful.exception.StaffException;
import com.calculator.ws.restful.exception.VpmsValidationException;
import com.calculator.ws.restful.exception.YesNoException;
import com.calculator.ws.restful.model.ApplicationConfiguration;
import com.calculator.ws.restful.model.PrtaRequest;
import com.calculator.ws.restful.model.PrttRequest;
import com.calculator.ws.restful.model.Response;
import com.calculator.ws.restful.model.WebServiceFormat;
import com.calculator.ws.restful.service.PrtaService;
import com.calculator.ws.restful.service.PrttService;
import com.csc.dip.jvpms.runtime.base.VpmsLoadFailedException;

@RestController
@RequestMapping("/ws")
public class WebServiceController {

	private static final String DSAWS_PROPERTIES = "dsa.properties";
	private static Properties dsaProp = new Properties();
	private static Properties vpmProp = new Properties();
	private static InputStream dsaInputStream = null;
	private static InputStream vpmInputStream = null;
	private static final Logger log = Logger.getLogger(WebServiceController.class);
	@Autowired
	private PrtaService prtaService;
	@Autowired
	private PrttService prttService;

	public WebServiceController() {
		try {
			log.info("[Loading DSA properties...]");
			if(dsaInputStream==null) {
				dsaInputStream = WebServiceController.class.getClassLoader().getResourceAsStream(DSAWS_PROPERTIES);
				dsaProp.load(dsaInputStream);
			}
			log.info("[DSA properties loaded...]");
			log.info("[Loading VPM properties...]");
			log.info("[VPM properties file path = " + dsaProp.getProperty(PropertiesConstants.VPM_CONFIG) + "]");
			if(vpmInputStream==null) {
				vpmInputStream = new FileInputStream(dsaProp.getProperty(PropertiesConstants.VPM_CONFIG));
				vpmProp.load(vpmInputStream);
			}
			log.info("[VPM properties loaded...]");
		} catch (IOException e) {
			log.error("Input/output error detected while preparing DSA properties & VPM properties objects",e);;
		}
	}

	@GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAppInfo() {
		log.info("Retriving profile details...");
		ApplicationConfiguration app = new ApplicationConfiguration();
		app.setProfile(dsaProp.getProperty(PropertiesConstants.PROFILE));
		app.setDescription(dsaProp.getProperty(PropertiesConstants.DESCRIPTION));
		app.setVersion(dsaProp.getProperty(PropertiesConstants.VERSION));

		return new ResponseEntity<Object>(app, HttpStatus.OK);
	}

	@RequestMapping(value = "/prta", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPRTAFormat() {
		WebServiceFormat response = null;
		try {
			response = prtaService.getPrtaFormat();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/prta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPRTA(@RequestBody PrtaRequest request) {
		Response response = new Response();

		try {
			response = prtaService.getPrtaResult(request, vpmProp);
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

		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/prtt", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPRTTFormat() {
		WebServiceFormat response = null;
		try {
			response = prttService.getPrttFormat();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/prtt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPRTT(@RequestBody PrttRequest request) {
		Response response = new Response();
		try {
			response = prttService.getPrttResult(request, vpmProp);
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
		} catch (DigitsException e) {
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
		
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
