package kr.co.rentwheel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.ResponseItem;

@RestController
@RequestMapping(value="/review")
public class ReviewController {
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseItem addReview(HttpServletRequest req) {
		return null;
	}
}
