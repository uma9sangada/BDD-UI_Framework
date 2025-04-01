package org.uma.web.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private int retryCount = 0;
	private int maxRetryCount;

	public RetryAnalyzer() {
		this.maxRetryCount = ConfigurationManager.getInstance().getMaxRetryCount();
	}

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			retryCount++;
			System.out.println("Retrying test: " + result.getName() + ", Retry count: " + retryCount);
			return true;
		}
		return false;
	}
}