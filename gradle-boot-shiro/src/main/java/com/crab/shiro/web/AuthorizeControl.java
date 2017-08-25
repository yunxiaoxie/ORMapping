package com.crab.shiro.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crab.shiro.jwt.JwtHelper;
import com.crab.shiro.vo.AccessToken;
import com.crab.shiro.vo.Audience;
import com.crab.shiro.vo.LoginPara;

/**
 * 得到授权code
 */
@RestController
@RequestMapping("/")
public class AuthorizeControl extends BaseControl {
	
	@Autowired
	private Audience audienceEntity;

	@RequestMapping("oauth/token")
	public ModelMap getAccessToken(@RequestBody LoginPara loginPara) {
		try {
			if (loginPara.getClientId() == null) {
				return retResult("Fail",	"400", null);
			}

			// 拼装accessToken
			String accessToken = JwtHelper.createJWT(loginPara.getUsername(), "id",
					"role", audienceEntity.getClientId(), audienceEntity.getName(),
					audienceEntity.getExpiresSecond() * 1000, audienceEntity.getBase64Secret());

			// 返回accessToken
			AccessToken accessTokenEntity = new AccessToken();
			accessTokenEntity.setAccess_token(accessToken);
			accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());
			accessTokenEntity.setToken_type("Bearer");
			return retResult("Success",	"200", accessTokenEntity);

		} catch (Exception ex) {
			ex.printStackTrace();
			return retResult("Fail",	"400", null);
		}
	}

}