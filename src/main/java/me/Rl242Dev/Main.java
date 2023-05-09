package me.Rl242Dev;

import me.Rl242Dev.classes.CommandHandler.Mine;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

/*

Comments :

@A : Author
@U : Usage
@E : Explanation


 */

/*

@A = Rl242Dev
@U = Main
@E = Main code of the bot


 */

public class Main {

    public static final JDABuilder bot = JDABuilder.createDefault("")
            .setActivity(Activity.playing("Apprendre"));


    public static void main(String[] args) {
        bot.build();

        bot.addEventListeners(new Mine());
    }
}