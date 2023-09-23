package me.Rl242Dev;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.Rl242Dev.Classes.Cases.Case;
import me.Rl242Dev.Classes.Cases.HeroLoots;
import me.Rl242Dev.Classes.Cases.NormalLoots;
import me.Rl242Dev.Classes.Utils.Logger;
import me.Rl242Dev.CommandHandler.Discord.LevelHandler;
import me.Rl242Dev.CommandHandler.Discord.Shop.SellHandler;
import me.Rl242Dev.CommandHandler.Discord.Shop.ShopDisplayHandler;
import me.Rl242Dev.CommandHandler.Discord.StartHandler;
import me.Rl242Dev.CommandHandler.Discord.UtilsHandler;
import me.Rl242Dev.CommandHandler.Minecraft.Actions.HarvestHandler;
import me.Rl242Dev.CommandHandler.Minecraft.Actions.MineHandler;
import me.Rl242Dev.CommandHandler.Mutliplayer.MiningContest;
import me.Rl242Dev.DatabaseManager.DatabaseManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.JSONObject;

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

    private static JDA bot;
    private final String BASE_URL = "src/main/resources/";
    private static DisCraft instance;
    private static DatabaseManager databaseManager;
    private List<Case> cases = new ArrayList<>();
    public static Logger logger;

    public static void main(String[] args) {
        logger = new Logger(true);
        JDA bot = JDABuilder.createLight(getToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("Getting build"))
                .build();

        bot.addEventListener(new MineHandler());
        bot.addEventListener(new StartHandler());
        bot.addEventListener(new HarvestHandler());
        bot.addEventListener(new UtilsHandler());
        bot.addEventListener(new SellHandler());
        bot.addEventListener(new ShopDisplayHandler());
        bot.addEventListener(new LevelHandler());
        bot.addEventListener(new MiningContest());

        instance = new DisCraft();

        DatabaseManager databaseManagerLocal = new DatabaseManager();
        databaseManagerLocal.connect();

        databaseManager = databaseManagerLocal;

        setBot(bot);

        initDatabases();
        initCases();

        logger.appendLogger("Connected to: "+getBot().getSelfUser().getName());
        logger.send();
    }

    private static String getToken(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2];
        String callingClassName = caller.getClassName();

        if(!callingClassName.equals("me.Rl242Dev.DisCraft")){
            return null;
        }

        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/token.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            return json.getString("token");
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private static void initDatabases() {
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
                    "player_id TEXT PRIMARY KEY," +
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

    private static void initCases(){
        /* Normal Case */
        Map<NormalLoots, Integer> normalLootsIntegerMap = new HashMap<>();
        normalLootsIntegerMap.put(NormalLoots.PET_GOAT, 20);
        normalLootsIntegerMap.put(NormalLoots.PET_CAT, 20);
        normalLootsIntegerMap.put(NormalLoots.PET_BEE, 20);
        normalLootsIntegerMap.put(NormalLoots.ITEM_IRON_PICKAXE, 5);
        normalLootsIntegerMap.put(NormalLoots.LEVEL_TWO, 10);
        normalLootsIntegerMap.put(NormalLoots.LEVEL_FIVE, 10);
        normalLootsIntegerMap.put(NormalLoots.GRADE_NORMAL_LOOT, 15);

        Case normalCase = new Case("\uD83D\uDD10 | Normal Case", 100000, normalLootsIntegerMap);
        getInstance().cases.add(normalCase);

        Map<HeroLoots, Integer> heroLootsIntegerMap = new HashMap<>();

    }

    public static JDA getBot(){
        return bot;
    }

    public String getBaseURL(){
        return BASE_URL;
    }

    public List<Case> getCases() {
        return cases;
    }

    public static DisCraft getInstance(){
        return instance;
    }

    public static void setBot(JDA bot) {
        DisCraft.bot = bot;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}