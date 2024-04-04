package ge.tbc.itacademy.ReportListener;

import ge.tbcitacademy.data.Constants;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomReportListenerForCheckboxRadioButton implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResults = suite.getResults();

            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext testContext = suiteResult.getTestContext();

                Set<ITestResult> relevantResults = testContext.getFailedTests().getAllResults();
                for (ITestResult result : relevantResults) {
                    System.out.println(Constants.DESCRIPTION + result.getMethod().getDescription());

                }
            }
        }
    }
}
