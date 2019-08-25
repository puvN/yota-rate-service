package com.puvn.yotarateservice.service;

import com.puvn.yotarateservice.entity.Sim;
import com.puvn.yotarateservice.exception.BusinessError;
import com.puvn.yotarateservice.exception.BusinessException;
import com.puvn.yotarateservice.repository.SimsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 23.08.2019.
 */
@Service
@Transactional(rollbackFor = Throwable.class, isolation = Isolation.REPEATABLE_READ)
public class ActionServiceImpl implements ActionService {

	private final SimsRepository simsRepository;

	@Autowired
	public ActionServiceImpl(SimsRepository simsRepository) {
		this.simsRepository = simsRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addMinutes(long iccid, int minutes, LocalDateTime minutesExpire) throws BusinessException {
		//1. Находим необходимую активную сим-карту
		var simCard = this.findActiveSimCard(iccid);
		//2. Устанавливаем дату, до которой будут действовать минуты
		if (minutesExpire != null) {
			// Если указана дата окончания минут, и она раньше существующей даты, бросаем исключение
			if (minutesExpire.isBefore(simCard.getMinutesExpire())) {
				throw BusinessError.B004.constructException();
			} else {
				//Иначе обновляем их время жизни
				simCard.setMinutesExpire(minutesExpire);
			}
		}
		//3. Начисляем минуты
		simCard.setMinutes(simCard.getMinutes() + minutes);
		//4. Сохраняем в бд
		this.simsRepository.save(simCard);
		return simCard.getMinutes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addGigabytes(long iccid, int gigabytes, LocalDateTime gigabytesExpire) throws BusinessException {
		//1. Находим необходимую активную сим-карту
		var simCard = this.findActiveSimCard(iccid);
		//2. Устанавливаем дату, до которой будут действовать гигабайты
		if (gigabytesExpire != null) {
			// Если указана дата окончания гигабайт, и она раньше существующей даты, бросаем исключение
			if (gigabytesExpire.isBefore(simCard.getGigabytesExpire())) {
				throw BusinessError.B004.constructException();
			} else {
				//Иначе обновляем их время жизни
				simCard.setGigabytesExpire(gigabytesExpire);
			}
		}
		//3. Начисляем минуты
		simCard.setGigabytes(simCard.getGigabytes() + gigabytes);
		//4. Сохраняем в бд
		this.simsRepository.save(simCard);
		return simCard.getGigabytes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int spendMinutes(long iccid, int minutes) throws BusinessException {
		//1. Находим необходимую активную сим-карту
		var simCard = this.findActiveSimCard(iccid);
		//2. Если минуты на сим-карте просрочены - бросаем исключение
		if (LocalDateTime.now().isAfter(simCard.getMinutesExpire())) {
			throw BusinessError.B001.constructException();
		}
		//3. Если минут нет - бросаем исключение
		if (simCard.getMinutes() == null || simCard.getMinutes() <= 0) {
			throw BusinessError.B003.constructException();
		}
		//4. Списываем минуты
		simCard.setMinutes(simCard.getMinutes() - minutes);
		//5. Сохраняем состояние
		this.simsRepository.save(simCard);
		return simCard.getMinutes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int spendGigabytes(long iccid, int gigaBytes) throws BusinessException {
		//1. Находим необходимую активную сим-карту
		var simCard = this.findActiveSimCard(iccid);
		//2. Если гигабайты на сим-карте просрочены - бросаем исключение
		if (LocalDateTime.now().isAfter(simCard.getGigabytesExpire())) {
			throw BusinessError.B001.constructException();
		}
		//3. Если гигабайт нет - бросаем исключение
		if (simCard.getGigabytes() == null || simCard.getGigabytes() <= 0) {
			throw BusinessError.B003.constructException();
		}
		//4. Списываем минуты
		simCard.setGigabytes(simCard.getGigabytes() - gigaBytes);
		//5. Сохраняем состояние
		this.simsRepository.save(simCard);
		return simCard.getGigabytes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinutesByIccid(long iccid) throws BusinessException {
		//1. Находим необходимую активную сим-карту
		var simCard = this.findActiveSimCard(iccid);
		//2. Возвращаем минуты
		return simCard.getMinutes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getGigsByIccid(long iccid) throws BusinessException {
		//1. Находим необходимую активную сим-карту
		var simCard = this.findActiveSimCard(iccid);
		//2. Возвращаем гигабайты
		return simCard.getGigabytes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean setSimCardActive(long iccid, boolean active) throws BusinessException {
		//1. Находим необходимую сим-карту
		var simCard = this.simsRepository.findByIccid(iccid).orElseThrow(BusinessError.B005::constructException);
		//2. Устанавливаем флаг активности и сохраняем в бд
		simCard.setActive(active);
		this.simsRepository.save(simCard);
		return simCard.getActive();
	}

	/**
	 * Метод находит активную сим-карту по ее ICCID
	 *
	 * @param iccid iccid сим-карты
	 * @return найденная сим-карта
	 * @throws BusinessException ошибка
	 */
	private Sim findActiveSimCard(long iccid) throws BusinessException {
		return this.simsRepository.findByIccidAndActive(iccid, true)
				.orElseThrow(BusinessError.B002::constructException);
	}
}
