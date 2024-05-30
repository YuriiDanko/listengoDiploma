package com.urilvv.listengo.json.jsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.urilvv.listengo.json.jsonModels.AlbumIdModel;
import com.urilvv.listengo.json.jsonModels.ArtistIdModel;
import com.urilvv.listengo.json.jsonModels.SongIdModel.SongBuilder;
import com.urilvv.listengo.json.jsonModels.SongIdModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class Parser {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static JsonNode parseJson(String src) throws JsonProcessingException {
        return objectMapper.readTree(src);
    }

    public static String toJson(Object song) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
        return ow.writeValueAsString(song);
    }

    public static String getAlbumTracksJson(JsonNode jsonNode, String albumId) throws JsonProcessingException {
        ArrayList<SongIdModel> songs = new ArrayList<>();

        for(JsonNode node : jsonNode){
            SongIdModel song = new SongBuilder().builder()
                    .trackId(node.get("id").toString().replace("\"", ""))
                    .albumId(albumId)
                    .artistId(node.get("artists").findValue("id").toString().replace("\"", ""))
                    .playlists(new HashSet<>())
                    .build();

            songs.add(song);
        }

        return Parser.toJson(songs);
    }

    public static JSONArray getTracksJson(JsonNode jsonNode) throws JsonProcessingException {
        ArrayList<SongIdModel> songs = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            SongIdModel song = new SongBuilder().builder()
                    .trackId(node.get("id").toString().replace("\"", ""))
                    .albumId(node.get("album").get("id").toString().replace("\"", ""))
                    .artistId(node.get("album").get("artists").findValue("id").toString().replace("\"", ""))
                    .playlists(new HashSet<>())
                    .build();

            songs.add(song);
        }

        return new JSONArray(Parser.toJson(songs));
    }

    public static JSONArray getAlbumsJson(JsonNode jsonNode) throws JsonProcessingException {
        ArrayList<AlbumIdModel> albums = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            albums.add(new AlbumIdModel(node.get("id").toString().replace("\"", "")));
        }

        return new JSONArray(Parser.toJson(albums));
    }

    public static JSONArray getArtistsJson(JsonNode jsonNode) throws JsonProcessingException {
        ArrayList<ArtistIdModel> artists = new ArrayList<>();

        for (JsonNode node : jsonNode) {
            artists.add(new ArtistIdModel(node.get("id").toString().replace("\"", "")));
        }

        return new JSONArray(Parser.toJson(artists));
    }

}
