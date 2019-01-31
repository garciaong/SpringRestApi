package com.calculator.ws.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Data {

	@JsonUnwrapped
	private PrtaResponse prtaResponse;
	@JsonUnwrapped
	private PrttResponse prttResponse;
	
	public PrtaResponse getPrtaResponse() {
		return prtaResponse;
	}
	public void setPrtaResponse(PrtaResponse prtaResponse) {
		this.prtaResponse = prtaResponse;
	}
	public PrttResponse getPrttResponse() {
		return prttResponse;
	}
	public void setPrttResponse(PrttResponse prttResponse) {
		this.prttResponse = prttResponse;
	}
	
}
