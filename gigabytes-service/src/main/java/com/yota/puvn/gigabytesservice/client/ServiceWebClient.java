package com.yota.puvn.gigabytesservice.client;

import org.springframework.web.reactive.function.client.ClientResponse;

/**
 * Веб клиент сервиса для взаимодействия с другими сервисами
 *
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 19.10.2019.
 */
public interface ServiceWebClient {

	/**
	 * Отправка get запроса
	 *
	 * @param uri адрес сервиса
	 * @return ответ сервиса
	 */
	ClientResponse monoGet(String uri);

	/**
	 * Отправка данных пост запросом
	 *
	 * @param uri  адрес сервиса
	 * @param body данных для отправки
	 * @return ответ сервиса
	 */
	ClientResponse monoPost(String uri, Object body);

}
