package com.puvn.yotarateservice.service;

import com.puvn.yotarateservice.exception.BusinessException;

import java.time.LocalDateTime;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 23.08.2019.
 */
public interface ActionService {

	/**
	 * Начисляет пакет минут по ICCID сим-карты
	 *
	 * @param iccid         iccid сим-карты
	 * @param minutes       количество минут
	 * @param minutesExpire дата и время, до которых предоставлены минуты
	 * @return количество минут на сим-карте
	 * @throws BusinessException бизнес ошибка
	 */
	int addMinutes(long iccid, int minutes, LocalDateTime minutesExpire) throws BusinessException;

	/**
	 * Начисляет пакет минут по ICCID сим-карты
	 *
	 * @param iccid           iccid сим-карты
	 * @param gigabytes       количество гигабайт
	 * @param gigabytesExpire дата и время, до которых предоставлены гигабайты
	 * @return количество гигабайт на сим-карте
	 * @throws BusinessException бизнес ошибка
	 */
	int addGigabytes(long iccid, int gigabytes, LocalDateTime gigabytesExpire) throws BusinessException;

	/**
	 * Расходует минуты
	 *
	 * @param iccid   iccid сим-карты
	 * @param minutes количество минут
	 * @return количество оставшихся минут
	 * @throws BusinessException бизнес ошибка
	 */
	int spendMinutes(long iccid, int minutes) throws BusinessException;

	/**
	 * Расходует гигабайты
	 *
	 * @param iccid     iccid сим-карты
	 * @param gigaBytes количество гигабайт
	 * @return количество оставшихся гигабайт
	 * @throws BusinessException бизнес ошибка
	 */
	int spendGigabytes(long iccid, int gigaBytes) throws BusinessException;

	/**
	 * Получение оставшихся минут по ICCID сим-карты
	 *
	 * @param iccid iccid сим-карты
	 * @return оставшиеся минуты
	 * @throws BusinessException бизнес ошибка
	 */
	int getMinutesByIccid(long iccid) throws BusinessException;

	/**
	 * Получение оставшихся гигабайт по iccid сим-карты
	 *
	 * @param iccid iccid сим-карты
	 * @return оставшиеся гигабайты
	 * @throws BusinessException бизнес ошибка
	 */
	int getGigsByIccid(long iccid) throws BusinessException;

	/**
	 * Активирует/дезактивирует сим-карту
	 *
	 * @param iccid  iccid сим-карты
	 * @param active флаг активации
	 * @throws BusinessException бизнес ошибка
	 */
	boolean setSimCardActive(long iccid, boolean active) throws BusinessException;
}
