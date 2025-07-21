# 📘 Test Automation Framework

This is a test automation framework built with **Java 21**, **Gradle**, **TestNG**, **Lombok**, and
**ExtentReports** for API testing.

---

## 🧰 Technologies & Tools

- **Java 21**
- **Gradle**
- **TestNG**
- **AsserJ**
- **ExtentReports**
- **Lombok**
- **Rest-Assured**

---

## 🚀 Prerequisites

Before you begin, ensure the following are installed:

| Tool                        | Version                 | Link                                                       |
|-----------------------------|-------------------------|------------------------------------------------------------|
| Java SDK                    | 21 (21.0.8)             | https://www.oracle.com/europe/java/technologies/downloads/ |
| Gradle                      | Wrapper included (8.13) | https://gradle.org/                                        |
| IntelliJ IDEA (Recommended) | Latest                  | https://www.jetbrains.com/idea/                            |

---

## 🔧 IDE Setup

### 1. Lombok Plugin for IntelliJ

- Go to `Settings` → `Plugins`
- Search for **Lombok**, install it, and restart IntelliJ
- Enable annotation processing:
    - `Settings` → `Build, Execution, Deployment` → `Compiler` → `Annotation Processors`
    - ✅ Check **Enable annotation processing**

---

## 🧪 Test Execution

You can run the tests using either **Gradle** or directly from your **IDE**.

### ✅ 1. Run via Gradle

```bash
./gradlew clean test
```

- Cleans previous builds and runs all tests using TestNG
- Uses testng.xml for suite configuration
- Generates a detailed ExtentReport after execution

### ✅ 2. Run via IntelliJ (TestNG XML)

- Through testng.xml config file
    - `Right-click on testng.xml` → `Select Run testng.xml`
- Run a specific test class or method
    - `Open the desired test class or method` → `Right-click inside the method or class name` →
      `Select **Run 'ClassName.methodName()'**`

### ✅ 3. Run via GitHub Actions (CI)

This project includes GitHub Actions workflow support for automated test execution on every push or
pull request.

- Can be started automatically on push/pull request or **manually from the Workflows tab**
- Uses `./gradlew clean test` internally to execute the full test suite
- After test execution, the HTML report (`ExtentReport.html`) is uploaded as an artifact

#### ▶️ How to use it:

- **To run automatically:** Push your code to a tracked branch (e.g., `main` or
  `feature/your-feature`)
- **To run manually:**
    1. Go to the **Actions** tab in your GitHub repository
    2. Select the workflow named (e.g., `API Tests`)
    3. Click the **Run workflow** button on the right side
    4. Choose the branch to run on (optional) and confirm
- After the workflow completes, open the **Actions** tab
- Select the latest workflow run
- Download the test report from the **Artifacts** section (named `ExtentReport`)

## 📈 Test Report

You can retrieve test reports after running locally or in CI.
The extended test automation report includes metrics such as:

- Test pass/fail status
- Execution time

  Tests divided by categories for better test report analytics

### ✅ 1. Test Report after local execution

- After local test execution (steps are mention above)
    - `Navigate to the `build/reports` folder ` →
      `Open "ExtentReport.html" in your browser to view the detailed report`

### ✅ 2. Test Report as attachments in CI

- When running tests via CI (e.g., GitHub Actions):
    - `The report is generated automatically and uploaded as an artifact` →
      `You can download the "ExtentReport.html" file from the workflow run's **Artifacts** section in
          the CI UI`