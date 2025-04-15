package org.qa.textcontext;

import org.qa.TestBase;
import org.qa.pageobjectmanager.PageObjectManager;

import java.io.IOException;

public class TestContext {

    public PageObjectManager pageObjectManager;
    public TestBase testBase;

    public TestContext() throws IOException {
        testBase = new TestBase();
        pageObjectManager = new PageObjectManager(testBase.initilizeBrowser());
    }
}
