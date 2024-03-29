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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.assolutoRacing.Bean.LoginUserRes;
import com.example.assolutoRacing.Bean.RefreshTokenReq;
import com.example.assolutoRacing.Constants.Constants;
import com.example.assolutoRacing.Service.CustomUserDetailsService;
import com.example.assolutoRacing.Service.JwtUtil;

/**
 * リフレッシュトークンコントローラークラス
 * @author nakagawa.so
 *
 */
@RestController
public class RefreshTokenController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping(path = "/refresh/token", method = RequestMethod.POST)
	public ResponseEntity<LoginUserRes> refreshToken(
			@RequestBody(required = true) @Validated RefreshTokenReq refreshTokenReq,
			HttpServletRequest httpServletRequest
		) throws Exception{
		
		LoginUserRes loginUserRes = new LoginUserRes();
		
		HttpHeaders headers = new HttpHeaders();
		
		String refreshToken = refreshTokenReq.getRefreshToken();
		try {
			String username = jwtUtil.extractUsername(refreshToken);
			
			if(StringUtils.isNoneBlank(username)) {
				UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
				
				//リフレッシュトークンが有効である場合
				if(jwtUtil.validateToken(refreshToken, userDetails)) {
					String url = httpServletRequest.getRequestURL().toString();
					
					//アクセストークンを新規に作成
					String acessToken = jwtUtil.generateAcessToken(userDetails,url);
					loginUserRes.setAcessToken(acessToken);
				}
			}
			
		} catch(Exception e) {
			throw new RuntimeException("失敗");
		}
		
		ResponseEntity<LoginUserRes> resEntity = new ResponseEntity<>(loginUserRes,headers,HttpStatus.OK); 
		return resEntity;
	}
}
