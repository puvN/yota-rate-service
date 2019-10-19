package com.yota.puvn.gigabytesservice.client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;


/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 19.10.2019.
 */
@Service
public class ServiceWebClientImpl implements ServiceWebClient, InitializingBean {

	private WebClient webClient;

	@Override
	public void afterPropertiesSet() {
		this.webClient = WebClient
				.builder()
			    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	@Override
	public ClientResponse monoGet(String uri) {
		var responseMono = this.webClient
				.get()
				.uri(uri)
				.exchange();
		return Objects.requireNonNull(responseMono.block());
	}

	@Override
	public ClientResponse monoPost(String uri, Object body) {
		var responseMono = this.webClient
				.post()
				.uri(uri)
				.body(BodyInserters.fromObject(body))
				.exchange();
		return Objects.requireNonNull(responseMono.block());
	}

}
