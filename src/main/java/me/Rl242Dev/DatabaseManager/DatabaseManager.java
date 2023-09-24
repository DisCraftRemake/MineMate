package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Entity.Pets.Pets;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Crops;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Resources;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import me.Rl242Dev.MineMate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*

@A = Rl242Dev
@U = Database
@E = Class for the DatabaseManager, used to query data

 */

public class DatabaseManager {

    private static Connection connection;

    public void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
    
            String url = "jdbc:sqlite:"+ MineMate.getInstance().getBaseURL()+"players.db";
    
            connection = DriverManager.getConnection(url);
            MineMate.logger.appendLogger("DatabaseManager connected");
            MineMate.logger.send();
        }catch(SQLException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
    }

    public Item getPickaxeFromUUID(String UUID){
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM player_items WHERE player_id = '" + UUID + "' AND type = 'Pickaxe';";

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
            String query = "SELECT quantity from player_resources WHERE player_id = '"+UUID+"' AND resource = '"+resource+"';";

            ResultSet resultSet = stmt.executeQuery(query);

            return resultSet.getInt("quantity");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public Item getHoeFromUUID(String UUID){
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM player_items WHERE player_id = '" + UUID + "' AND type = 'Hoe';";

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
        for(Ores ore : ressourcesMap.keySet()){
            try {
                Statement statement = connection.createStatement();
                String queryResource = "SELECT quantity FROM player_resources WHERE player_id = '"+UUID+"' AND resource = '"+ore.name()+"';";

                ResultSet resourceResult = statement.executeQuery(queryResource);

                int total = resourceResult.getInt("quantity") + ressourcesMap.get(ore);
                String query = "UPDATE player_resources SET quantity = '"+total+"' WHERE player_id = '"+UUID+"' AND resource = '"+ore.name()+"';";
                statement.execute(query);
            }catch (SQLException e){
                e.printStackTrace();
            }
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

    public Map<String, Integer> getLeaderboard(){
        try {
            Map<String, Integer> leaderboard = new HashMap<>();
            Statement statement = connection.createStatement();

            String query = "SELECT player_id, balance FROM players ORDER BY balance ASC LIMIT 10";
            ResultSet result = statement.executeQuery(query);

            while (result.next()){
                leaderboard.put(
                        result.getString("player_id"),
                        result.getInt("balance")
                );
            }

            return leaderboard;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void saveCropsToUUID(String UUID, Map<Crops, Integer> ressourcesMap){
        for(Crops crop : ressourcesMap.keySet()){
            try {
                Statement statement = connection.createStatement();
                String queryResource = "SELECT quantity FROM player_resources WHERE player_id = '"+UUID+"' AND resource = '"+crop.name()+"';";

                ResultSet resourceResult = statement.executeQuery(queryResource);

                int total = resourceResult.getInt("quantity") + ressourcesMap.get(crop);
                String query = "UPDATE player_resources SET quantity = '"+total+"' WHERE player_id = '"+UUID+"' AND resource = '"+crop.name()+"';";
                statement.execute(query);
            }catch (SQLException e){
                e.printStackTrace();
            }
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

    public void updateItem(String UUID, Item item){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE player_items SET material = '"+item.getMaterial().toString()+"' WHERE player_id = '"+UUID+"' AND type = '"+DatabaseUtils.getNameFromType(item.getType())+"';";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void prestigeUser(String UUID){
        try {
            Statement statement = connection.createStatement();

            for(Type type : Type.values()){
                String query = "UPDATE player_items SET material = 'Wood' WHERE player_id = '"+UUID+"' AND type = '"+DatabaseUtils.getNameFromType(type)+"';";
                statement.execute(query);
            }
            for(Pets pet : Pets.values()){
                String query = "UPDATE player_pets SET pet = '' WHERE player_id = '"+UUID+"' AND pet = '"+DatabaseUtils.getNameFromPet(pet)+"';";
                statement.execute(query);
            }

            String balance = "UPDATE players SET balance = 0 WHERE player_id = '"+UUID+"';";
            String level = "UPDATE players SET level = 0 WHERE player_id = '"+UUID+"';";

            statement.execute(balance);
            statement.execute(level);

            resetResourcesFromUUID(UUID);

            String updatePrestige = "UPDATE players SET prestige = '"+getPrestige(UUID)+1+"' WHERE player_id = '"+UUID+"';";
            statement.execute(updatePrestige);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getPrestige(String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT prestige FROM players WHERE player_id = '"+UUID+"';";

            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getInt("prestige");
        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    public void registerUser(String UUID){
        try {
            Statement statement = connection.createStatement();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();

            String playersQuery = "INSERT INTO players (player_id, balance, level, start_date, prestige) VALUES ('"+UUID+"', '0', '0', '"+format.format(date)+"', '0');";
            statement.execute(playersQuery);

            for(Resources resources : Resources.values()){
                String playerResourcesQuery = "INSERT INTO player_resources (player_id, resource, quantity) VALUES ('"+UUID+"', '"+resources.name()+"', '0');";
                statement.execute(playerResourcesQuery);
            }

            for(Type type : Type.values()){
                String playerItemsQuery = "INSERT INTO player_items (item_id, player_id, type, material) VALUES ('"+Item.hashItemId(UUID, type)+"', '"+UUID+"', '"+DatabaseUtils.getNameFromType(type)+"','"+DatabaseUtils.getNameFromMaterial(Material.WOOD)+"');"; // player_items
                statement.execute(playerItemsQuery);
            }
        }catch (SQLException e){
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
            String query = "UPDATE player_resources SET quantity = 0 WHERE player_id = '"+UUID+"' AND resource = '"+resource+"';";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void resetResourcesFromUUID(String UUID) {
        try {
            Statement statement = connection.createStatement();
            Map<Resources, Integer> resources = getResourcesFromUUID(UUID);

            for(Resources resource : resources.keySet()){
                String query = "UPDATE player_resources SET quantity = 0 WHERE player_id = '"+UUID+"' AND resource = '"+resource.name()+"';";
                statement.execute(query);
            }
        } catch (SQLException e) {
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
