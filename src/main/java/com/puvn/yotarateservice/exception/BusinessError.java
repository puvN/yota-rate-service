package com.puvn.yotarateservice.exception;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 24.08.2019.
 */
public enum BusinessError {
	B001("Could not use traffic, ttl expired",
			"EXPIRED_TTL"),

	B002("Could not find active sim-card by ICCID",
			"ACTIVE_SIM_NOT_FOUND"),

	B003("No traffic on sim-card",
			"NO_TRAFFIC"),

	B004("TTL is before existing",
			"TTL_IS_BEFORE_EXISTING"),

	B005("Could not find registered sim-card",
				 "SIM_NOT_FOUND");

	private final String message;

	private final String reasonCode;

	BusinessError(String message, String reasonCode) {
		this.message = message;
		this.reasonCode = reasonCode;
	}

	public BusinessException constructException() {
		return new BusinessException(this);
	}

	public String getMessage() {
		return message;
	}

	public String getReasonCode() {
		return reasonCode;
	}
}
