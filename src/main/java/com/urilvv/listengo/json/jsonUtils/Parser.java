package com.urilvv.listengo.json.jsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.urilvv.listengo.json.jsonModels.AlbumIdModel;
import com.urilvv.listengo.json.jsonModels.ArtistIdModel;
import com.urilvv.listengo.json.jsonModels.SongIdModel;
import com.urilvv.listengo.models.SongJsonModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Parser {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static JsonNode parseJson(String src) throws JsonProcessingException {
        return objectMapper.readTree(src);
    }

    public static JSONArray getTracksJson(JsonNode jsonNode) {
        ArrayList<SongIdModel> songs = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            SongIdModel song = new SongIdModel.SongBuilder().builder()
                    .trackId(node.get("id").toString().replace("\"", ""))
                    .albumId(node.get("album").get("id").toString().replace("\"", ""))
                    .artistId(node.get("album").get("artists").findValue("id").toString().replace("\"", ""))
                    .build();

            songs.add(song);
        }

        JSONArray jsonArray = new JSONArray();

        for(SongIdModel song : songs){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("trackId", song.getTrackId());
            jsonObject.put("albumId", song.getAlbumId());
            jsonObject.put("artistId", song.getArtistId());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public static JSONArray getAlbumsJson(JsonNode jsonNode) {
        ArrayList<AlbumIdModel> albums = new ArrayList<>();

        for(JsonNode node : jsonNode){
            albums.add(new AlbumIdModel(node.get("id").toString().replace("\"", "")));
        }

        JSONArray jsonArray = new JSONArray();

        for(AlbumIdModel album : albums){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("albumId", album.getAlbumId());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public static JSONArray getArtistsJson(JsonNode jsonNode){
        ArrayList<ArtistIdModel> artists = new ArrayList<>();

        for(JsonNode node : jsonNode){
            artists.add(new ArtistIdModel(node.get("id").toString().replace("\"", "")));
        }

        JSONArray jsonArray = new JSONArray();

        for(ArtistIdModel artist : artists){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("artistId", artist.getArtistId());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    public static String toJson(Object song) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
        return ow.writeValueAsString(song);
    }
}
