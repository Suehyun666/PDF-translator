package project.translator;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class DeepLTranslator {

    // API 키 설정 (DeepL Pro의 API 키)
    private static final String API_KEY = "@@@";

    public static String translateText(String text, String targetLanguage) throws IOException {
        // DeepL API 엔드포인트 URL
        String urlString = "https://api-free.deepl.com/v2/translate";

        // 요청 파라미터 설정
        String params = "auth_key=" + API_KEY + "&text=" + text + "&target_lang=" + targetLanguage;

        // URL 연결 설정
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // 요청 본문 전송
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = params.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 응답 처리
        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        // JSON 응답 파싱
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONArray("translations").getJSONObject(0).getString("text");
    }

}
