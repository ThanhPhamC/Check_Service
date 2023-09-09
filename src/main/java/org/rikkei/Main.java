package org.rikkei;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import static org.rikkei.Constant.TIME_DELAY;
import static org.rikkei.Constant.TIME_PERIOD;

public class Main {
    private static final Properties props = new Properties();
    public static void main(String[] args){
        getProperties();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Job.schedule(props);
            }
        };
        timer.scheduleAtFixedRate(task, Integer.parseInt(TIME_DELAY), Integer.parseInt(TIME_PERIOD));
    }
    public static void getProperties(){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src/main/resources/configuration.properties");
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}