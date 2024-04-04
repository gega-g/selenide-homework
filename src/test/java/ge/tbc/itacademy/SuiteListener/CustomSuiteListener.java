package ge.tbc.itacademy.SuiteListener;

import ge.tbcitacademy.data.Constants;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.time.Duration;
import java.time.LocalDateTime;

public class CustomSuiteListener implements ISuiteListener{

    LocalDateTime suiteStartTime = LocalDateTime.now();

    @Override
    public void onStart(ISuite suite) {
        System.out.printf(Constants.SUITESTART, suite.getName(), suiteStartTime);
    }

    @Override
    public void onFinish(ISuite suite) {
        LocalDateTime suiteEndTime = LocalDateTime.now();
        System.out.printf(Constants.SUITEEND, suite.getName(), suiteEndTime);
        System.out.printf(Constants.SUITETIME, Duration.between(suiteStartTime, suiteEndTime).toMillis());
    }
}
