package com.calculator.ws.restful.service;

import java.util.Properties;

import com.calculator.ws.restful.model.PrttRequest;
import com.calculator.ws.restful.model.Response;
import com.calculator.ws.restful.model.WebServiceFormat;

public interface PrttService {

	public WebServiceFormat getPrttFormat() throws Exception;
	
	public Response getPrttResult(PrttRequest request, Properties vpmProp) throws Exception;
	
}
