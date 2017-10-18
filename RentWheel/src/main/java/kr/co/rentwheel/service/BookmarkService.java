package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.BookmarkDto;
import kr.co.rentwheel.dto.MobilityDto;

public interface BookmarkService {
	public List<MobilityDto> getBookmark(String bm_user_id);
	public ResponseItem insertBookmark(BookmarkDto dto);
	public ResponseItem deleteBookmark(BookmarkDto dto);
	public ResponseItem updateBookmarkFlag(BookmarkDto dto);
}
