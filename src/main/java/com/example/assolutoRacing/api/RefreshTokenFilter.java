package com.example.assolutoRacing.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.assolutoRacing.Bean.LoginUserRes;
import com.example.assolutoRacing.Service.CustomUserDetailsService;
import com.example.assolutoRacing.Service.JwtUtil;

/**
 * リフレッシュトークンフィルタークラス
 * @author nakagawa.so
 *
 */
public class RefreshTokenFilter {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping(path = "/refresh/token", method = RequestMethod.POST)
	public ResponseEntity<LoginUserRes> refreshToken(HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) throws Exception{
		
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		LoginUserRes loginUserRes = new LoginUserRes();
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<LoginUserRes> resEntity = new ResponseEntity<>(loginUserRes,headers,HttpStatus.OK); 
		
		//リフレッシュトークン
		String refreshToken = "";
		
		//ユーザー名
		String username = "";
		try {
			if(StringUtils.isNoneBlank(authorizationHeader) && authorizationHeader.startsWith("Basic")) {
				//リフレッシュトークン
				refreshToken = authorizationHeader.substring(6);
				//ユーザー名
				username = jwtUtil.extractUsername(refreshToken);
			} else {
				throw new RuntimeException("リフレッシュトークンが無効です");
			}
			
			if(StringUtils.isNoneBlank(username)) {
				UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
				
				Date acessExp = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);

				//暫定処理
				if(jwtUtil.validateToken(refreshToken, userDetails)) {
					
					//アクセストークンを新規に作成
					String acessToken = jwtUtil.generateToken(userDetails, acessExp);
					loginUserRes.setAcessToken(acessToken);
				}
			}			
		} catch(Exception e) {
			throw e;
		}
		return resEntity;
	}
}