package br.com.beanstalker;

import com.amazonaws.services.elasticbeanstalk.model.EnvironmentStatus;

class EnvironmentStatusHealth {
	String status;
	String health;

	EnvironmentStatusHealth(String status, String health) {
		this.status = status;
		this.health = health;
	}

	public boolean isEnvironmentUp() {
		return !status.equalsIgnoreCase(EnvironmentStatus.Terminated.toString()) && !status.equalsIgnoreCase(EnvironmentStatus.Terminating.toString());
	}
}
