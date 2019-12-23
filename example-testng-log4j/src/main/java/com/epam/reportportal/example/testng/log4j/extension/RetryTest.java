package com.epam.reportportal.example.testng.log4j.extension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryTest {

	private static final Logger LOGGER = LogManager.getLogger(RetryTest.class);

	private static final AtomicInteger COUNTER = new AtomicInteger(0);

	@Test(retryAnalyzer = RetryImpl.class)
	public void failOne() {
		String errorMsg = "Ooops";
		if (20 != COUNTER.get()) {
			for (int i = 0; i < 10; i++) {
				LOGGER.error(errorMsg);
			}
			Assert.fail(errorMsg);
		}

	}

	public static class RetryImpl implements IRetryAnalyzer {

		@Override
		public boolean retry(ITestResult result) {
			return !result.isSuccess() && COUNTER.incrementAndGet() < 50;
		}
	}
}
