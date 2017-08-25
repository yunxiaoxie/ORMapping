package com.crab.shiro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crab.shiro.jwt.JwtHelper;
import com.crab.shiro.utils.Constant;
import com.crab.shiro.vo.AccessToken;
import com.crab.shiro.vo.Audience;
import com.crab.shiro.vo.LoginPara;


@RestController
@RequestMapping("/")
public class LoginControl extends BaseControl {
	
	@Autowired
	private Audience audienceEntity;

	@PostMapping("login")
	public ModelMap login(@RequestBody LoginPara loginPara) throws Exception {
		Assert.notNull(loginPara.getUsername(), "account must not be null.");
		Assert.notNull(loginPara.getPassword(), "password must not be null.");
		
		UsernamePasswordToken upt = new UsernamePasswordToken(loginPara.getUsername(), loginPara.getPassword());
		Subject subject = SecurityUtils.getSubject();
		//subject.getPrincipal();
		subject.login(upt);
		//subject.getPrincipal();
		
		// get accessToken
		AccessToken token = getAccessToken(loginPara.getUsername(), null, null);
		
		return retResult(Constant.HTTP_200, Constant.SUCCESS_MSG, token);
	}
	
	private AccessToken getAccessToken(String name, String id, String role) {
		// 拼装accessToken
		String accessToken = JwtHelper.createJWT(name, id,
				role, audienceEntity.getClientId(), audienceEntity.getName(),
				audienceEntity.getExpiresSecond() * 1000, audienceEntity.getBase64Secret());

		// 返回accessToken
		AccessToken accessTokenEntity = new AccessToken();
		accessTokenEntity.setAccess_token(accessToken);
		accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());
		accessTokenEntity.setToken_type("Bearer");
		return accessTokenEntity;
	}
}
