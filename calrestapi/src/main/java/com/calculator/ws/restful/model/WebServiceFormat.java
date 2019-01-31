package com.calculator.ws.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WebServiceFormat {

	@JsonProperty(value = "description")
    private String description;
	@JsonProperty(value = "prta-request-format")
    private PrtaRequest prtaRequest;
	@JsonProperty(value = "prtt-request-format")
    private PrttRequest prttRequest;
	@JsonProperty(value = "prta-response-format")
    private Response prtaResponse;
	@JsonProperty(value = "prtt-response-format")
    private Response prttResponse;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PrtaRequest getPrtaRequest() {
		return prtaRequest;
	}
	public void setPrtaRequest(PrtaRequest prtaRequest) {
		this.prtaRequest = prtaRequest;
	}
	public PrttRequest getPrttRequest() {
		return prttRequest;
	}
	public void setPrttRequest(PrttRequest prttRequest) {
		this.prttRequest = prttRequest;
	}
	public Response getPrtaResponse() {
		return prtaResponse;
	}
	public void setPrtaResponse(Response prtaResponse) {
		this.prtaResponse = prtaResponse;
	}
	public Response getPrttResponse() {
		return prttResponse;
	}
	public void setPrttResponse(Response prttResponse) {
		this.prttResponse = prttResponse;
	}
	
}
