package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.RessourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class DatabaseUtils {

    public static final String playersPath = "src/main/resources/players.json";

    public static Item getPickaxeFromUUID(int UUID) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(new FileReader(playersPath));

        for (Object player : array){

            int uuid = (int) ((JSONObject) player).get("uuid");
            if(uuid == UUID){
                JSONArray pickaxe = (JSONArray) ((JSONObject) player).get("pickaxe");

                for (Object attributes : pickaxe){

                    String displayName = (String) ((JSONObject) attributes).get("name");
                    String type = (String) ((JSONObject) attributes).get("material");
                    String id = (String) ((JSONObject) attributes).get("id");

                    Material material = RessourceUtils.getTypeFromString(type);

                    return new Item(displayName, material, Type.PICKAXE,  id);
                }
            }
        }
        return null;
    }

    /*
    TODO:
        Methods :
            GetRessourcesFromUUID (List<int>, [STONE, COAL, IRON, GOLD, DIAMOND])
            SaveRessourcesToUUID
            ...

     */

}
