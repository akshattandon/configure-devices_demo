package com.akshat.configuredevices.cucumber.stepdefs;

import com.akshat.configuredevices.ConfiguredevicesApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ConfiguredevicesApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
