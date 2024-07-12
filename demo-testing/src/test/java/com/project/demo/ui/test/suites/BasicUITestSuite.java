package com.project.demo.ui.test.suites;


import com.project.demo.ui.test.basic.LoginTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@SelectClasses({LoginTest.class})
@Suite
public class BasicUITestSuite
{
}
