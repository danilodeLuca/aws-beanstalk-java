package br.com.beanstalker.creator;

import br.com.beanstalker.BeanstalkException;
import br.com.beanstalker.BeanstalkHelper;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.CreateEnvironmentRequest;
import com.amazonaws.services.elasticbeanstalk.model.CreateEnvironmentResult;
import org.apache.commons.lang3.RandomUtils;

import java.util.Map;

public class EnvironmentCreator {

	private static final String SEPARATOR = "|";
	private Map<String, CreateEnvironmentResult> environments;
	private BeanstalkHelper helper;

	private CreateEnvironmentRequest creatingEnvironment;

	public EnvironmentCreator(AWSElasticBeanstalk awsBeanStalk) {
		this.helper = new BeanstalkHelper(awsBeanStalk);
		this.creatingEnvironment = new CreateEnvironmentRequest();
	}

	public static EnvironmentCreator creator(AWSElasticBeanstalk awsBeanStalk) {
		return new EnvironmentCreator(awsBeanStalk);
	}

	public EnvironmentCreator newEnv() {
		this.creatingEnvironment = new CreateEnvironmentRequest();
		return this;
	}

	public EnvironmentCreator newEnv(String appName) {
		return newEnv().withApplicationName(appName);
	}

	public EnvironmentCreator newEnv(String appName, String envName) {
		return newEnv(appName).withName(envName);
	}

	public EnvironmentCreator withApplicationName(String appName) {
		if (appName != null)
			getCreatingEnvironment().withApplicationName(appName);
		return this;
	}

	public EnvironmentCreator withName(String envName) {
		if (envName != null) {
			getCreatingEnvironment().withEnvironmentName(envName);
		}
		return this;
	}

	public EnvironmentCreator withUrl(String url) {
		if (url != null) {
			getCreatingEnvironment().withCNAMEPrefix(url);
		}
		return this;
	}

	public EnvironmentCreator loadFromSavedConfiguration(String savedConfigName) {
		getCreatingEnvironment().withTemplateName(savedConfigName);
		if (getCreatingEnvironment().getCNAMEPrefix() == null) {
			getCreatingEnvironment().withCNAMEPrefix(getCreatingEnvironment().getEnvironmentName());
		}

		CreateEnvironmentResult environmetCreated = helper.createEnvironmet(getCreatingEnvironment());
		addEnvironment(environmetCreated);
		return this;
	}

	private void addEnvironment(CreateEnvironmentResult environmetCreated) {
		String key = environmetCreated.getApplicationName() + SEPARATOR + environmetCreated.getEnvironmentName();
		if (!this.environments.containsKey(key)) {
			this.environments.put(key, environmetCreated);
		} else {
			int i = RandomUtils.nextInt(1, 100);
			this.environments.put(key + i, environmetCreated);
		}
	}

	public CreateEnvironmentResult getEnvironment(String appName, String envName) {
		return this.environments.get(appName + SEPARATOR + envName);
	}

	public void killAll() {
		for (CreateEnvironmentResult env : environments.values()) {
			try {
				this.helper.byId().terminateEnvironment(env.getEnvironmentId());
			} catch (InterruptedException | BeanstalkException e) {
				e.printStackTrace();
			}
		}
	}

	public CreateEnvironmentRequest getCreatingEnvironment() {
		if (this.creatingEnvironment == null)
			this.creatingEnvironment = new CreateEnvironmentRequest();
		return this.creatingEnvironment;
	}
}
