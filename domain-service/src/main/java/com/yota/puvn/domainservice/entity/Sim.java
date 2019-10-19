package com.yota.puvn.domainservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 23.08.2019.
 */
@Entity
@Table(name = "sims")
public class Sim {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SIMS")
	@SequenceGenerator(sequenceName = "SEQ_SIMS", name = "SEQ_SIMS", allocationSize = 1)
	private Long id;

	@NotNull
	@Column(name = "iccid", nullable = false)
	private Long iccid;

	@Column(name = "minutes")
	private Integer minutes;

	@Column(name = "minutes_expire")
	private LocalDateTime minutesExpire;

	@Column(name = "gigabytes")
	private Integer gigabytes;

	@Column(name = "gigabytes_expire")
	private LocalDateTime gigabytesExpire;

	@Column(name = "active")
	private Boolean active;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIccid() {
		return iccid;
	}

	public Sim() {}

	public Sim(long iccid, int minutes, LocalDateTime minutesExpire, int gigabytes,
			   LocalDateTime gigabytesExpire, boolean active) {
		this.iccid = iccid;
		this.minutes = minutes;
		this.minutesExpire = minutesExpire;
		this.gigabytes = gigabytes;
		this.gigabytesExpire = gigabytesExpire;
		this.active = active;
	}

	public void setIccid(Long ICCID) {
		this.iccid = ICCID;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getGigabytes() {
		return gigabytes;
	}

	public void setGigabytes(Integer gigabytes) {
		this.gigabytes = gigabytes;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getMinutesExpire() {
		return minutesExpire;
	}

	public void setMinutesExpire(LocalDateTime minutesExpire) {
		this.minutesExpire = minutesExpire;
	}

	public LocalDateTime getGigabytesExpire() {
		return gigabytesExpire;
	}

	public void setGigabytesExpire(LocalDateTime gigabytesExpire) {
		this.gigabytesExpire = gigabytesExpire;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Sim)) return false;
		Sim sim = (Sim) o;
		return getId().equals(sim.getId()) &&
				getIccid().equals(sim.getIccid());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getIccid());
	}
}
