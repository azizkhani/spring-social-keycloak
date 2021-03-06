/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.keycloak.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.keycloak.api.Keycloak;
import org.springframework.social.keycloak.api.KeycloakUser;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Github ApiAdapter implementation.
 * @author ali akbar azizkhani
 */
public class KeycloakAdapter implements ApiAdapter<Keycloak> {

	public boolean test(Keycloak keycloak) {
		try {
			keycloak.getUserProfile();
			return true;
		} catch (HttpClientErrorException e) {
			// TODO : Beef up Keycloak's error handling and trigger off of a more specific exception
			return false;
		}
	}

	public void setConnectionValues(Keycloak keycloak, ConnectionValues values) {
		KeycloakUser profile = keycloak.getUserProfile();
		values.setProviderUserId(String.valueOf(profile.getId()));
		values.setDisplayName(profile.getName());
		values.setProfileUrl("http://localhost:8080/auth/realms/master/protocol/openid-connect/userinfo"); // TODO: Expose and use HTML URL
	}

	public UserProfile fetchUserProfile(Keycloak keycloak) {
		KeycloakUser profile = keycloak.getUserProfile();
		return new UserProfileBuilder().setName(profile.getName()).setEmail(profile.getEmail()).setUsername(profile.getEmail()).build();
	}

	public void updateStatus(Keycloak keycloak, String message) {
		// not supported
	}

}
