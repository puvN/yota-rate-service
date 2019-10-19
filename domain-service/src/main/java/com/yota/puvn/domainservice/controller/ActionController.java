package com.yota.puvn.domainservice.controller;

import com.yota.puvn.domainservice.exception.BusinessException;
import com.yota.puvn.domainservice.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер обработки данных от других сервисов
 *
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 20.10.2019.
 */
@RestController
public class ActionController {

	private final ActionService actionService;

	@Autowired
	public ActionController(ActionService actionService) {
		this.actionService = actionService;
	}

	@RequestMapping(value = "/getGigabytes/{iccid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGigabytes(@PathVariable("iccid") long iccid) throws BusinessException {
		return String.valueOf(this.actionService.getGigsByIccid(iccid));
	}

}
