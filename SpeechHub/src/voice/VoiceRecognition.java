package voice;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.*;



public class VoiceRecognition {
    private String ACCESS_TOKEN;
    private String uuid;

    private AccessToken token = new AccessToken();

    String attachmentName = "bitmap";
    String attachmentFileName = "bitmap.bmp";
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary =  "*****";


    public VoiceRecognition(){
        ACCESS_TOKEN = token.getAccessToken();

        uuid = UUID.randomUUID().toString();
    }

    private String recognitionRequest() throws Exception {
        File record = new File("RecordAudio.wav");

        String authToken = "Bearer " + ACCESS_TOKEN;

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_XML);
        mediaTypes.add(MediaType.APPLICATION_JSON);

        headers.setAccept(mediaTypes);

        headers.add(HttpHeaders.AUTHORIZATION, authToken);
        headers.add(HttpHeaders.HOST, "speech.platform.bing.com");
        headers.set(HttpHeaders.CONTENT_TYPE, "audio/wav;codec=\"audio/pcm\";samplerate=8000;trustsourcerate=false");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<byte[]> entity = new HttpEntity<>(IOUtils.toByteArray(new FileInputStream(record)), headers);

        String requestId = UUID.randomUUID().toString();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://speech.platform.bing.com/recognize/query")
                .queryParam("format", "json")
                .queryParam("appid", "D4D52672-91D7-4C74-8AD8-42B1D98141A5")
                .queryParam("locale", "en-US")
                .queryParam("device.os", "Linux")
                .queryParam("version", "3.0")
                .queryParam("maxnbest", "3")
                .queryParam("scenarios", "smd")
                .queryParam("instanceid", uuid)
                .queryParam("requestid", requestId);

        HashMap<String,String> dummy = new HashMap<>();

        ResponseEntity<HashMap<String,String>> conversion =
                restTemplate.exchange(builder.build().encode().toUri(),
                        HttpMethod.POST, entity, (Class<HashMap<String,String>>) dummy.getClass());

//        System.out.println(conversion.getBody().get("results"));
//        return conversion.getBody().get("results");
        ArrayList<String> result = (ArrayList<String>) (Object)conversion.getBody().get("results");
        LinkedHashMap<String,String> res = (LinkedHashMap<String, String>) (Object)result.get(0);
        System.out.println(res.toString());
        return res.get("lexical");

//        String url = "https://speech.platform.bing.com/recognize";
//        URL obj = new URL(url);
//        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//        StringBuilder result = new StringBuilder();
//
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Authorization","Bearer "+ACCESS_TOKEN);
//        con.setRequestProperty("Content-type","audio/wav; codec=\"audio/pcm\"; samplerate=16000");
//
//        con.setDoOutput(true);
//        con.setDoInput(true);
//        DataOutputStream request = new DataOutputStream(con.getOutputStream());
//
//        request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
//        request.writeBytes("scenarios=smd&appid=D4D52672-91D7-4C74-8AD8-42B1D98141A5&locale=en-US&device.os=Windows OS&version=3.0&format=json&instanceid="+uuid+"requestid="+uuid);
//        PrintWriter writer = new PrintWriter(new OutputStreamWriter(request,"UTF-8"));
//        byte[] audio = new byte[(int) record.length()];
//        InputStream is = new FileInputStream(record);
//        BufferedInputStream bif = new BufferedInputStream(is);
//        DataInputStream dis = new DataInputStream(bif);
//        int i = 0;
//        final int buffer_size = 4096;
//        try {
//
//            byte[] bytes = new byte[buffer_size];
//            int k=-1;
//            double prog=0;
//            while ((k = dis.read(bytes, 0, bytes.length)) > -1) {
//                if(k != -1) {
//                    request.write(bytes, 0, k);
//                    prog=prog+k;
//                    double progress = ((long) prog)/1000;///size;
//
//                }
//            }
//            request.flush();
//            dis.close();
//            request.close();
//        } catch (Exception ex) {
//            System.out.println("File to Network Stream Copy error "+ex);
//        }
//
//        con.connect();
//
//        int responseCode = con.getResponseCode();
//        System.out.println(responseCode);
//        System.out.println(con.getResponseMessage());
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        return response.toString();
    }

    public String getRecognitionResult(){
        try {
            String result = recognitionRequest();
            System.out.println(result);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args){
        VoiceRecognition vr = new VoiceRecognition();

            vr.getRecognitionResult();

    }

}
