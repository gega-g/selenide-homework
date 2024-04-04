package ge.tbc.itacademy.ReportListener;

import ge.tbcitacademy.data.Constants;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomReportListener implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        for (ISuite suite:
                suites){
            String suiteName = suite.getName();
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult suiteResult:
                    suiteResults.values()){
                ITestContext testContext = suiteResult.getTestContext();
                System.out.println(Constants.SUITENAMETEXT+suiteName);
                System.out.println(Constants.TESTNAMETEXT +testContext.getName());

                Set<ITestResult> failedTests = testContext.getFailedTests().getAllResults();
                for (ITestResult result:
                        failedTests){
                    System.out.println(Constants.DESCRIPTION + result.getMethod().getDescription());
                }
            }
        }
    }
}