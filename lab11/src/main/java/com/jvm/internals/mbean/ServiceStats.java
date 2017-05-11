package com.jvm.internals.mbean;

import java.beans.ConstructorProperties;

public class ServiceStats {

	private int exponent;

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public ServiceStats() {
		super();
	}
	
	@ConstructorProperties({"exponent"})
	public ServiceStats(int exponent) {
		super();
		this.exponent = exponent;
	}

}
