package ServiceTest;

import Request.LoadRequest;
import Service.LoadService;

public class LoadServiceTest {
    LoadRequest request;
    LoadService service;

    /*
    try {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
        LoadRequest loadRequest = gson.fromJson(jsonReader, LoadRequest.class);

        proxy.load(host, port, loadRequest);
    } catch (ServerConnectionException e) {
        fail(e.getMessage());
    } catch(FileNotFoundException e) {
        Assertions.fail("passoffFiles/LoadData.json not found in project root directory");
    }

     */
}
