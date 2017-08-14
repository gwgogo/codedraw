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
 * ReservationOrderStatusTo1Launcher
 * 
 * @author 구도원
 * 
 *         'reservationOrderStatusTo1Updating' Job을 실행시키는 Launcher.
 *         JobParameters는 본 클래스의 'run()'메소드에서 설정한 뒤, 주입한다.
 * 
 * @jobParameter
 * @name currTime
 * @value currTime
 * @type long
 * @note Server의 System.currentTimeMillis()의 반환 값
 * 
 * @jobParameter
 * @name searchFinishDate
 * @value ReservationOrderStatusUpdateTime.getSearchFinishDate(currTime)
 * @type String
 * @note Reader에서 사용되는 Query문의 변수, RESERVATION_ORDER의 전체 목록에서
 *       searchFinishDate까지의 statusID가 0인 데이터를 찾는다.
 *
 */
public class ReservationOrderStatusTo1Launcher {

	private static final Logger logger = LoggerFactory.getLogger(ReservationOrderStatusTo1Launcher.class);

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
		builder.addString("searchFinishDate",
				ReservationOrderStatusUpdateTime.getSearchFinishDate(currTime, "yyyy-MM-dd HH:mm:ss"));

		JobParameters jobParameters = builder.toJobParameters();

		try {
			logger.info("reservationOrderStatusTo1Updating run!");
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
