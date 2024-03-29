package com.example.assolutoRacing.api;

import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Dto.SettingInfoDto;
import com.example.assolutoRacing.Service.SettingInfoService;


@RestController
public class HomeController {
	@Autowired
	SettingInfoService settingInfoService;
	
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public ResponseEntity<List<SettingInfoDto>> home(@RequestParam(value = "userId") Integer userId) {
		List<SettingInfoDto> settingInfoList = new ArrayList<>();
		
		try {
			settingInfoList = settingInfoService.selectAll(userId);
		} catch(Exception e) {
			throw e;
		}
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<List<SettingInfoDto>> resEntity = new ResponseEntity<>(settingInfoList,headers,HttpStatus.OK); 
		return resEntity;
	}

}