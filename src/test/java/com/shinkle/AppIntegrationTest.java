package com.shinkle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AppIntegrationTest {

    OutputStream consoleText;
    PrintStream console;
    int actualErrorCode;
    App app;

    @BeforeEach
    void setUp() {
        consoleText = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(consoleText));
        actualErrorCode = 0;
        app = new App() {
            @Override
            public void exit(int errorCode) {
                AppIntegrationTest.this.actualErrorCode = errorCode;
            }
        };
    }

    @Test
    void shouldDisplayListOfPhotoIdsAndTitles() {
        App.main(new String[]{"3"});

        String actualConsoleOutput = "[53] soluta et harum aliquid officiis ab omnis consequatur";
        assertThat(actualConsoleOutput).isEqualTo(consoleText.toString());
        assertThat(actualErrorCode).isEqualTo(0);
    }
}
