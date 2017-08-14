package com.tmon.platform.batch.reservation.launch;

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

/**
 * ReservationOrderStatusTo2Launcher
 * 
 * @author 구도원
 * 
 *         'reservationOrderStatusTo2Updating' Job을 실행시키는 Launcher
 *         JobParameters는 본 클래스의 'run()' 메소드에서 설정한 뒤, 주입힌다.
 * 
 * @jobParameter
 * @name currTime
 * @value currTime
 * @type long
 * @note Server의 System.currentTimeMillis()의 반환 값
 * 
 * @jobParameter
 * @name deliveryDate
 * @value ReservationOrderStatusUpdateTime.getSearchDeliveryDate(currTime)
 * @type String
 * @note Reader에서 사용되는 Query문의 변수
 * 
 * @jobParameter
 * @name searchFinishTime
 * @value ReservationOrderStatusUpdateTime.getSearchFinishTime(currTime)
 * @type String
 * @note Reader에서 사용되는 Query문의 변수
 *
 */
public class ReservationOrderStatusTo2Launcher {

	private static final Logger logger = LoggerFactory.getLogger(ReservationOrderStatusTo2Launcher.class);

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

	public void run() {
		long currTime = System.currentTimeMillis();
		JobParametersBuilder builder = new JobParametersBuilder();

		builder.addLong("currTime", currTime);
		builder.addString("deliveryDate",
				ReservationOrderStatusUpdateTime.getSearchDeliveryDate(currTime, "yyyy-MM-dd"));
		builder.addString("searchFinishTime",
				ReservationOrderStatusUpdateTime.getSearchFinishDate(currTime, "HH:mm:ss"));

		JobParameters jobParameters = builder.toJobParameters();

		try {
			logger.info("reservationOrderStatusTo2Updating run!");
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

}
