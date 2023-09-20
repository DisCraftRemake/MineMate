package me.Rl242Dev;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.Rl242Dev.CommandHandler.DS.LevelHandler;
import me.Rl242Dev.CommandHandler.DS.Shop.SellHandler;
import me.Rl242Dev.CommandHandler.DS.Shop.ShopDisplayHandler;
import me.Rl242Dev.CommandHandler.DS.StartHandler;
import me.Rl242Dev.CommandHandler.DS.UtilsHandler;
import me.Rl242Dev.CommandHandler.MC.Actions.HarvestHandler;
import me.Rl242Dev.CommandHandler.MC.Actions.MineHandler;
import me.Rl242Dev.DatabaseManager.DatabaseManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

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

public class DisCraft {

    static JDA bot;
    private final String BASE_URL = "src/main/resources/";
    public static DisCraft instance;
    private DatabaseManager databaseManager;

    public static void main(String[] args) {
        JDA bot = JDABuilder.createLight("MTEwNTg3NDEzNzcwMTk0MTMyMQ.GSnY4V.ZAls7_o799PKkxvn_n5Ch5rTofaGIjj4rUUeFg", GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("Getting build"))
                .build();

        bot.addEventListener(new MineHandler());
        bot.addEventListener(new StartHandler());
        bot.addEventListener(new HarvestHandler());
        bot.addEventListener(new UtilsHandler());
        bot.addEventListener(new SellHandler());
        bot.addEventListener(new ShopDisplayHandler());
        bot.addEventListener(new LevelHandler());

        instance = new DisCraft();

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect();

        initDatabases();
    }

    public static void initDatabases() {
        Connection conn = null;
        try { 
            Class.forName("org.sqlite.JDBC");
    
            String url = "jdbc:sqlite:"+DisCraft.getInstance().getBaseURL()+"players.db";
    
            conn = DriverManager.getConnection(url);

            String createPlayersTableSQL = "CREATE TABLE IF NOT EXISTS players (" +
                    "player_id TEXT PRIMARY KEY," +
                    "balance INT," +
                    "level INT" +
                    ")";

            String createPlayerItemsTableSQL = "CREATE TABLE IF NOT EXISTS player_items (" +
                    "item_id TEXT PRIMARY KEY," +
                    "player_id TEXT," +
                    "type TEXT," +
                    "material TEXT" +
                    ")";

            String createPlayerResourcesTableSQL = "CREATE TABLE IF NOT EXISTS player_resources (" +
                    "item_id TEXT PRIMARY KEY," +
                    "player_id TEXT," +
                    "resource TEXT," +
                    "quantity INT" +
                    ")";

            Statement stmt = conn.createStatement();
            stmt.execute(createPlayersTableSQL);
            stmt.execute(createPlayerItemsTableSQL);
            stmt.execute(createPlayerResourcesTableSQL);

            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error closing the database connection: " + ex.getMessage());
            }
        }
    }

    public static JDA getBot(){
        return bot;
    }

    public String getBaseURL(){
        return BASE_URL;
    }

    public static DisCraft getInstance(){
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}