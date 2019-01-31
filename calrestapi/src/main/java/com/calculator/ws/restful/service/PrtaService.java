package com.calculator.ws.restful.service;

import java.util.Properties;

import com.calculator.ws.restful.model.PrtaRequest;
import com.calculator.ws.restful.model.Response;
import com.calculator.ws.restful.model.WebServiceFormat;

public interface PrtaService {

	public WebServiceFormat getPrtaFormat() throws Exception;
	
	public Response getPrtaResult(PrtaRequest request, Properties vpmProp) throws Exception;

}
