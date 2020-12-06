package com.shinkle;

import org.springframework.web.reactive.function.client.WebClient;

public class App {

    public static final String USAGE = "Usage: App <albumId>\n\t where <albumId> is an integer";
    public static final String MSG_TOO_FEW_ARGUMENTS = "too few arguments";
    public static final String MSG_ALBUM_ID_MUST_BE_INTEGER = "albumId not an integer";
    public static final int ERROR_CODE = 1;

    private static final String BASEURL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) {
        new App().execute(new PhotoService(WebClient.create(BASEURL)), args);
    }

    public void execute(PhotoService photoService, String... args) {
        if (isValidArgument(args)) {
            photoService.retrievePhotosIdsAndTitles(args[0]).forEach(System.out::println);
        } else {
            System.out.println(USAGE);
            exit(ERROR_CODE);
        }
    }

    public void exit(int errorCode) {
        System.exit(errorCode);
    }

    private boolean isValidArgument(String[] args) {
        try {
            Integer.parseInt(args[0]);
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(MSG_TOO_FEW_ARGUMENTS);
            return false;
        } catch (NumberFormatException e) {
            System.out.println(MSG_ALBUM_ID_MUST_BE_INTEGER);
            return false;
        }
    }
}
