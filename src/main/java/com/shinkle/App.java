package com.shinkle;

public class App {

//    private static App app = null;
//
//    public static App current() {
//        return app;
//    }

    public void execute(PhotoService photoService, String... args) {
//        if (args.length == 0) {
//            System.out.println("This app requires one command line argument for the albumId");
//            System.exit(1);
//        }
        photoService.retrievePhotos(args[0]);
    }

    public static void main(String[] args) {

    }
}
