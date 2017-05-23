package mr.cell.incubator.springboottest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@Component
public class OAuthHelper {
	
	@Autowired
	private AuthorizationServerTokenServices tokenService;
	
	public RequestPostProcessor addBearerToken(final String username, String... authorities) {
		return mockRequest -> {
			OAuth2AccessToken token = getAccessToken(username, authorities);
			mockRequest.addHeader("Authorization", "Bearer " + token.getValue());
			return mockRequest;
		};
	}
	
	public String getAccessTokenValue(final String username, String... authorities) {
		OAuth2AccessToken token = getAccessToken(username, authorities);
		return token.getValue();
	}
	
	public OAuth2AccessToken getAccessToken(final String username, String... authorities) {
		OAuth2Request request = new OAuth2Request(null, "android-bookmarks", null, true, null, null, null, null, null);
		Authentication userAuth = new TestingAuthenticationToken(username, null, authorities);
		OAuth2Authentication oauthAuth = new OAuth2Authentication(request, userAuth);
		OAuth2AccessToken token = tokenService.createAccessToken(oauthAuth);
		return token;
	}

}
