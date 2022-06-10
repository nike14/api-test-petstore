package com.api.core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Date;


public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance(String directoryName) {
        if (extent == null)
            createInstance(directoryName);
        return extent;
    }

    public static ExtentReports createInstance(String directoryName) {
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }
        Date date = new Date();
        String fileName = date.toString().replace(":", "_").replace(" ", "_") +"_"+"extent.html";
        String reportPath = directoryName + fileName;
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("ExtentReports");
        htmlReporter.config().setReportName("ExtentReports.html");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("utf-8");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}