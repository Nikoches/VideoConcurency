import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;


// Класс Магазин, хранящий произведенные товары
public class Store {
    ArrayList<Model> models = Loader.getModels();
    int modelsq = 0;

    public synchronized void get() {
        while (modelsq == models.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
        for (Model x : models) {
            try {
                String text2 = new BufferedReader(
                        new InputStreamReader(new URL(x.getTokenDataUrl()).openConnection().getInputStream(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
                JsonObject convertedObject = new Gson().fromJson(text2, JsonObject.class);
                x.setValue(convertedObject.get("value").getAsString());
                x.setTtl(convertedObject.get("ttl").getAsInt());
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        }
    }

    public synchronized void put() {
        notify();
    }
}

// Класс Потребитель
class Consumer implements Runnable {
    Store store;

    Consumer(Store store) {
        this.store = store;
    }

    public void run() {
        store.get();
    }
}
