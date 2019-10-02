package com.microsoft.identity.common.internal.servertelemetry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;

@RunWith(JUnit4.class)
public class ServerTelemetryTest {

    @Before
    public void setup() {
        ServerTelemetry.clearCurrentRequestTelemetry();
        ServerTelemetry.clearLastRequestTelemetry();
    }

    @Test
    public void testEmitSuccessValidFields() {
        ServerTelemetry.emit(Schema.Key.API_ID, "101");
        ServerTelemetry.emit(Schema.Key.FORCE_REFRESH, "false");

        String actualResult = ServerTelemetry.getCurrentTelemetryHeaderString();
        String expectedResult = Schema.Value.SCHEMA_VERSION + "|101,0|";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmitAvoidOverwrite() {
        ServerTelemetry.emit(Schema.Key.API_ID, "101");
        ServerTelemetry.emit(Schema.Key.API_ID, "102");
        ServerTelemetry.emit(Schema.Key.API_ID, "103");
        ServerTelemetry.emit(Schema.Key.FORCE_REFRESH, "false");

        String actualResult = ServerTelemetry.getCurrentTelemetryHeaderString();
        String expectedResult = Schema.Value.SCHEMA_VERSION + "|101,0|";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testHeaderStringWithNullTelemObject() {
        String actualResult = ServerTelemetry.getCurrentTelemetryHeaderString();
        String expectedResult = null;

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testHeaderStringWithNoFields() {
        ServerTelemetry.startScenario();

        String actualResult = ServerTelemetry.getCurrentTelemetryHeaderString();
        String expectedResult = Schema.Value.SCHEMA_VERSION + "|,|";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmitWithInvalidField() {
        ServerTelemetry.startScenario();
        ServerTelemetry.emit("invalid-fake-key", "102");

        String actualResult = ServerTelemetry.getCurrentTelemetryHeaderString();
        String expectedResult = Schema.Value.SCHEMA_VERSION + "|,|";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmitWithOneValidAndOneInvalidField() {
        ServerTelemetry.startScenario();
        ServerTelemetry.emit(Schema.Key.API_ID, "101");
        ServerTelemetry.emit("invalid-fake-key", "102");

        String actualResult = ServerTelemetry.getCurrentTelemetryHeaderString();
        String expectedResult = Schema.Value.SCHEMA_VERSION + "|101,|";

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testEmptyHeaderStrings() {
        Map<String, String> headerStrings = ServerTelemetry.getTelemetryHeaders();
        Assert.assertEquals(0, headerStrings.size());
    }

    // more tests need to be added

}
