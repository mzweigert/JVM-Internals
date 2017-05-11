package com.jvm.internals.mbean;


import com.jvm.internals.service.SomeService;

public class ServiceStatsMonitor implements IServiceStatsMonitor {
	
	private SomeService ss;
	
	public ServiceStatsMonitor(SomeService ss) {
		this.ss = ss;
	}

	@Override
	public ServiceStats getServiceStats() {
		return new ServiceStats(ss.getExponent());
	}


}
