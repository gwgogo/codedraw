package com.tmon.platform.batch.snapshot.launch;

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
 * SnapShotLauncher
 * 
 * @author 구도원
 * 
 *         'snapShotUpdating' Job을 실행시키는 Launcher. JobParameters는 본 클래스의
 *         'run()'메소드에서 설정한 뒤, 주입한다.
 * 
 * @jobParameter
 * @name currTime
 * @value currTime
 * @type long
 * @note Server의 System.currentTimeMillis()의 반환 값
 * 
 * @jobParameter
 * @name snapshotTime
 * @value SnapShotTime.getSnapshotTime(currTime)
 * @type String
 * @note 'snapShotUpdating' Job이 실행된 시간을 의미
 * 
 * @jobParameter
 * @name snapshotStartTime
 * @value SnapShotTime.getSnapshotStartTime(currTime)
 * @type String
 * @note 'snapShotUpdating'의 검색 대상의 시작 시간
 * 
 * @jobParameter
 * @name snapshotEndTime
 * @value SnapShotTime.getSnapshotEndTime(surrTime)
 * @type String
 * @note 'snapShotUpdating'의 검색 대상의 끝 시간
 *
 */
public class SnapShotLauncher {

	private static final Logger logger = LoggerFactory.getLogger(SnapShotLauncher.class);

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
		builder.addString("snapshotTime", SnapShotTime.getSnapshotTime(currTime));
		builder.addString("snapshotStartTime", SnapShotTime.getSnapshotStartTime(currTime));
		builder.addString("snapshotEndTime", SnapShotTime.getSnapshotEndTime(currTime));

		JobParameters jobParameters = builder.toJobParameters();

		try {
			logger.info("snapShotUpdating run!");
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
