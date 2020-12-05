package com.shinkle;

public class App {

    public static final int ERROR_CODE = 1;

    public static void main(String[] args) {

    }

    public void execute(PhotoService photoService, String... args) {
        if (!isValidArgument(args)) {
            exit(ERROR_CODE);
        } else {
            photoService.retrievePhotos(args[0]);
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
