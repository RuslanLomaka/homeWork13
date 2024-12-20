import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;


public class JsonPlaceholderInteraction {

    private static final String TEST_URL_USERS =
            "https://jsonplaceholder.typicode.com/users";

    private static final String TEST_URL_TODOS =
            "https://jsonplaceholder.typicode.com/users/1/todos";

    private static final String TEST_URL_POSTS =
            "https://jsonplaceholder.typicode.com/users/1/posts";

    static void createNewObjectSendPOST() throws IOException { //створення нового об'єкта
        URL url = new URL(TEST_URL_USERS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();

        os.write(Files.readAllBytes(new File("user.json").toPath()));
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        System.out.println("POST response code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            response = new StringBuilder(response.toString().replaceAll("\\\\n", "\n").replaceAll("\\\\", ""));

            System.out.println(response);

        } else {
            System.out.println("POST request not worked");
        }
    }



    static void sendGETallUsersInfo() throws IOException {
        URL url = new URL(TEST_URL_USERS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonElement jsonElement = JsonParser.parseString(response.toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(jsonElement);
            System.out.println(prettyJson);
        } else {
            System.out.println("GET request not worked");
        }
    }


    static void sendGETRrefreshUserInfo() throws IOException {
        URL url = new URL(TEST_URL_USERS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");



        OutputStream os = connection.getOutputStream();
        os.write(Files.readAllBytes(new File("user.json").toPath()));
        os.flush();
        os.close();
        int responseCode = connection.getResponseCode();


        System.out.println("GET response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonElement jsonElement = JsonParser.parseString(response.toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(jsonElement);
            System.out.println(prettyJson);
        } else {
            System.out.println("GET request not worked");
        }
    }

}

class JsonPlaceholderInteractionSandBox {

    public static void main(String[] args) throws IOException {
        //JsonPlaceholderInteraction.createNewObjectSendPOST();//  1. створення нового об'єкта
        JsonPlaceholderInteraction.sendGETRrefreshUserInfo();// 2. оновлення об'єкту

        //JsonPlaceholderInteraction.sendGETallUsersInfo();// 4.отримання інформації про всіх користувачів
    }
}



