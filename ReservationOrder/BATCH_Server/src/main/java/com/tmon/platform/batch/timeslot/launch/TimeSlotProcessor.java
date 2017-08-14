package com.tmon.platform.batch.timeslot.launch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import com.tmon.platform.batch.timeslot.model.TimeSlotDto;
import com.tmon.platform.batch.timeslot.model.TimeSlotSettingDto;

/**
 * TimeSlotProcessor
 * 
 * @author 구도원
 *
 */
@Scope(value = "step")
public class TimeSlotProcessor implements ItemProcessor<TimeSlotSettingDto, TimeSlotDto> {

	public static final Logger logger = LoggerFactory.getLogger(TimeSlotProcessor.class);

	@Value("#{jobParameters['inputDayCalendar']}")
	private long inputDayCalendar;

	@Override
	public TimeSlotDto process(TimeSlotSettingDto timeSlotSettingDto) throws Exception {

		/*
		 * 현재 기준(Job 실행일)으로부터 X일 후의 날짜를 입력한다. JobParameters의 inputDayCalendar는 X일 후의 날짜로
		 * 계산된 데이터 이다.
		 */
		Date createDate = new Date(inputDayCalendar);

		/*
		 * 'timeSlotSettingDto'의 cutoff로부터 hour와 minute를 구한다. 타임슬롯의 날짜에서 하루를 빼고, 해당
		 * 타임슬롯의 startTime으로 세팅한다.
		 */
		Calendar cutoffCalendar = new GregorianCalendar();
		cutoffCalendar.setTime(timeSlotSettingDto.getCutoff());
		int hour = cutoffCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = cutoffCalendar.get(Calendar.MINUTE);

		cutoffCalendar.setTime(createDate);
		cutoffCalendar.add(Calendar.DATE, -1);
		cutoffCalendar.set(Calendar.HOUR_OF_DAY, hour);
		cutoffCalendar.set(Calendar.MINUTE, minute);
		cutoffCalendar.set(Calendar.SECOND, 0);
		cutoffCalendar.set(Calendar.MILLISECOND, 0);

		/*
		 * 
		 * 새롭게 타임슬롯을 생성하기 위한 객체 'timeSlotDto'를 생성한다.
		 * 
		 * 인자로 받은 timeSlotSettingDto로 부터 얻은 시간 정보들과 이를 가공한 정보를 'timeSlotDto'에 저장한다.
		 * 
		 */
		TimeSlotDto timeSlotDto = new TimeSlotDto();
		timeSlotDto.setStartTime(timeSlotSettingDto.getStartTime());
		timeSlotDto.setEndTime(timeSlotSettingDto.getEndTime());
		timeSlotDto.setDeliveryDate(createDate);
		timeSlotDto.setCutoff(cutoffCalendar.getTime());

		// 가공된 배송 날짜와 그날의 타임슬롯을 출력한다.
		logger.info("=====================");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("배송 날짜: " + dateFormat.format(timeSlotDto.getDeliveryDate()));

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		logger.info("타임슬롯 시작시간: " + timeFormat.format(timeSlotDto.getStartTime()));
		logger.info("타임슬롯 끝시간: " + timeFormat.format(timeSlotDto.getEndTime()));

		SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("타임슬롯 컷오프 시간: " + fullFormat.format(timeSlotDto.getCutoff()));

		return timeSlotDto;
	}

}
