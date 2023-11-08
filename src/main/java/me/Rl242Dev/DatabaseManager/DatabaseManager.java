package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Clans.Clan;
import me.Rl242Dev.Classes.Entity.Pets.*;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Crops;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Resources;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/*

@A = Rl242Dev
@U = Database
@E = Class for the DatabaseManager, used to query data

 */

public class DatabaseManager {

    private static Connection connection;

    /**
     * Connect to the database
     */
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

    /**
     * Remove pet from user
     * 
     * @param UUID
     * 
     * @return void
     * 
     */
    public void removePet(String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE player_pets SET pet = 'null' WHERE player_id = '"+UUID+"';";

            statement.execute(query);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the Members of a clan by clan ID
     * 
     * @param ID
     * @return Map<Player, Integer> | null
     * 
     */
    public Map<Player, Integer> getMembersOfClan(String ID){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT player_id, owner FROM player_clans WHERE clan_id = '"+ID+"';";

            ResultSet resultSet = statement.executeQuery(query);

            Map<Player, Integer> members = new HashMap<>();
            while (resultSet.next()){
                Player player = new Player(resultSet.getString("player_id"));
                Integer owner = resultSet.getInt("owner");

                members.put(player, owner);
            }
            return members;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Kicks player out of Clan, can only be used by owner of the clan
     * 
     * @param ID
     * @param UUID
     * 
     * @return void
     */
    public void kickPlayerOfClan(String ID, String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE player_id, clan_id, owner FROM player_clans WHERE clan_id = '"+ID+"' AND player_id = '"+UUID+"';";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * get Clan from member UUID
     * 
     * @param UUID
     * @return Clan | null
     */
    public Clan getClanFromMember(String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT clan_id FROM player_clans WHERE player_id = '"+UUID+"';";

            ResultSet resultSet = statement.executeQuery(query);
            if(!resultSet.next()){
                return null;
            }
            return new Clan(resultSet.getString("clan_id"));
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add clan owner to a Clan, PRIMARY KEY
     * 
     * @param ID
     * @param UUID
     * 
     * @return void
     */
    public void addClanOwner(String ID, String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO player_clans (player_id, clan_id, owner) VALUES ('"+UUID+"', '"+ID+"', '1');";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Add clan member to clan
     * 
     * @param ID
     * @param UUID
     */
    public void addClanMember(String ID, String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO player_clans (player_id, clan_id, owner) VALUES ('"+UUID+"', '"+ID+"', '0')";

            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Query the Item Pickaxe from a user
     * 
     * @param UUID
     * @return Item | null
     */
    public Item getPickaxeFromUUID(String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM player_items WHERE player_id = '" + UUID + "' AND type = 'Pickaxe';";

            ResultSet results = statement.executeQuery(query);

            return new Item(
                    ResourceUtils.getMaterialFromString(results.getString("material")),
                    ResourceUtils.getTypeFromString(results.getString("type")),
                    results.getString("item_id"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all clans in the database
     * 
     * @return List<Clan> | null
     */
    public List<Clan> getClans(){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT clan_id FROM player_clans;";

            List<Clan> clans = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                clans.add(new Clan(resultSet.getString("clan_id")));
            }

            return clans;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Query the amount of a resource of a user from the database
     * 
     * @param UUID
     * @param resource
     * @return int | 0
     */
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

    /**
     * Query pet of a player from database
     * 
     * @param UUID
     * @return Class<? extends PetIdentufier> | null
     */
    public Class<? extends PetIdentifier> getPetFromUUID(String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT pet FROM player_pets WHERE player_id = '"+UUID+"';";

            ResultSet resultSet = statement.executeQuery(query);

            return switch (resultSet.getString("pet")){
                case "bee" -> Bee.class;
                case "cat" -> Cat.class;
                case "goat" -> Goat.class;
                default -> null;
            };
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Query Item Hoe of a player from database
     * 
     * @param UUID
     * @return Item | null
     */
    public Item getHoeFromUUID(String UUID){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM player_items WHERE player_id = '" + UUID + "' AND type = 'Hoe';";

            ResultSet results = statement.executeQuery(query);

            return new Item(
                    ResourceUtils.getMaterialFromString(results.getString("material")),
                    ResourceUtils.getTypeFromString(results.getString("type")),
                    results.getString("item_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Query all resources of a player from database
     * 
     * @param UUID
     * @return Map<Resources, Integer> | null
     */
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

        if (resources.isEmpty()) {
            return null;
        } else {
            return resources;
        }
    }

    /**
     * Save ores to database for a player
     * 
     * @param UUID
     * @param ressourcesMap
     * 
     * @return void
     */
    public void saveOresToUUID(String UUID, Map<Ores, Integer> ressourcesMap){
        for(Ores ore : ressourcesMap.keySet()){
            try {
                Statement statement = connection.createStatement();
                String queryResource = "SELECT quantity FROM player_resources WHERE player_id = '"+UUID+"' AND resource = '"+ore.name()+"';";

                ResultSet resourceResult = statement.executeQuery(queryResource);

                int total = resourceResult.getInt("quantity") + ressourcesMap.get(ore);
                String query = "UPDATE player_resources SET quantity = '"+total+"' WHERE player_id = '"+UUID+"' AND resource = '"+ore.name()+"';";
                statement.execute(query);

                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds amount to balance for user in database
     * 
     * @param UUID
     * @param amout
     * 
     * @return void
     */
    public void addToBalanceFromUUID(String UUID, int amout){
        try {
            int balance = getBalanceFromUUID(UUID);

            int totalBalance = balance + amout;
            Statement statement = connection.createStatement();
            String query = "UPDATE players SET balance = '"+totalBalance+"' WHERE player_id = '"+UUID+"';";

            statement.execute(query);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Removes amount from balance of user in database
     * 
     * @param UUID
     * @param amout
     * 
     * @return void
     */
    public void removeAmountFromUUID(String UUID, int amout){
        try {
            int balance = getBalanceFromUUID(UUID);
            int totalBalance = balance - amout;

            Statement statement = connection.createStatement();
            String query = "UPDATE players SET balance = '"+totalBalance+"' WHERE player_id = '"+UUID+"';";

            statement.execute(query);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Query top 10 balance/user by asc order 
     * 
     * @return Map<String, Integer> | null
     */
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

    /**
     * Saves crops to database for a player
     * 
     * @param UUID
     * @param ressourcesMap
     * 
     * @return void
     */
    public void saveCropsToUUID(String UUID, Map<Crops, Integer> ressourcesMap){
        for(Crops crop : ressourcesMap.keySet()){
            try {
                Statement statement = connection.createStatement();
                String queryResource = "SELECT quantity FROM player_resources WHERE player_id = '"+UUID+"' AND resource = '"+crop.name()+"';";

                ResultSet resourceResult = statement.executeQuery(queryResource);

                int total = resourceResult.getInt("quantity") + ressourcesMap.get(crop);
                String query = "UPDATE player_resources SET quantity = '"+total+"' WHERE player_id = '"+UUID+"' AND resource = '"+crop.name()+"';";
                statement.execute(query);

                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if an user exists 
     * 
     * @param UUID
     * @return bool
     */
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

    /**
     * Update item (Hoe, Pickaxe, ...) for a player 
     * 
     * @param UUID
     * @param item
     * 
     * @return void
     */
    public void updateItem(String UUID, Item item){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE player_items SET material = '"+item.getMaterial().toString()+"' WHERE player_id = '"+UUID+"' AND type = '"+DatabaseUtils.getNameFromType(item.getType())+"';";

            statement.execute(query);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Add +1 prestige to user and reset every items, resources
     * 
     * @param UUID
     * 
     * @return void
     */
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

            String updatePrestige = "UPDATE players SET prestige = '"+Utils.IntToString(getPrestige(UUID)+1)+"' WHERE player_id = '"+UUID+"';";
            statement.execute(updatePrestige);

            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Query the prestige level of an user from database
     * 
     * @param UUID
     * @return int | 0
     */
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

    /**
     * Update pet from player to database
     * 
     * @param UUID
     * @param pets
     * 
     * @return void
     */
    public void updatePet(String UUID, Pets pets){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE player_pets SET pet = '"+pets.toString().toLowerCase()+"' WHERE player_id = '"+UUID+"';";

            statement.execute(query);

            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Add an user to the database (UUID, start_date, ...)
     * 
     * @param UUID
     * 
     * @return void 
     */
    public void registerUser(String UUID){
        try {
            Statement statement = connection.createStatement();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();

            String playersQuery = "INSERT INTO players (player_id, balance, level, start_date, prestige) VALUES ('"+UUID+"', '0', '0', '"+format.format(date)+"', '0');";
            statement.execute(playersQuery);

            String petQuery = "INSERT INTO player_pets (player_id, pet) VALUES ('"+UUID+"', 'null')";
            statement.execute(petQuery);

            for(Resources resources : Resources.values()){
                String playerResourcesQuery = "INSERT INTO player_resources (player_id, resource, quantity) VALUES ('"+UUID+"', '"+resources.name()+"', '0');";
                statement.execute(playerResourcesQuery);
            }

            for(Type type : Type.values()){
                String playerItemsQuery = "INSERT INTO player_items (item_id, player_id, type, material) VALUES ('"+Item.hashItemId(UUID, type)+"', '"+UUID+"', '"+DatabaseUtils.getNameFromType(type)+"','"+DatabaseUtils.getNameFromMaterial(Material.WOOD)+"');"; // player_items
                statement.execute(playerItemsQuery);
            }

            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Update the level of a player
     * 
     * @param UUID
     * @param level
     * 
     * @return void
     */
    public void updateLevelToUUID(String UUID, int level){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE players SET level = '"+level+"' WHERE player_id = '"+UUID+"';";

            statement.execute(query);

            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Query the level of a player
     * 
     * @param UUID
     * @return int | 0
     */
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

    /**
     * Reset only one resource from a player
     * 
     * @param UUID
     * @param resource
     * 
     * @return void
     */
    public void resetResourceFromString(String UUID, String resource){
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE player_resources SET quantity = 0 WHERE player_id = '"+UUID+"' AND resource = '"+resource+"';";

            statement.execute(query);

            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Reset every resources of an user
     * 
     * @param UUID
     * 
     * @return void
     */
    public void resetResourcesFromUUID(String UUID) {
        try {
            Statement statement = connection.createStatement();
            Map<Resources, Integer> resources = getResourcesFromUUID(UUID);

            for(Resources resource : resources.keySet()){
                String query = "UPDATE player_resources SET quantity = 0 WHERE player_id = '"+UUID+"' AND resource = '"+resource.name()+"';";
                statement.execute(query);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query balance of an user from database
     * 
     * @param UUID
     * 
     * @return int | 0
     */
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
