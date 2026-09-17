package com.sneaksapp.knowledgebuilder.openai;

import com.sneaksapp.knowledgebuilder.openai.request.OpenAiRequest;
import com.sneaksapp.knowledgebuilder.openai.response.OpenAiResponse;
import java.net.HttpURLConnection;
import java.util.*;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final RestClient restClient;

    public OpenAiService(RestClient.Builder builder) {

        this.restClient = builder
                .baseUrl("https://api.openai.com/v1")
                .build();

    }

    private void testImageUrl(String imageUrl) {

        try {

            URL url = new URL(imageUrl);

            HttpURLConnection connection
                    = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0"
            );

            int status = connection.getResponseCode();

            System.out.println("=== IMAGE TEST ===");
            System.out.println("URL = " + imageUrl);
            System.out.println("STATUS = " + status);
            System.out.println(
                    "CONTENT TYPE = "
                    + connection.getContentType()
            );
            System.out.println(
                    "CONTENT LENGTH = "
                    + connection.getContentLengthLong()
            );

            connection.disconnect();

        } catch (Exception e) {

            System.out.println("=== IMAGE TEST FAILED ===");
            System.out.println("URL = " + imageUrl);
            System.out.println("ERROR = " + e.getMessage());
        }
    }

    public OpenAiResponse sendPrompt(String prompt, String imageUrl) {

    try {

        System.out.println("=== OPENAI SEND PROMPT ===");
        System.out.println("IMAGE URL = " + imageUrl);

        if (imageUrl != null && !imageUrl.isBlank()) {
            testImageUrl(imageUrl);
        }

        Map<String, Object> textContent = new HashMap<>();

        textContent.put("type", "input_text");
        textContent.put("text", prompt);

        List<Map<String, Object>> content = new ArrayList<>();

        content.add(textContent);


        if (imageUrl != null && !imageUrl.isBlank()) {

            System.out.println("OPENAI IMAGE URL = " + imageUrl);

            Map<String, Object> imageContent = new HashMap<>();

            imageContent.put("type", "input_image");
            imageContent.put("image_url", imageUrl);

            content.add(imageContent);
        }

        Map<String, Object> message = new HashMap<>();

        message.put("role", "user");
        message.put("content", content);

        List<Map<String, Object>> input = new ArrayList<>();

        input.add(message);

        OpenAiRequest request = new OpenAiRequest(
                model,
                input
        );

        System.out.println(
                "API KEY VAR MI: "
                + (apiKey != null && !apiKey.isBlank())
        );

        System.out.println(
                "=== OPENAI REQUEST GÖNDERİLİYOR ==="
        );

        System.out.println("MODEL = " + model);
        System.out.println("IMAGE URL = " + imageUrl);

        return restClient
                .post()
                .uri("/responses")
                .header(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .header(
                        "Content-Type",
                        "application/json"
                )
                .body(request)
                .retrieve()
                .body(OpenAiResponse.class);

    } catch (Exception e) {

        System.out.println("=== OPENAI REQUEST HATASI ===");
        System.out.println("IMAGE URL = " + imageUrl);
        System.out.println("ERROR = " + e.getMessage());

        e.printStackTrace();

        throw new RuntimeException(
                "OpenAI request failed.",
                e
        );
    }
}
    public void testOpenAiConnection() {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/models"))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            HttpResponse<String> response
                    = client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println("OPENAI STATUS: " + response.statusCode());
            System.out.println("OPENAI RESPONSE: " + response.body());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
