package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;

public class App {

    public static final int ERROR_CODE = 1;

    public static void main(String[] args) {
        new App().execute(new PhotoService(WebClient.create("https://jsonplaceholder.typicode.com")), args);
    }

    public void execute(PhotoService photoService, String... args) {
        if (!isValidArgument(args)) {
            exit(ERROR_CODE);
        } else {
            Photo[] photos = photoService.retrievePhotosIdsAndTitles(args[0]);
            for (Photo p : photos) {
                System.out.println(p);
            }
        }
    }

    public void exit(int errorCode) {
        System.exit(errorCode);
    }

    private boolean isValidArgument(String[] args) {
        try {
            Integer.parseInt(args[0]);
            return true;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return false;
        }
    }
}
