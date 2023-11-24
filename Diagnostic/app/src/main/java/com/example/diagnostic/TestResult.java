package com.example.diagnostic;

public class TestResult {
        private String testName;
        private String testResult;

        public TestResult() {

        }

        public TestResult(String testName, String testResult) {
            this.testName = testName;
            this.testResult = testResult;
        }

        public String getTestName() {
            return testName;
        }

        public String getTestResult() {
            return testResult;
        }

}
