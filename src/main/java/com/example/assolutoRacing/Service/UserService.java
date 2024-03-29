package com.example.assolutoRacing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assolutoRacing.Bean.AuthUserRes;
import com.example.assolutoRacing.Bean.SelectUpdateMailDto;
import com.example.assolutoRacing.Bean.SelectUserBean;
import com.example.assolutoRacing.Bean.UserRes;
import com.example.assolutoRacing.Dto.AuthUserDto;
import com.example.assolutoRacing.Dto.RegistUserDto;
import com.example.assolutoRacing.Dto.UpdatePasswordDto;
import com.example.assolutoRacing.Dto.UpdateUserNameDto;
import com.example.assolutoRacing.Mapper.UserMapper;

/**
 * 
 * @author nakagawa.so
 * ユーザーサービスクラス
 */
@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	
	/**
	 * ユーザーを登録する
	 * @return 登録件数
	 */
	public Integer insert(RegistUserDto registUser) {
		int insertCount = 0;
		try {
			insertCount = userMapper.insert(registUser);
		} catch(Exception e) {
			throw e;
		}
		return insertCount;
	}
	
	/**
	 * ユーザー数をユーザー名から取得する
	 * @return ユーザー件数
	 */
	public Integer selectUserCountByUserName(String userName) {
		int userCount = 0;
		try {
			userCount = userMapper.selectUserCountByUserName(userName);
		} catch(Exception e) {
			throw e;
		}
		return userCount;
	}
	
	/**
	 * ユーザー認証
	 * @return ユーザー件数
	 */
	public AuthUserRes auth(AuthUserDto authUser) {
		AuthUserRes user = new AuthUserRes();
		try {
			user = userMapper.auth(authUser);
		} catch(Exception e) {
			throw e;
		}
		return user;
	}
	
	/**
	 * ユーザーを取得する.
	 * @param selectUserBean
	 * @return ユーザー件数
	 */
	public Integer select(SelectUserBean selectUserBean) {
		int userCount = 0;
		try {
			userCount = userMapper.select(selectUserBean);
		} catch(Exception e) {
			throw e;
		}
		return userCount;		
	}
	
	/**
	 * メールを条件にパスワードを更新する。
	 * @param updatePassword
	 * @return 更新件数
	 */
	public Integer updatePassword(UpdatePasswordDto updatePassword) {
		int updateCount = 0;
		try {
			updateCount = userMapper.updatePassword(updatePassword);
		} catch(Exception e) {
			throw e;
		}
		return updateCount;
	}
	
	/**
	 * ユーザー名からユーザー件数を取得する
	 * @param userName ユーザー名
	 * @return ユーザー件数
	 */
	public Integer selectByUserName(String userName) {
		int userCount = 0;
		try {
			userCount = userMapper.selectByUserName(userName);
		} catch(Exception e) {
			throw e;
		}
		return userCount;
	}
	
	/**
	 * メールからユーザー件数を取得する
	 * @param mail メール
	 * @return ユーザー件数
	 */
	public Integer selectByMail(String mail) {
		int userCount = 0;
		try {
			userCount = userMapper.selectByMail(mail);
		} catch(Exception e) {
			throw e;
		}
		return userCount;
	}
	
	/**
	 * ユーザーidを条件にユーザー情報を取得する
	 * @param userId ユーザーid
	 * @return
	 */
	public UserRes selectbyUserId(int userId) {
		UserRes userRes = new UserRes();
		
		try {
			userRes = userMapper.selectByUserId(userId);
		} catch(Exception e) {
			throw e;
		}
		return userRes;
	}
	
	/**
	 * ユーザー名を更新する
	 * @param updateUserDto ユーザー名更新用Dtoクラス
	 * @return 更新件数
	 */
	public Integer updateUserName(UpdateUserNameDto updateUserNameDto) {
		int userUpdateCount = 0;
		
		try {
			userUpdateCount = userMapper.updateUserName(updateUserNameDto);
		} catch(Exception e) {
			throw e;
		}
		return userUpdateCount;
	}
	
	/**
	 * メールを更新する
	 * @param selectUpdateMailDto
	 * @return 更新件数
	 */
	public Integer updateMail(SelectUpdateMailDto selectUpdateMailDto) {
		int mailUpdateCount = 0;
		
		try {
			mailUpdateCount = userMapper.updateMail(selectUpdateMailDto);
		} catch(Exception e) {
			throw e;
		}
		
		return mailUpdateCount;
	}
}
