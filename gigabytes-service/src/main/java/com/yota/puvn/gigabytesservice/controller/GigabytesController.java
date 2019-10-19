package com.yota.puvn.gigabytesservice.controller;

import com.yota.puvn.gigabytesservice.client.ServiceWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Обработка запросов, связанных с начислением/списанием/выводом гигабайт трафика
 *
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 19.10.2019.
 */
@RestController
@RequestMapping("/gigabytes-service")
public class GigabytesController {

	private final ServiceWebClient serviceWebClient;

	private final DiscoveryClient discoveryClient;

	@Autowired
	public GigabytesController(ServiceWebClient serviceWebClient, DiscoveryClient discoveryClient) {
		this.serviceWebClient = serviceWebClient;
		this.discoveryClient = discoveryClient;
	}

	@RequestMapping(value = "/getGigabytes/{iccid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGigabytes(@PathVariable("iccid") long iccid) {
		var domainInstance = this.getServiceInstance("domain-service");
		var url = String.format("%s/getGigabytes/%d", domainInstance.getUri(), iccid);
		var response = serviceWebClient.monoGet(url);
		return response.bodyToMono(String.class).block();
	}

	/*@RequestMapping(value = "/addGigabytes/{iccid}/{gigabytes}/{gigabytesExpire}",
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
	}*/


	private ServiceInstance getServiceInstance(String serviceName) {
		return this.discoveryClient.getInstances(serviceName)
				.stream()
				.findFirst()
				.orElseThrow(() -> new RuntimeException("NO DOMAIN SERVICE FOUND"));
	}

}
