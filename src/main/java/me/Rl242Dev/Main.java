package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(String[] args) {
        JDA bot = JDABuilder.createDefault("")
                .setActivity(Activity.playing("Apprendre"))
                .build();
    }

    /*
    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {

    }

     */
}