package com.tmon.platform.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tmon.platform.batch.reservation.launch.ReservationOrderStatusTo1Launcher;
import com.tmon.platform.batch.reservation.launch.ReservationOrderStatusTo2Launcher;
import com.tmon.platform.batch.snapshot.launch.SnapShotLauncher;
import com.tmon.platform.batch.timeslot.launch.TimeSlotLauncher;

/**
 * BatchScheduled
 * 
 * @author 구도원
 * 
 *         Time Scheduled ==> "초 분 시 일 월 요일"
 * 
 *         1. Batch가 실행되는 여러 세팅들(static final 변수)은 'ServerSettingUtils'클래스에서
 *         설정한다.
 * 
 *         2. 각 Job들의 *Launcher.java 소스코드에서도 Job실행에 대한 설정을 할 수 있다.
 *
 */

@Component
public class BatchScheduled {

	private static final Logger logger = LoggerFactory.getLogger(BatchScheduled.class);

	@Autowired
	TimeSlotLauncher timeSlotLauncher;

	@Autowired
	SnapShotLauncher snapShotLauncher;

	@Autowired
	ReservationOrderStatusTo1Launcher reservationOrderStatusTo1Launcher;

	@Autowired
	ReservationOrderStatusTo2Launcher reservationOrderStatusTo2Launcher;

	/**
	 * timeSlotUpdating Job 실행 메소드
	 * 
	 * 메소드 실행 스케쥴링은 "00 00 09 * * *" 이다. (매일 오후 6시) 서버시간이 현재시간보다 9시간 느리다.
	 * 
	 * @author 구도원
	 */
	@Scheduled(cron = "00 00 09 * * *")
	public void timeSlot() {
		timeSlotLauncher.run();
	}

	/**
	 * snapShotUpdating Job 실행 메소드
	 * 
	 * 메소드 실행 스케줄링은 "00 00,10,20,30,40,50 * * * *" 정각을 기준으로 10분 간격으로 실행
	 * 
	 * @author 구도원
	 */
	@Scheduled(cron = "00 00,10,20,30,40,50 * * * *")
	public void snapShot() {
		snapShotLauncher.run();
	}

	/**
	 * reservationOrderStatusTo1Updating Job 실행 메소드
	 * 
	 * 메소드 실행 스케쥴링은 "05 * * * * *" 정각을 기준으로 매시 3분 실행
	 */
	@Scheduled(cron = "05 * * * * *")
	public void reservationOrderTo1Status() {
		reservationOrderStatusTo1Launcher.run();
	}

	/**
	 * reservationOrderStatusTo2Updating Job 실행 메소드
	 * 
	 * 메소드 실행 스케쥴링은 "03 * * * * *" 정각을 기준으로 매시 3분 실행
	 */
	@Scheduled(cron = "08 * * * * *")
	public void reservationOrderTo2Status() {
		reservationOrderStatusTo2Launcher.run();
	}

}
