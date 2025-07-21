package org.test.api.common;

import com.aventstack.extentreports.ExtentTest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

@Slf4j
public class ExtentTestListener implements ITestListener {

  private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

  @Override
  public void onTestStart(ITestResult result) {
    String testName = extractTestName(result);

    String className = result.getMethod()
                             .getRealClass()
                             .getSimpleName();

    ExtentTest test = ExtentReport.getExtent()
                                  .createTest(testName)
                                  .assignCategory(className);

    String description = result.getMethod()
                               .getDescription();
    if (description != null && !description.isBlank()) {
      test.getModel()
          .setDescription(description);
    }

    Optional.ofNullable(result.getParameters())
            .filter(params -> params.length > 0)
            .ifPresent(params -> test.info("Test Data: " + Arrays.toString(params)));

    testThread.set(test);
    result.setAttribute("extentTest", test);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    Optional.ofNullable(getTest(result))
            .ifPresentOrElse(test -> test.pass("Test passed"),
                () -> log.warn("ExtentTest is null in onTestSuccess for: {}", result.getName()));
  }

  @Override
  public void onTestFailure(ITestResult result) {
    Optional.ofNullable(getTest(result))
            .ifPresentOrElse(test -> test.fail("Test failed: " + result.getThrowable()),
                () -> log.warn("ExtentTest is null in onTestFailure for: {}", result.getName()));
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    Optional.ofNullable(getTest(result))
            .ifPresentOrElse(test -> test.skip("Test skipped: " + result.getThrowable()), () -> {
              log.error("ExtentTest is null in onTestSkipped for: {}", result.getName());
              log.error(result.getThrowable()
                              .toString());
            });
  }

  @Override
  public void onFinish(ITestContext context) {
    ExtentReport.tearDown();
    testThread.remove();
  }

  private ExtentTest getTest(ITestResult result) {
    return (ExtentTest) result.getAttribute("extentTest");
  }

  private String extractTestName(ITestResult result) {
    Method method = result.getMethod()
                          .getConstructorOrMethod()
                          .getMethod();
    Test annotation = method.getAnnotation(Test.class);

    if (annotation != null && !annotation.testName()
                                         .isBlank()) {
      return annotation.testName();
    }
    return method.getName();
  }
}
