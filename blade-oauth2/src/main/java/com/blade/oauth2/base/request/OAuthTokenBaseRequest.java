/**
 * 
 */

package com.blade.oauth2.base.request;

import blade.kit.ReflectKit;
import blade.kit.StringKit;

import com.blade.http.Request;
import com.blade.oauth2.OAuth;
import com.blade.oauth2.base.validator.OAuthValidator;
import com.blade.oauth2.exception.OAuthProblemException;
import com.blade.oauth2.kit.OAuthKit;

/**
 * Abstract OAuth Token request class
 * 
 * @author BruceZCQ [zcq@zhucongqi.cn]
 * @version
 */
public abstract class OAuthTokenBaseRequest extends OAuthBaseRequest {

	protected OAuthTokenBaseRequest(Request request)
			throws OAuthProblemException {
		super(request);
	}

	protected OAuthValidator<Request> initValidator()
			throws OAuthProblemException {
		final String requestTypeValue = getParam(OAuth.OAUTH_GRANT_TYPE);
		if (StringKit.isBlank(requestTypeValue)) {
			throw OAuthKit
					.handleOAuthProblemException("Missing grant_type parameter value");
		}
		final Class<? extends OAuthValidator<Request>> clazz = validators
				.get(requestTypeValue);
		if (clazz == null) {
			throw OAuthKit
					.handleOAuthProblemException("Invalid grant_type parameter value");
		}
		return (OAuthValidator<Request>) ReflectKit.newInstance(clazz);
	}

	public String getPassword() {
		return getParam(OAuth.OAUTH_PASSWORD);
	}

	public String getUsername() {
		return getParam(OAuth.OAUTH_USERNAME);
	}

	public String getRefreshToken() {
		return getParam(OAuth.OAUTH_REFRESH_TOKEN);
	}

	/**
	 * response_type : code, 中获得的code
	 * @return
	 */
	public String getCode() {
		return getParam(OAuth.OAUTH_CODE);
	}

	public String getGrantType() {
		return getParam(OAuth.OAUTH_GRANT_TYPE);
	}
}