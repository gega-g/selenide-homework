package ge.tbcitacademy.Retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int tryCount = 0;
    @Override
    public boolean retry(ITestResult testResult) {
        IRetry retry = testResult
                .getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(IRetry.class);
        if ((retry != null) && tryCount < retry.count()){
            tryCount++;
            return true;
        }
        return false;
    }
}
