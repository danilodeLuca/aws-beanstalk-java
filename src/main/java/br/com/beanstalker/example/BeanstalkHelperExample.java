package br.com.beanstalker.example;

import br.com.beanstalker.BeanstalkException;
import br.com.beanstalker.BeanstalkHelper;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalkClientBuilder;

public class BeanstalkHelperExample extends BaseExample {

	public void rebuildEnvironmentById() throws BeanstalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byId().rebuildEnvironment("ENV_ID");
	}

	public void rebuildEnvironmentByName() throws BeanstalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byName().rebuildEnvironment("ENV_NAME");
	}

	public void terminateEnvironmentById() throws BeanstalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byId().rebuildEnvironment("ENV_ID");
	}

	public void terminateEnvironmentByName() throws BeanstalkException, InterruptedException {
		AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
		new BeanstalkHelper(awsElasticBeastalk).byName().terminateEnvironment("ENV_NAME");
	}

}
