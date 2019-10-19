package com.yota.puvn.domainservice.exception;

import java.util.Objects;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 24.08.2019.
 */
public class BusinessException extends Exception {

	/**
	 * Код ошибки
	 */
	private final String code;

	BusinessException(BusinessError businessError) {
		super(businessError.getMessage());
		this.code = businessError.getReasonCode();
	}

	private String getCode() {
		return code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, super.getMessage());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BusinessException)) return false;
		BusinessException that = (BusinessException) o;
		return Objects.equals(getCode(), that.getCode()) &&
				Objects.equals(super.getMessage(), that.getMessage());
	}

	@Override
	public String toString() {
		return getCode() + ", " + ": " + getMessage();
	}
}

