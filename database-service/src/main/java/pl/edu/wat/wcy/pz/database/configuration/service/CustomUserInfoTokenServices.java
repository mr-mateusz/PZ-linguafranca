package pl.edu.wat.wcy.pz.database.configuration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.*;

public class CustomUserInfoTokenServices implements ResourceServerTokenServices {

    private final Logger logger = LoggerFactory.getLogger(CustomUserInfoTokenServices.class);

    private static final String[] PRINCIPAL_KEYS = new String[]{"user", "username",
            "userid", "user_id", "login", "id", "name"};

    private final String userInfoEndpointUrl;

    private final String clientId;

    private OAuth2RestOperations restTemplate;

    private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

    public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        this.userInfoEndpointUrl = userInfoEndpointUrl;
        this.clientId = clientId;
    }

    public void setRestTemplate(OAuth2RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
        this.authoritiesExtractor = authoritiesExtractor;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        Map<String, Object> map = getMap(userInfoEndpointUrl, accessToken);
        if (map.containsKey("error")) {
            logger.debug("Userinfo returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        }
        return extractAuthentication(map);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap(String userInfoEndpointUrl, String accessToken) {
        logger.debug("Getting user info from: " + userInfoEndpointUrl);
        try {
            OAuth2RestOperations oAuth2RestOperations = restTemplate;
            if (oAuth2RestOperations == null) {
                BaseOAuth2ProtectedResourceDetails resourceDetails = new BaseOAuth2ProtectedResourceDetails();
                resourceDetails.setClientId(clientId);
                oAuth2RestOperations = new OAuth2RestTemplate(resourceDetails);
            }
            OAuth2AccessToken existingToken = oAuth2RestOperations.getOAuth2ClientContext().getAccessToken();
            if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
                token.setTokenType(tokenType);
                oAuth2RestOperations.getOAuth2ClientContext().setAccessToken(token);
            }
            return oAuth2RestOperations.getForEntity(userInfoEndpointUrl, Map.class).getBody();
        } catch (Exception e) {
            logger.info("Cannot fetch user details " + e.getClass() + ", " + e.getMessage());
            return Collections.singletonMap("error", "Could not fetch user details");
        }
    }

    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        Object principal = getPrincipal(map);
        OAuth2Request request = getRequest(map);
        List<GrantedAuthority> authorities = authoritiesExtractor.extractAuthorities(map);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        token.setDetails(map);
        return new OAuth2Authentication(request, token);
    }

    @SuppressWarnings("unchecked")
    private OAuth2Request getRequest(Map<String, Object> map) {
        Map<String, Object> request = (Map<String, Object>) map.get("oauth2Request");
        String clientId = (String) request.get("clientId");
        Set<String> scopes = new LinkedHashSet<>(request.containsKey("scope") ? (Collection<String>) request.get("scope") : Collections.emptySet());
        return new OAuth2Request(null, clientId, null, true, new HashSet<>(scopes),
                null, null, null, null);
    }

    private Object getPrincipal(Map<String, Object> map) {
        for (String principalKey : PRINCIPAL_KEYS) {
            if (map.containsKey(principalKey))
                return map.get(principalKey);
        }
        return "unknown";
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Read Access token - not supported");
    }


}
