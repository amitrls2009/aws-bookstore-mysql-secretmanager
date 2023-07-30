package com.bookstore.config;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;

@Configuration
public class ApplicationConfig {

	private static final Logger log = LogManager.getLogger(ApplicationConfig.class);

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretkey;
	@Value("${cloud.aws.region.static}")
	private String region;

	private Gson gson = new Gson();

	@Bean
	public DataSource dataSource() {
		AwsSecrets secrets = getSecret();
		log.info("DB details from Secret Manager  " + secrets.toString());

		return DataSourceBuilder.create()
				.url("jdbc:" + secrets.getEngine() + "://" + secrets.getHost() + ":" + secrets.getPort() + "/bookstore")
				.username(secrets.getUsername()).password(secrets.getPassword()).build();
	}

	private AwsSecrets getSecret() {

		String secretName = "bookstore-mysql-credentials";

		AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretkey)))
				.build();

		String secret;
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
		GetSecretValueResult getSecretValueResult = null;

		try {
			getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		} catch (Exception e) {
			throw e;
		}
		if (getSecretValueResult.getSecretString() != null) {
			secret = getSecretValueResult.getSecretString();			
			return gson.fromJson(secret, AwsSecrets.class);
		}

		return null;
	}

}
