package kr.co.rentwheel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.BookmarkDto;
import kr.co.rentwheel.dto.MobilityDto;
import kr.co.rentwheel.service.BookmarkService;

@RestController
@RequestMapping(value="/bookmark")
public class BookmarkController {
	
	@Autowired
	private BookmarkService bookmarkService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public List<MobilityDto> getBookmark(@RequestParam("bm_user_id")String bm_user_id){
		return bookmarkService.getBookmark(bm_user_id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseItem insertBookmark(@RequestParam("bm_user_id")String bm_user_id, @RequestParam("bm_mobility_id")int bm_mobility_id) {
		BookmarkDto bookmarkDto = new BookmarkDto();
		bookmarkDto.setBm_user_id(bm_user_id);
		bookmarkDto.setBm_mobility_id(bm_mobility_id);
		return bookmarkService.insertBookmark(bookmarkDto);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseItem deleteBookmark(@RequestParam("bm_user_id")String bm_user_id, @RequestParam("bm_mobility_id")int bm_mobility_id) {
		BookmarkDto bookmarkDto = new BookmarkDto();
		bookmarkDto.setBm_user_id(bm_user_id);
		bookmarkDto.setBm_mobility_id(bm_mobility_id);
		return bookmarkService.deleteBookmark(bookmarkDto);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseItem updateBookmarkFlag(@RequestParam("bm_user_id")String bm_user_id, @RequestParam("bm_mobility_id")int bm_mobility_id) {
		BookmarkDto bookmarkDto = new BookmarkDto();
		bookmarkDto.setBm_user_id(bm_user_id);
		bookmarkDto.setBm_mobility_id(bm_mobility_id);
		return bookmarkService.updateBookmarkFlag(bookmarkDto);
	}
	
	
	
}
