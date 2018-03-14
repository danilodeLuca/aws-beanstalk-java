package br.com.beanstalker.creator;

import br.com.beanstalker.BeanstalkException;
import br.com.beanstalker.BeanstalkHelper;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.CreateEnvironmentRequest;
import com.amazonaws.services.elasticbeanstalk.model.CreateEnvironmentResult;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

public class EnvironmentCreator {

	private static final String SEPARATOR = "|";
	private Map<String, CreateEnvironmentResult> environments = new HashMap<>();

	private BeanstalkHelper helper;
	private CreateEnvironmentRequest creatingEnvironment;
	private Set<CreateEnvironmentRequest> envsToCreate = new HashSet<>();

	public EnvironmentCreator(AWSElasticBeanstalk awsBeanStalk) {
		this.helper = new BeanstalkHelper(awsBeanStalk);
		this.creatingEnvironment = new CreateEnvironmentRequest();
	}

	public static EnvironmentCreator creator(AWSElasticBeanstalk awsBeanStalk) {
		return new EnvironmentCreator(awsBeanStalk);
	}

	public EnvironmentCreator newEnv() {
		this.creatingEnvironment = new CreateEnvironmentRequest();
		this.envsToCreate.add(this.creatingEnvironment);
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
			getCreatingEnvironment().withCNAMEPrefix(url.trim());
		}
		return this;
	}

	public EnvironmentCreator withSavedConfiguration(String savedConfigName) {
		getCreatingEnvironment().withTemplateName(savedConfigName);
		if (getCreatingEnvironment().getCNAMEPrefix() == null) {
			getCreatingEnvironment().withCNAMEPrefix(getCreatingEnvironment().getEnvironmentName());
		}

		return this;
	}

	public EnvironmentCreator withConfiguration(String config) {
		getCreatingEnvironment().withSolutionStackName(config);
		return this;
	}

	public EnvironmentCreator withVersion(String version) {
		getCreatingEnvironment().withVersionLabel(version);
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

	public void kill(String appName, String envName) throws BeanstalkException, InterruptedException {
		CreateEnvironmentResult envToKill = getEnvironment(appName, envName);
		this.helper.byId().terminateEnvironment(envToKill.getEnvironmentId());
	}

	public CreateEnvironmentRequest getCreatingEnvironment() {
		if (this.creatingEnvironment == null)
			this.creatingEnvironment = new CreateEnvironmentRequest();
		return this.creatingEnvironment;
	}

	public EnvironmentCreator process() throws BeanstalkException {
		return process(false);
	}

	public EnvironmentCreator processAndWait() throws BeanstalkException {
		return process(true);
	}

	public EnvironmentCreator process(boolean shouldWaitToBeReady) throws BeanstalkException {
		List<CreateEnvironmentRequest> notProcessed = new ArrayList<>();
		for (CreateEnvironmentRequest env : this.envsToCreate) {
			try {
				CreateEnvironmentResult environmetCreated = helper.createEnvironmet(env);
				addEnvironment(environmetCreated);
			} catch (Exception e) {
				notProcessed.add(env);
			}
		}

		if (shouldWaitToBeReady) {
			List<String> envsIds = getEnvsIds();
			try {
				helper.waitEnviromentsToBeReady(envsIds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (!notProcessed.isEmpty()) {
			throw new BeanstalkException("Some environments were not process!" + notProcessed);
		}
		return this;
	}

	private List<String> getEnvsIds() {
		List<String> envsIds = new ArrayList<>();
		for (CreateEnvironmentResult env : environments.values()) {
			envsIds.add(env.getEnvironmentId());
		}
		return envsIds;
	}

}
