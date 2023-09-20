package me.Rl242Dev.Classes.Utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Logger {

    private StringBuilder logger;
    private boolean Timestamp;

    public Logger(boolean Timestamp){
        this.logger = new StringBuilder();
        this.Timestamp = Timestamp;
    }

    public void appendLogger(String str){
        logger.append(str);
    }

    public String getLoggerAsString(){
        return logger.toString();
    }

    public void send(){
        if(this.Timestamp){
            Instant currentInstant = Instant.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = formatter.format(currentInstant.atZone(ZoneId.of("UTC")));

            System.out.println("["+formattedTime+"] "+this.logger.toString());
        }else {
            System.out.println(logger.toString());
        }
    }
}
