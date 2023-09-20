package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Crops;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Resources;
import me.Rl242Dev.DisCraft;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private static Connection connection;

    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
    
            String url = "jdbc:sqlite:"+DisCraft.getInstance().getBaseURL()+"players.db";
    
            connection = DriverManager.getConnection(url);
            System.out.println("DatabaseManager connected");
        }catch(SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    public Item getPickaxeFromUUID(String UUID){
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM players_items WHERE player_id = '" + UUID + "' AND type = 'Pickaxe';";

            ResultSet results = stmt.executeQuery(query);
            
            return new Item(
                    ResourceUtils.getMaterialFromString(results.getString("material")),
                    ResourceUtils.getTypeFromString(results.getString("type")),
                    results.getString("item_id"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getResourceQuantityFromString(String UUID, String resource){
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT resource from player_resources WHERE player_id = '"+UUID+"' AND resource = '"+resource+"';";

            ResultSet resultSet = stmt.executeQuery(query);

            return resultSet.getInt("resource");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public Item getHoeFromUUID(String UUID){
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM players_items WHERE player_id = '" + UUID + "' AND type = 'Hoe';";

            ResultSet results = stmt.executeQuery(query);

            return new Item(
                    ResourceUtils.getMaterialFromString(results.getString("material")),
                    ResourceUtils.getTypeFromString(results.getString("type")),
                    results.getString("item_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<Resources, Integer> getResourcesFromUUID(String UUID){
        Map<Resources, Integer> resources = new HashMap<>();

        for(Resources resource: Resources.values()){
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT quantity FROM player_resources WHERE player_id = '"+UUID+"' AND resource = '"+resource.name()+"';";

                ResultSet result = statement.executeQuery(query);
                resources.put(resource, result.getInt("quantity"));
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return resources;
    }

    public void saveOresToUUID(String UUID, Map<Ores, Integer> ressourcesMap){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject resources = player.getJSONObject("resources");

                /* User data */

                int Stone = resources.getInt("stone");
                int Coal = resources.getInt("coal");
                int Iron = resources.getInt("iron"); 
                int Gold = resources.getInt("gold");
                int Diamond = resources.getInt("diamond");
                int Obsidian = resources.getInt("obsidian");

                /* Current data */

                int StoneMap = ressourcesMap.get(Ores.STONE);
                int CoalMap = ressourcesMap.get(Ores.COAL);
                int IronMap = ressourcesMap.get(Ores.IRON);
                int GoldMap = ressourcesMap.get(Ores.GOLD);
                int DiamondMap = ressourcesMap.get(Ores.DIAMOND);
                int ObsidianMap = ressourcesMap.get(Ores.OBSIDIAN);

                /* Write data */
                resources.remove("stone");
                resources.remove("coal");
                resources.remove("iron");
                resources.remove("gold");
                resources.remove("diamond");
                resources.remove("obsidian");

                resources.put("stone", Stone + StoneMap);
                resources.put("coal", Coal + CoalMap);
                resources.put("iron", Iron + IronMap);
                resources.put("gold", Gold + GoldMap);
                resources.put("diamond", Diamond + DiamondMap);
                resources.put("obsidian", Obsidian + ObsidianMap);

                FileWriter writer = new FileWriter("src/main/resources/players.json" ,false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addToBalanceFromUUID(String UUID, int amout){
        try {
            int balance = getBalanceFromUUID(UUID);

            int totalBalance = balance + amout;
            Statement statement = connection.createStatement();
            String query = "UPDATE players SET balance = '"+totalBalance+"' WHERE player_id = '"+UUID+"';";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeBalanceFromUUID(String UUID, int amout){
        try {
            int balance = getBalanceFromUUID(UUID);
            int totalBalance = balance - amout;

            Statement statement = connection.createStatement();
            String query = "UPDATE players SET balance = '"+totalBalance+"' WHERE player_id = '"+UUID+"';";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveCropsToUUID(String UUID, Map<Crops, Integer> ressourcesMap){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject resources = player.getJSONObject("resources");

                /* User data */

                int Wheat = resources.getInt("wheat");
                int Potato = resources.getInt("potato");
                int Carrot = resources.getInt("carrot");
                int SugarCane = resources.getInt("sugar_cane");

                /* Current data */

                int WheatMap = ressourcesMap.get(Crops.WHEAT);
                int PotatoMap = ressourcesMap.get(Crops.POTATO);
                int CarrotMap = ressourcesMap.get(Crops.CARROT);
                int SugarCaneMap = ressourcesMap.get(Crops.SUGARCANE);

                /* Write data */
                resources.remove("wheat");
                resources.remove("potato");
                resources.remove("carrot");
                resources.remove("sugar_cane");

                resources.put("wheat", Wheat + WheatMap);
                resources.put("potato", Potato + PotatoMap);
                resources.put("carrot", Carrot + CarrotMap);
                resources.put("sugar_cane", SugarCane + SugarCaneMap);

                FileWriter writer = new FileWriter("src/main/resources/players.json" ,false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean userExist(String UUID){
        try  {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM players WHERE player_id = '"+UUID+"'";

            ResultSet resultSet = statement.executeQuery(query);

            return  resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public void registerUser(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            JSONObject player = new JSONObject();

            players.put(UUID, player);
            FileWriter writer = new FileWriter("src/main/resources/players.json");
            writer.write(json.toString());
            writer.flush();
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveLevelToUUID(String UUID, int level){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE players SET level = '"+level+"' WHERE player_id = '"+UUID+"';";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getLevelFromUUID(String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT level FROM players WHERE player_id = '"+UUID+"';";

            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getInt("level");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void resetResourceFromString(String UUID, String resource){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE player_resources SET quantity = 0 WHERE player_id = '"+UUID+"';";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void resetResourcesFromUUID(String UUID) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {
                JSONObject player = players.getJSONObject(UUID);

                JSONObject resources = player.getJSONObject("resources");

                resources.remove("stone");
                resources.remove("coal");
                resources.remove("iron");
                resources.remove("gold");
                resources.remove("diamond");
                resources.remove("obsidian");

                resources.remove("wheat");
                resources.remove("potato");
                resources.remove("carrot");
                resources.remove("sugar_cane");

                resources.put("wheat", 0);
                resources.put("potato", 0);
                resources.put("carrot", 0);
                resources.put("sugar_cane", 0);

                resources.put("stone", 0);
                resources.put("coal", 0);
                resources.put("iron", 0);
                resources.put("gold", 0);
                resources.put("diamond", 0);
                resources.put("obsidian", 0);

                FileWriter writer = new FileWriter("src/main/resources/players.json", false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public int getBalanceFromUUID(String UUID) {
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT balance FROM players WHERE player_id = '"+UUID+"';";

                ResultSet resultSet = statement.executeQuery(query);
                return resultSet.getInt("balance");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }
}
