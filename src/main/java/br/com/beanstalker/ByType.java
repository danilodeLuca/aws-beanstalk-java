package br.com.beanstalker;

import com.amazonaws.services.elasticbeanstalk.model.DescribeEnvironmentsRequest;
import com.amazonaws.services.elasticbeanstalk.model.RebuildEnvironmentRequest;
import com.amazonaws.services.elasticbeanstalk.model.TerminateEnvironmentRequest;

enum ByType {
	ID {
		@Override
		public RebuildEnvironmentRequest getRebuildEnvironmentRequest(String environmentId) {
			RebuildEnvironmentRequest rebuildEnvironmentRequest = new RebuildEnvironmentRequest();
			rebuildEnvironmentRequest.withEnvironmentId(environmentId);
			return rebuildEnvironmentRequest;
		}

		@Override
		public TerminateEnvironmentRequest getTerminateEnvironmentRequest(String environmentId) {
			return new TerminateEnvironmentRequest().withEnvironmentId(environmentId);
		}

		@Override
		public DescribeEnvironmentsRequest getDescribeEnvironmentsRequest(String... environments) {
			return new DescribeEnvironmentsRequest().withEnvironmentIds(environments);
		}
	}, NAME {
		@Override
		public RebuildEnvironmentRequest getRebuildEnvironmentRequest(String type) {
			RebuildEnvironmentRequest rebuildEnvironmentRequest = new RebuildEnvironmentRequest();
			rebuildEnvironmentRequest.withEnvironmentName(type);
			return rebuildEnvironmentRequest;
		}

		@Override
		public TerminateEnvironmentRequest getTerminateEnvironmentRequest(String environment) {
			return new TerminateEnvironmentRequest().withEnvironmentName(environment);
		}

		@Override
		public DescribeEnvironmentsRequest getDescribeEnvironmentsRequest(String... environments) {
			return new DescribeEnvironmentsRequest().withEnvironmentNames(environments);
		}
	};

	public abstract RebuildEnvironmentRequest getRebuildEnvironmentRequest(String environment);

	public abstract TerminateEnvironmentRequest getTerminateEnvironmentRequest(String environment);

	public abstract DescribeEnvironmentsRequest getDescribeEnvironmentsRequest(String... environments);

}
