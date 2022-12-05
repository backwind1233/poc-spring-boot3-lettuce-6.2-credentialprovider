package com.example.lettuce6.credentialprovider.implementation;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import io.lettuce.core.RedisCredentials;
import io.lettuce.core.RedisCredentialsProvider;
import reactor.core.publisher.Mono;

public class AzureCredentialsProvider implements RedisCredentialsProvider {
    public static final String SCOPE  = "https://*.cacheinfra.windows.net:10225/appid/.default";
    private String userName  = "${user_name}";

    TokenCredential credential;
    public AzureCredentialsProvider(TokenCredential credential) {
        this.credential = credential;
    }

    @Override
    public Mono<RedisCredentials> resolveCredentials() {
        TokenRequestContext requestContext = new TokenRequestContext()
                .addScopes(SCOPE);
        Mono<AccessToken> token = credential
                .getToken(requestContext);
        return token.map(accessToken ->
            RedisCredentials.just(userName, accessToken.getToken())
        );
    }
}
