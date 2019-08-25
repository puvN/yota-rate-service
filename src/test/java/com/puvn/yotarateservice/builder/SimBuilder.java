package com.puvn.yotarateservice.builder;

import com.puvn.yotarateservice.entity.Sim;

import java.time.LocalDateTime;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 25.08.2019.
 */
public class SimBuilder {
	private long iccid;
	private int minutes;
	private LocalDateTime minutesExpire;
	private int gigabytes;
	private LocalDateTime gigabytesExpire;
	private boolean active;

	public SimBuilder iccid(long iccid) {
		this.iccid = iccid;
		return this;
	}

	public SimBuilder minutes(int minutes) {
		this.minutes = minutes;
		return this;
	}

	public SimBuilder minutesExpire(LocalDateTime minutesExpire) {
		this.minutesExpire = minutesExpire;
		return this;
	}

	public SimBuilder gigabytes(int gigabytes) {
		this.gigabytes = gigabytes;
		return this;
	}
	public SimBuilder gigabytesExpire(LocalDateTime gigabytesExpire) {
		this.gigabytesExpire = gigabytesExpire;
		return this;
	}

	public SimBuilder active(boolean active) {
		this.active = active;
		return this;
	}

	public Sim build() {
		return new Sim(iccid, minutes, minutesExpire, gigabytes, gigabytesExpire, active);
	}
}
