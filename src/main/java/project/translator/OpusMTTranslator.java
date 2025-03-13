package project.translator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class OpusMTTranslator {

    private static final String API_URL = "http://localhost:5000/translate"; // OpusMT 서버의 API URL

    public static String translateText(String text) throws IOException {
        // 요청 파라미터 설정
        JSONObject requestBody = new JSONObject();
        requestBody.put("src_lang", "en"); // 원본 언어 (영어)
        requestBody.put("tgt_lang", "ko"); // 대상 언어 (한국어)
        requestBody.put("text", text); // 번역할 텍스트

        // URL 연결 설정
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        // 요청 본문 전송
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 응답 처리
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to translate text: " + responseCode);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        // JSON 응답 파싱
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getString("translatedText");
    }
}
