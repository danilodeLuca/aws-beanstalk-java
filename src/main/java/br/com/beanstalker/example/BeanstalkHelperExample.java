package br.com.beanstalker.example;

import br.com.beanstalker.BeanStalkException;
import br.com.beanstalker.BeanstalkHelper;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalkClientBuilder;

public class BeanstalkHelperExample {

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

	public void rebuildEnvironmentById() throws BeanStalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byId().rebuildEnvironment("ENV_ID");
	}

	public void rebuildEnvironmentByName() throws BeanStalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byName().rebuildEnvironment("ENV_NAME");
	}

	public void terminateEnvironmentById() throws BeanStalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byId().rebuildEnvironment("ENV_ID");
	}

	public void terminateEnvironmentByName() throws BeanStalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byName().terminateEnvironment("ENV_NAME");
	}

	private AWSElasticBeanstalk getAWSElasticBeastalk() {
		AWSCredentialsProvider awsCredentialsProvider = getAwsCredentialsProvider();
		String awsRegion = "us-east-1";
		return AWSElasticBeanstalkClientBuilder.standard().withCredentials(awsCredentialsProvider).withRegion(awsRegion).build();
	}
}
