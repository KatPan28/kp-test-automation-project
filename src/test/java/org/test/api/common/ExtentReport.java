package org.test.api.common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentReport {

  private static volatile ExtentReports extent;

  private ExtentReport() {
  }

  public static ExtentReports getExtent() {
    if (extent == null) {
      synchronized (ExtentReport.class) {
        if (extent == null) {
          ExtentSparkReporter spark = new ExtentSparkReporter("build/reports/ExtentReport.html");
          spark.config()
               .setDocumentTitle("API Test Report");
          spark.config()
               .setReportName("Automation Test Results");

          extent = new ExtentReports();
          extent.attachReporter(spark);
        }
      }
    }
    return extent;
  }

  public static void tearDown() {
    if (extent != null) {
      extent.flush();
    }
  }
}
