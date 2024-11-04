package dat;

import dat.config.ApplicationConfig;
import dat.config.Populator;

public class Main {
    public static void main(String[] args) {
        Populator.populate();
        ApplicationConfig.startServer(7007);
    }
}