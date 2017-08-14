package com.tmon.platform.batch.reservation.launch;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import com.tmon.platform.batch.reservation.model.ReservationOrderDto;

/**
 * ReservationOrderStatusTo2Processor
 * 
 * @author 구도원
 * 
 *         'reservationOrderStatusTo2Updating' Job의
 *         reservationOrderStatusTo2UpdateStep1에 속하는 Processor
 * 
 */
@Scope(value = "step")
public class ReservationOrderStatusTo2Processor implements ItemProcessor<ReservationOrderDto, ReservationOrderDto> {

	private static final Logger logger = LoggerFactory.getLogger(ReservationOrderStatusTo2Processor.class);

	@Value("#{jobParameters['currTime']}")
	private long currTime;

	private static final int RESERVATION_ORDER_STATUS_ID_GONE = 2;

	@Override
	public ReservationOrderDto process(ReservationOrderDto reservationOrderDto) throws Exception {
		logger.info("This is reservationOrderStatusTo2Processor");

		logger.info("[To2]reservationID: " + reservationOrderDto.getReservationID());
		logger.info("[To2]timeslotID: " + reservationOrderDto.getTimeslotID());
		logger.info("[To2]status: " + reservationOrderDto.getStatusID());

		reservationOrderDto.setStatusID(RESERVATION_ORDER_STATUS_ID_GONE);

		logger.info("[To2]" + reservationOrderDto.getReservationID() + "번 주문 상태 '배송완료'로 변경");
		logger.info("[To2]변경 시간(서버시간 기준): " + new Date(currTime).toString());
		logger.info("========================================");

		return reservationOrderDto;
	}

}
