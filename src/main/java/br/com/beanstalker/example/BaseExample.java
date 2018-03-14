package br.com.beanstalker.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalkClientBuilder;

public class BaseExample {

	private static AWSCredentialsProvider getAwsCredentialsProvider() {
		String awsAccessKey = "YOUR_ACCESS_KEY";
		String awsSecretKey = "YOUR_SECRET_KEY";

		return new AWSCredentialsProvider() {
			AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

			public AWSCredentials getCredentials() {
				return awsCredentials;
			}

			public void refresh() {
			}
		};
	}

	protected AWSElasticBeanstalk getAWSElasticBeastalk() {
		AWSCredentialsProvider awsCredentialsProvider = getAwsCredentialsProvider();
		String awsRegion = "us-east-1";
		return AWSElasticBeanstalkClientBuilder.standard().withCredentials(awsCredentialsProvider).withRegion(awsRegion).build();
	}
}
