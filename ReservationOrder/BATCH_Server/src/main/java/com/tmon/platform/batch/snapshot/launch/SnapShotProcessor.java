package com.tmon.platform.batch.snapshot.launch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;

import com.tmon.platform.batch.snapshot.model.SnapShotDto;

/**
 * SnapShotProcessor
 * 
 * @author 구도원
 * 
 *         'snapShotUpdating'Job의 snapShotUpdateStep1에 속하는 Processor
 *
 */
public class SnapShotProcessor implements ItemProcessor<SnapShotDto, SnapShotDto> {

	private static final Logger logger = LoggerFactory.getLogger(SnapShotProcessor.class);

	@Override
	public SnapShotDto process(SnapShotDto snapShotDto) throws Exception {
		logger.info("This is snapShotProcessor");

		logger.info("SnapshotTime: " + snapShotDto.getSnapshotTime());
		logger.info("SnapshotStartTime: " + snapShotDto.getSnapshotStartTime());
		logger.info("SnapshotEndTime: " + snapShotDto.getSnapshotEndTime());
		logger.info("OrderCount: " + snapShotDto.getSnapshotOrderCount());
		logger.info("CancelCount: " + snapShotDto.getSnapshotCancelCount());

		return snapShotDto;
	}

}
