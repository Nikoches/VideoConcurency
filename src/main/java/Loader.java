import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class Loader {

    private static final ArrayList<Model> models = new ArrayList<>();
    private static Store store;

    public static void main(String[] args) {
        store = new Store();
        Thread sec = new Thread( new Consumer(store));
        new Loader().connect();
        sec.start();
    }

    public static ArrayList<Model> getModels() {
        return models;
    }

    private void connect() {
        try {
            URL url = new URL("http://www.mocky.io/v2/5c51b9dd3400003252129fb5");
            URLConnection yc = url.openConnection();
            Gson gson = new GsonBuilder().create();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuilder ss = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                ss.append(inputLine).append("\n");
            }
            Collections.addAll(models, gson.fromJson(ss.toString(), Model[].class));
            store.put();
            for (Model x : models) {
                String text1 = new BufferedReader(
                        new InputStreamReader(new URL(x.getSourceDataUrl()).openConnection().getInputStream(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
                JsonObject convertedObject = new Gson().fromJson(text1, JsonObject.class);
                x.setType(convertedObject.get("urlType").getAsString());
                x.setVideoUrl(convertedObject.get("videoUrl").getAsString());
            }
            in.close();

        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}
