package kr.co.rentwheel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.dto.ContentDto;
import kr.co.rentwheel.service.ContentService;


/*
 * ct_category
 * 1 : ���� �̺�Ʈ ���
 * 2 : ���� ��õ ������ ���
 * 3 : ��õ �ڽ�
 * 
 */
@RestController
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	/* ī�װ���ȣ�� ���� �̺�Ʈ���, ��õ��� ����Ʈ ��ȸ*/
	@RequestMapping(value = "/contents", method = RequestMethod.GET)
	public List<ContentDto> contents(@RequestParam("ct_category") String ct_category) {
		return contentService.contents(ct_category);
	}
}
