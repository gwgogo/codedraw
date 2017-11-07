package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.BookmarkDao;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.BookmarkDto;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.service.BookmarkService;

@Service
public class BookmarkServiceImpl implements BookmarkService{
	
	@Autowired
	private BookmarkDao bookmarkDao; 
	
	public List<MobilityDto> getBookmark(String bm_user_id){
		return bookmarkDao.getBookmark(bm_user_id);
	}
	
	public ResponseItem insertBookmark(BookmarkDto dto) {
		ResponseItem responseItem = new ResponseItem();
		try {
			bookmarkDao.insertBookmark(dto);
		}catch(Exception e) {
			responseItem.setMsg("Fail : Bookmark insert query Error");
			responseItem.setResult("420");
		}
		return responseItem;
	}
	
	public ResponseItem deleteBookmark(BookmarkDto dto) {
		ResponseItem responseItem = new ResponseItem();
		if(bookmarkDao.deleteBookmark(dto) == 0) {
			responseItem.setMsg("Fail : Bookmark Not Deleted");
			responseItem.setResult("421");
		}
		return responseItem;
	}
	
	public ResponseItem updateBookmarkFlag(BookmarkDto dto) {
		ResponseItem responseItem = new ResponseItem();
		if(bookmarkDao.updateBookmarkFlag(dto) == 0) {
			responseItem.setMsg("Fail : Bookmark Flag Not Updated");
			responseItem.setResult("422");
		}
		return responseItem;
	}
}
