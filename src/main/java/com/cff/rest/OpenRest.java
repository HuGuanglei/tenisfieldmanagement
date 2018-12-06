package com.cff.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cff.dao.UserDao;
import com.cff.domain.User;
import com.cff.dto.RetCode;

@RestController
@RequestMapping("/open")
public class OpenRest {
	private final static Logger logger = LoggerFactory.getLogger(OpenRest.class);

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/regist", method = RequestMethod.POST, produces = "application/json")
	public RetCode regist(@RequestBody User user) {
		RetCode retCode = new RetCode();
		if(user == null){
			retCode.setErrorCode("400");
			retCode.setErrorMsg("用户为空！");
			return retCode;
		}
		try {
			System.out.println(user.toString());
			user.setRole("0");
			User userSave = userDao.save(user);
			if (userSave == null) {
				retCode.setErrorCode("500");
				retCode.setErrorMsg("注册失败！");
			} else {
				retCode.setErrorCode("200");
				retCode.setErrorMsg("注册成功！");
			}
		} catch (Exception e) {
			retCode.setErrorCode("500");
			retCode.setErrorMsg("注册失败！");
		}

		return retCode;
	}

}
