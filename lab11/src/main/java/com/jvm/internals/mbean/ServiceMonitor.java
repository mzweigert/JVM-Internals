package com.jvm.internals.mbean;

import com.jvm.internals.service.SomeService;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;


public class ServiceMonitor extends NotificationBroadcasterSupport implements
		ServiceMonitorMBean {

	private SomeService ss;
	private int sequenceNumber = 0;

	public ServiceMonitor(SomeService ss) {
		this.ss = ss;
	}

	@Override
	public void setExponent(int exponent) {
		ss.setExponent(exponent);
		String message = "";
		if (exponent > 1 && exponent < 5) {
			message = "Niska";
 		} else if (exponent > 6 && exponent < 10) {
			message = "Średnia";
		} else {
			message = "Wysoka";
		}
		Notification notification = new AttributeChangeNotification(this,
				sequenceNumber++, System.currentTimeMillis(), message + " wartość potęgi"
				, "exponent", "int", ss.getExponent(),
				exponent);
		notification.setUserData("zmiana exponent  na " + exponent);
		sendNotification(notification);

	}

	@Override
	public int getExponent() {
		return ss.getExponent();
	}

	@Override
	public void clear() {
		ss.setExponent(2);
	}

}
