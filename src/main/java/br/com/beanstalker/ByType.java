package br.com.beanstalker;

import com.amazonaws.services.elasticbeanstalk.model.RebuildEnvironmentRequest;

enum ByType {
	ID {
		@Override
		public RebuildEnvironmentRequest getRebuildEnvironmentRequest(String environmentId) {
			RebuildEnvironmentRequest rebuildEnvironmentRequest = new RebuildEnvironmentRequest();
			rebuildEnvironmentRequest.withEnvironmentId(environmentId);
			return rebuildEnvironmentRequest;
		}
	}, NAME {
		@Override
		public RebuildEnvironmentRequest getRebuildEnvironmentRequest(String type) {
			RebuildEnvironmentRequest rebuildEnvironmentRequest = new RebuildEnvironmentRequest();
			rebuildEnvironmentRequest.withEnvironmentName(type);
			return rebuildEnvironmentRequest;
		}
	};

	public abstract RebuildEnvironmentRequest getRebuildEnvironmentRequest(String type);
}
