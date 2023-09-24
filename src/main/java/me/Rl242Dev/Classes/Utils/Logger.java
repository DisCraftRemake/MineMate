package me.Rl242Dev.Classes.Utils;

import me.Rl242Dev.MineMate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/*

@A = Rl242Dev
@U = Main
@E = Class for Logging data to console

 */

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
            String formattedTime = formatter.format(currentInstant.atZone(ZoneId.of(MineMate.getConfigManager().getString("dev.utc"))));

            System.out.println("["+ MineMate.getConfigManager().getString("general.name")+"] ["+formattedTime+"] "+this.logger.toString());
            this.logger = new StringBuilder();
        }else {
            System.out.println(logger.toString());
        }
    }
}
