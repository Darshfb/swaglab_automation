package com.saucedemo.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiCoreClient {

    private static final String BASE_URL = "https://api.saucedemo.com/v1";

    /**
     * Centralized Request Specification for all backend interactions.
     * Injects the AllureRestAssured filter to natively capture all payloads and HTTP tracing.
     */
    public static RequestSpecification getBaseReqSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    /**
     * Standard response validations expected across every endpoint.
     */
    public static ResponseSpecification getBaseResSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
