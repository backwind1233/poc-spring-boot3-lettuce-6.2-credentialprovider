package com.example.lettuce6.credentialprovider.config;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.example.lettuce6.credentialprovider.implementation.AzureRedisCredentialsProviderFactory;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.protocol.ProtocolVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class AppConfig {
    @Value("${spring.data.redis.azure.client-id}")
    private String CLIENT_ID;
    @Value("${spring.data.redis.azure.tenant-id}")
    private String TENANT_ID;
    @Value("${spring.data.redis.azure.client-secret}")
    private String CLIENT_SECRET;
    @Value("${spring.data.redis.host}")
    private String HOST_NAME;
    public int PORT = 6380;

    @Bean
    TokenCredential tokenCredential() {
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .clientId(CLIENT_ID)
                .tenantId(TENANT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();
        return credential;
    }

    @Bean
    AzureRedisCredentialsProviderFactory azureRedisCredentialsProviderFactory(TokenCredential tokenCredential){
        return new AzureRedisCredentialsProviderFactory(tokenCredential);
    }

    @Bean
    RedisConnectionFactory connectionFactory(AzureRedisCredentialsProviderFactory azureRedisCredentialsProviderFactory) {
        LettuceClientConfiguration build = LettuceClientConfiguration.builder()
                .redisCredentialsProviderFactory(azureRedisCredentialsProviderFactory)
                // It has to be RESP2, RESP3 doesn't support AAD Authentication.
                .clientOptions(ClientOptions.builder().protocolVersion(ProtocolVersion.RESP2).build())
                .useSsl()
                .build();

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(HOST_NAME, PORT);
        return new LettuceConnectionFactory(config,build);
    }
}
