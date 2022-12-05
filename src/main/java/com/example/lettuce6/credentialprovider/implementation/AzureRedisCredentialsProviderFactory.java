package com.example.lettuce6.credentialprovider.implementation;

import com.azure.core.credential.TokenCredential;
import io.lettuce.core.RedisCredentialsProvider;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.lettuce.RedisCredentialsProviderFactory;

public class AzureRedisCredentialsProviderFactory implements RedisCredentialsProviderFactory {
    TokenCredential tokenCredential;

    public AzureRedisCredentialsProviderFactory(TokenCredential tokenCredential) {
        this.tokenCredential = tokenCredential;
    }

    @Override
    public RedisCredentialsProvider createCredentialsProvider(RedisConfiguration redisConfiguration) {
        return new AzureCredentialsProvider(tokenCredential);
    }
}
