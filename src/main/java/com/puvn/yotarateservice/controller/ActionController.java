package com.puvn.yotarateservice.controller;

import com.puvn.yotarateservice.exception.BusinessException;
import com.puvn.yotarateservice.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 23.08.2019.
 */
@RestController
public class ActionController {

	private final ActionService actionService;

	@Autowired
	public ActionController(ActionService actionService) {
		this.actionService = actionService;
	}

	@RequestMapping(value = "/getMinutes/{iccid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getMinutes(@PathVariable("iccid") long iccid) throws BusinessException {
		return String.valueOf(this.actionService.getMinutesByIccid(iccid));
	}

	@RequestMapping(value = "/getGigabytes/{iccid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGigabytes(@PathVariable("iccid") long iccid) throws BusinessException {
		return String.valueOf(this.actionService.getGigsByIccid(iccid));
	}

	@RequestMapping(value = "/setActive/{iccid}/{active}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setSimCardActive(@PathVariable("iccid") long iccid,
								   @PathVariable("active") boolean active) throws BusinessException {
		return String.valueOf(this.actionService.setSimCardActive(iccid, active));
	}

	@RequestMapping(value = "/addMinutes/{iccid}/{minutes}/{minutesExpire}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String addMinutes(@PathVariable("iccid") long iccid,
							 @PathVariable("minutes") int minutes,
							 @PathVariable("minutesExpire") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
									 LocalDateTime minutesExpire) throws BusinessException {
		return String.valueOf(this.actionService.addMinutes(iccid, minutes, minutesExpire));
	}

	@RequestMapping(value = "/addGigabytes/{iccid}/{gigabytes}/{gigabytesExpire}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String addGigabytes(@PathVariable("iccid") long iccid,
							   @PathVariable("gigabytes") int gigabytes,
							   @PathVariable("gigabytesExpire") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
									   LocalDateTime gigabytesExpire) throws BusinessException {
		return String.valueOf(this.actionService.addGigabytes(iccid, gigabytes, gigabytesExpire));
	}

	@RequestMapping(value = "/spendGigabytes/{iccid}/{gigabytes}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String spendGigabytes(@PathVariable("iccid") long iccid,
								 @PathVariable("gigabytes") int gigabytes) throws BusinessException {
		return String.valueOf(this.actionService.spendGigabytes(iccid, gigabytes));
	}

	@RequestMapping(value = "/spendMinutes/{iccid}/{minutes}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String spendMinutes(@PathVariable("iccid") long iccid,
							   @PathVariable("minutes") int minutes) throws BusinessException {
		return String.valueOf(this.actionService.spendMinutes(iccid, minutes));
	}

}
