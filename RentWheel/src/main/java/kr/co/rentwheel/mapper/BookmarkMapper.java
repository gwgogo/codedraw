package kr.co.rentwheel.mapper;

import java.util.List;

import kr.co.rentwheel.dto.BookmarkDto;
import kr.co.rentwheel.dto.MobilityDto;

public interface BookmarkMapper {
	public void insertBookmark(BookmarkDto dto);
	public int deleteBookmark(BookmarkDto dto);
	public int updateBookmarkFlag(BookmarkDto dto);
	public List<MobilityDto> getBookmark(String bm_user_id);
}
