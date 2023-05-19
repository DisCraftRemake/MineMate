package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Items.Ressource.RessourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class DatabaseUtils {

    public static Item getPickaxeFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {

                JSONObject player = players.getJSONObject(UUID);

                JSONObject pickaxe = player.getJSONObject("pickaxe");

                Material material = RessourceUtils.getMaterialFromString(pickaxe.get("material").toString());
                Type type = RessourceUtils.getTypeFromString(pickaxe.get("type").toString());
                String id = pickaxe.get("id").toString();

                return new Item(material, type, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void SaveResourcesToUUID(String UUID, Map<Ores, Integer> ressourcesMap){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject ressources = player.getJSONObject("resources");

                /* User data */

                int Stone = ressources.getInt("stone");
                int Coal = ressources.getInt("coal");
                int Iron = ressources.getInt("iron");
                int Gold = ressources.getInt("gold");
                int Diamond = ressources.getInt("diamond");
                int Obsidian = ressources.getInt("obsidian");

                /* Current data */

                int StoneMap = ressourcesMap.get(Ores.STONE);
                int CoalMap = ressourcesMap.get(Ores.COAL);
                int IronMap = ressourcesMap.get(Ores.IRON);
                int GoldMap = ressourcesMap.get(Ores.GOLD);
                int DiamondMap = ressourcesMap.get(Ores.DIAMOND);
                int ObsidianMap = ressourcesMap.get(Ores.OBSIDIAN);

                /* Write data */
                ressources.remove("stone");
                ressources.remove("coal");
                ressources.remove("iron");
                ressources.remove("gold");
                ressources.remove("diamond");
                ressources.remove("obsidian");

                ressources.put("stone", Stone + StoneMap);
                ressources.put("coal", Coal + CoalMap);
                ressources.put("iron", Iron + IronMap);
                ressources.put("gold", Gold + GoldMap);
                ressources.put("diamond", Diamond + DiamondMap);
                ressources.put("obsidian", Obsidian + ObsidianMap);

                FileWriter writer = new FileWriter("src/main/resources/players.json" ,false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
    TODO:
        Methods :
            GetRessourcesFromUUID (List<int>, [STONE, COAL, IRON, GOLD, DIAMOND])
            SaveRessourcesToUUID
            ...

     */

}
