import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;


public class Loader {

    private static final ArrayList<Model> models = new ArrayList<>();
    public static ForkJoinPool commonPool = ForkJoinPool.commonPool();

    public static void main(String[] args) {
        Loader loader = new Loader();
        loader.connect();
        commonPool.invoke(new CustomRecursiveTask(models));
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
            in.close();

        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}
