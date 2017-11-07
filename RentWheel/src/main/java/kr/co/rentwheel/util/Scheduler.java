package kr.co.rentwheel.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.service.ReservationService;

@Component
public class Scheduler {
	
	private final static int USE_COMPLETION_STATE = 1;
	
	@Autowired
	private ReservationService reservationService;
	
	/* �� 30 �и��� �����췯 ���� - ����ð��� ���� ���� "����Ϸ�"���¿��� "�̿�Ϸ�" ���·� ���� */
	@Scheduled(cron = "0 0/30 * * * *")
	public void updateReservationCompletionToUseCompletion() throws CustomException {
		System.out.println("change");
		List<Integer> endReservationList = reservationService.getEndReservation();
		
		for(int i = 0; i < endReservationList.size(); i++) {
			int rs_id = endReservationList.get(i).intValue();
			reservationService.updateReservationState(rs_id, USE_COMPLETION_STATE);
		}
	}
}
