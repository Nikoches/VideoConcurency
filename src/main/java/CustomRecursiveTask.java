import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

public class CustomRecursiveTask extends RecursiveAction {
    private static final int THRESHOLD = 1;
    private final List<Model> modelList;

    public CustomRecursiveTask(List<Model> list) {
        this.modelList = list;
    }

    @Override
    protected void compute() {
        if (modelList.size() > THRESHOLD) {
             ForkJoinTask.invokeAll(createSubtasks());
        } else {
             processing(modelList);
        }
    }

    private Collection<CustomRecursiveTask> createSubtasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(modelList.subList(0,modelList.size()/2)));
        dividedTasks.add(new CustomRecursiveTask(modelList.subList(modelList.size()/2,modelList.size())));
        return dividedTasks;
    }
    private void processing(List<Model> model){
        Model x = model.get(0);
        try {
            String text2 = new BufferedReader(
                    new InputStreamReader(new URL(x.getTokenDataUrl()).openConnection().getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JsonObject convertedObject = new Gson().fromJson(text2, JsonObject.class);
            x.setValue(convertedObject.get("value").getAsString());
            x.setTtl(convertedObject.get("ttl").getAsInt());
            String text1 = new BufferedReader(
                    new InputStreamReader(new URL(x.getSourceDataUrl()).openConnection().getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JsonObject convertedObject2 = new Gson().fromJson(text1, JsonObject.class);
            x.setType(convertedObject2.get("urlType").getAsString());
            x.setVideoUrl(convertedObject2.get("videoUrl").getAsString());
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
}
