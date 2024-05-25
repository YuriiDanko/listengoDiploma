package com.urilvv.listengo.json.jsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.urilvv.listengo.models.jsonModels.AlbumJsonModel;
import com.urilvv.listengo.models.jsonModels.ArtistJsonModel;
import com.urilvv.listengo.models.jsonModels.SongJsonModel;
import com.urilvv.listengo.models.jsonModels.SongJsonModel.SongJsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonInformator {

    public static JSONArray getTracksInfo(JsonNode jsonNode) throws JsonProcessingException {
        JSONArray resultArray = new JSONArray();

        if(jsonNode.findValue("tracks") != null){
            for(JsonNode node : jsonNode.get("tracks")){
                getTrackJsonInfo(node, resultArray);
            }
        } else {
            getTrackJsonInfo(jsonNode, resultArray);
        }

        return resultArray;
    }

    private static void getTrackJsonInfo(JsonNode jsonNode, JSONArray resultArray) throws JsonProcessingException {
        JSONObject imageObject = new JSONObject(jsonNode.get("album").toString());
        JSONArray jsonArray = imageObject.getJSONArray("images");
        imageObject = (JSONObject) jsonArray.get(0);

        JsonNode artistNode = Parser.parseJson(jsonNode.get("album").get("artists").get(0).toString());

        SongJsonModel song = SongJsonBuilder.builder()
                .trackId(jsonNode.get("id").toString().replace("\"", ""))
                .trackName(jsonNode.get("name").toString().replace("\"", ""))
                .durationMs(jsonNode.get("duration_ms").toString().replace("\"", ""))
                .imageUrl(imageObject.get("url").toString().replace("\"", ""))
                .artist(new ArtistJsonModel(artistNode.get("id").toString().replace("\"", ""),
                        artistNode.get("name").toString().replace("\"", ""),
                        ""))
                .album(new AlbumJsonModel(jsonNode.get("album").get("id").toString().replace("\"", ""),
                        jsonNode.get("album").get("name").toString().replace("\"", ""),
                        ""))
                .build();

        JSONObject jsonObject = new JSONObject(Parser.toJson(song));
        resultArray.put(jsonObject);
    }

    public static JSONArray getArtistsInfo(JsonNode jsonNode) throws JsonProcessingException {
        JSONArray resultArray = new JSONArray();

        System.out.println(jsonNode.toPrettyString());

        if(jsonNode.findValue("artists") != null){
            for(JsonNode node : jsonNode.get("artists")){
                getArtistsJsonInfo(node, resultArray);
            }
        } else {
            getArtistsJsonInfo(jsonNode, resultArray);
        }

        return resultArray;
    }

    private static void getArtistsJsonInfo(JsonNode node, JSONArray resultArray) throws JsonProcessingException {
        JSONObject imageObject = new JSONObject(node.toString());
        JSONArray jsonArray = imageObject.getJSONArray("images");
        imageObject = (JSONObject) jsonArray.get(0);

        ArtistJsonModel artist = new ArtistJsonModel(node.get("id").toString().replace("\"", ""),
                node.get("name").toString().replace("\"", ""),
                imageObject.get("url").toString().replace("\"", ""));

        JSONObject jsonObject = new JSONObject(Parser.toJson(artist));
        resultArray.put(jsonObject);
    }

    public static JSONArray getAlbumsInfo(JsonNode jsonNode) throws JsonProcessingException {
        JSONArray resultArray = new JSONArray();

        if(jsonNode.findValue("albums") != null){
            for(JsonNode node : jsonNode.get("albums")){
                getAlbumJsonInfo(node, resultArray);
            }
        } else {
            getAlbumJsonInfo(jsonNode, resultArray);
        }

        return resultArray;
    }

    private static void getAlbumJsonInfo(JsonNode node, JSONArray resultArray) throws JsonProcessingException {
        JSONObject imageObject = new JSONObject(node.toString());
        JSONArray jsonArray = imageObject.getJSONArray("images");
        imageObject = (JSONObject) jsonArray.get(0);

        AlbumJsonModel album = new AlbumJsonModel(node.get("id").toString().replace("\"", ""),
                node.get("name").toString().replace("\"", ""),
                imageObject.get("url").toString().replace("\"", ""));

        JSONObject jsonObject = new JSONObject(Parser.toJson(album));
        resultArray.put(jsonObject);
    }

}
