package br.com.zup.edu.revendacarros.shared;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * Infelizmente a lib Open Feign ainda não suporta a versão do OAuth2 no Spring Security 5. Seu interceptor
 * padrão funciona somente com a versão antiga.
 *
 * Inpirado na implementação do @loesak
 * - https://github.com/loesak/spring-security-openfeign/blob/master/README.md
 * - https://www.baeldung.com/spring-cloud-feign-oauth-token
 */
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final Authentication ANONYMOUS_AUTHENTICATION = new AnonymousAuthenticationToken(
            "anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final String clientRegistrationId;

    public OAuth2FeignRequestInterceptor(OAuth2AuthorizedClientManager authorizedClientManager, String clientRegistrationId) {
        this.authorizedClientManager = authorizedClientManager;
        this.clientRegistrationId = clientRegistrationId;
    }

    @Override
    public void apply(RequestTemplate request) {
        if (this.authorizedClientManager == null) {
            return;
        }

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(this.clientRegistrationId)
                .principal(ANONYMOUS_AUTHENTICATION)
                .build();

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);
        if (authorizedClient == null) {
            throw new IllegalStateException(
                    "This client uses an authorization grant type that's not supported by the " +
                            "configured provider. ClientRegistrationId = " + this.clientRegistrationId);
        }

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        request
                .header(HttpHeaders.AUTHORIZATION,
                        String.format("Bearer %s", accessToken.getTokenValue()));
    }
}
