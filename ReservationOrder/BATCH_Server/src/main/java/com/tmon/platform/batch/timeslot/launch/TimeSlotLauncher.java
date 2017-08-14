package com.tmon.platform.batch.timeslot.launch;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmon.platform.batch.ServerSettingUtils;
import com.tmon.platform.batch.holiday.util.ConfirmHoliday;

/**
 * TimeSlotLauncher
 * 
 * @author 구도원
 *
 */
public class TimeSlotLauncher {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotLauncher.class);

	private Job job;

	private JobLauncher jobLauncher;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	@Autowired
	ConfirmHoliday confirmHoliday;

	public void run() {
		/*
		 * 오늘 날짜(배치가 실행되는 시점)를 구하고, 그로부터 7일후의 날짜를 계산한다.
		 */
		Calendar inputDayCalendar = new GregorianCalendar();
		inputDayCalendar.setTime(new Date(System.currentTimeMillis()));
		inputDayCalendar.add(Calendar.DATE, ServerSettingUtils.INPUT_DAYS_ADD);

		/*
		 * 오늘 부터 'inputDays'일 뒤의 날짜 계산이 날짜는 새롭게 생성되는 예약배송 일자다.
		 * 
		 * 생성되는 타임슬롯의 일자가 토/일(주말) OR 지정된 공휴일(DB조회)일 경우 타임슬롯을 생성하지 않는 정책이다.
		 */
		if (isValidateHoliday(inputDayCalendar)) {
			// 주말이거나 공휴일이면 'timeSlotUpdating' Job을 실행하지 않는다.
			return;
		}

		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addLong("inputDayCalendar", inputDayCalendar.getTimeInMillis());
		JobParameters jobParameters = builder.toJobParameters();

		try {
			logger.info("timeSlotUpdating run!");
			JobExecution execution = jobLauncher.run(job, jobParameters);
			logger.info("Exit Status : " + execution.getStatus());
		} catch (JobExecutionAlreadyRunningException e) {
			logger.error(e.toString());
		} catch (JobRestartException e) {
			logger.error(e.toString());
		} catch (JobInstanceAlreadyCompleteException e) {
			logger.error(e.toString());
		} catch (JobParametersInvalidException e) {
			logger.error(e.toString());
		}
	}

	public boolean isValidateHoliday(Calendar inputDayCalendar) {
		// 만약 생성하려고 하는 예약배송일자가 주말(토, 일)이라면
		// timeSlotLauncher가 실행되지 않는다.
		int day = inputDayCalendar.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			logger.info("Saturday OR Sunday After " + ServerSettingUtils.INPUT_DAYS_ADD + "days");
			return true;
		}

		// 만약 생성하려고 하는 예약배송일자가 공휴일 이라면
		// timeSlotLauncher가 실행되지 않는다.
		if (confirmHoliday.confirmHoliday(inputDayCalendar)) {
			logger.info("That day is Holiday");
			return true;
		}
		return false;
	}

}
