package ge.tbcitacademy.TestListener;

import ge.tbcitacademy.data.Constants;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CustomTestListener implements ITestListener{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.TIMEFORMAT);
    LocalDateTime testStartTime = LocalDateTime.now();


    @Override
    public void onStart(ITestContext context) {
        System.out.printf(Constants.TESTSTART, context.getName(), testStartTime.format(formatter));
    }

    @Override
    public void onFinish(ITestContext context) {
        LocalDateTime testEndTime = LocalDateTime.now();
        System.out.printf(Constants.TESTEND, context.getName(), testEndTime.format(formatter));
        System.out.printf(Constants.TESTTIME, Duration.between(testStartTime, testEndTime).toMillis());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.printf(Constants.TESTMETHODSTART, result.getName(), result.getMethod().getDescription(), testStartTime.format(formatter));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LocalDateTime testEndTime = LocalDateTime.now();
        System.out.printf(Constants.TESTSUCCESS, result.getName(), result.getMethod().getDescription(), testEndTime.format(formatter));
        System.out.printf(Constants.TESTTIME, Duration.between(testStartTime, testEndTime).toMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LocalDateTime testEndTime = LocalDateTime.now();
        System.out.printf(Constants.TESTFAILURE, result.getName(), result.getMethod().getDescription(), testEndTime.format(formatter), result.getStatus());
        System.out.printf(Constants.TESTTIME, Duration.between(testStartTime, testEndTime).toMillis());
    }

}
