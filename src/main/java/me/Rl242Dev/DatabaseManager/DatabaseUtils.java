package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Crops;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Resources;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import me.Rl242Dev.DisCraft;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtils {

    private final String playerUrl = DisCraft.getInstance().getBaseURL() + "players.db";

    public static Item getPickaxeFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {

                JSONObject player = players.getJSONObject(UUID);

                JSONObject pickaxe = player.getJSONObject("pickaxe");

                Material material = ResourceUtils.getMaterialFromString(pickaxe.get("material").toString());
                Type type = ResourceUtils.getTypeFromString(pickaxe.get("type").toString());
                String id = pickaxe.get("id").toString();

                return new Item(material, type, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getResourceQuantityFromString(String UUID, String resource){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {

                JSONObject player = players.getJSONObject(UUID);

                JSONObject resources = player.getJSONObject("resources");
                int quantity = resources.getInt(resource.toLowerCase());
                return quantity;

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static Item getHoeFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {

                JSONObject player = players.getJSONObject(UUID);

                JSONObject pickaxe = player.getJSONObject("hoe");

                Material material = ResourceUtils.getMaterialFromString(pickaxe.get("material").toString());
                Type type = ResourceUtils.getTypeFromString(pickaxe.get("type").toString());
                String id = pickaxe.get("id").toString();

                return new Item(material, type, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<Resources, Integer> GetResourcesFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject resources = player.getJSONObject("resources");

                Map<Resources, Integer> oresIntegerMap = new HashMap<Resources, Integer>();

                int Stone = resources.getInt("stone");
                int Coal = resources.getInt("coal");
                int Iron = resources.getInt("iron");
                int Gold = resources.getInt("gold");
                int Diamond = resources.getInt("diamond");
                int Obsidian = resources.getInt("obsidian");

                int Wheat = resources.getInt("wheat");
                int Potato = resources.getInt("potato");
                int Carrot = resources.getInt("carrot");
                int SugarCane = resources.getInt("sugar_cane");

                oresIntegerMap.put(Resources.STONE, Stone);
                oresIntegerMap.put(Resources.COAL, Coal);
                oresIntegerMap.put(Resources.IRON, Iron);
                oresIntegerMap.put(Resources.GOLD, Gold);
                oresIntegerMap.put(Resources.DIAMOND, Diamond);
                oresIntegerMap.put(Resources.OBSIDIAN, Obsidian);

                oresIntegerMap.put(Resources.WHEAT, Wheat);
                oresIntegerMap.put(Resources.POTATO, Potato);
                oresIntegerMap.put(Resources.CARROT, Carrot);
                oresIntegerMap.put(Resources.SUGARCANE, SugarCane);

                return oresIntegerMap;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void SaveOresToUUID(String UUID, Map<Ores, Integer> ressourcesMap){
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

    public static void SaveBalanceToUUID(String UUID, int balance){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                int UserBalance = player.getInt("balance");

                /* Write data */
                player.remove("balance");

                player.put("balance", UserBalance + balance);

                FileWriter writer = new FileWriter("src/main/resources/players.json" ,false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void RemoveBalanceToUUID(String UUID, int balance){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                int UserBalance = player.getInt("balance");

                /* Write data */
                player.remove("balance");

                player.put("balance", UserBalance - balance);

                FileWriter writer = new FileWriter("src/main/resources/players.json" ,false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void SaveCropsToUUID(String UUID, Map<Crops, Integer> ressourcesMap){
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

    public static boolean UserExist(String UUID){
        try  {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                return true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException exception){
            return false;
        }

        return false;
    }

    public static void RegisterUser(String UUID){
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

    public static void saveLevelToUUID(String UUID, int level){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {
                JSONObject player = players.getJSONObject(UUID);

                player.remove("level");

                player.put("level", level);

                FileWriter writer = new FileWriter("src/main/resources/players.json");
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static int getLevelFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {
                JSONObject player = players.getJSONObject(UUID);

                return player.getInt("level");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void ResetResourceFromString(String UUID, String resource){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject resources = player.getJSONObject("resources");

                resources.remove(resource);

                resources.put(resource, 0);
                FileWriter writer = new FileWriter("src/main/resources/players.json", false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void ResetResourcesFromUUID(String UUID) {
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

        public static int GetBalanceFromUUID(String UUID) {
            try {
                String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

                JSONObject json = new JSONObject(jsonString);

                JSONObject players = json.getJSONObject("players");

                if (players.has(UUID)) {
                    JSONObject player = players.getJSONObject(UUID);

                    int UserBalance = player.getInt("balance");

                    return UserBalance;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
}
