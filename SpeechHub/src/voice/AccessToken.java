package voice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class AccessToken {

    public static void main(String[] args) throws Exception {

    }

    private String generateAccessToken() throws Exception {
        String url = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        StringBuilder result = new StringBuilder();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", "0");
        con.setRequestProperty("Ocp-Apim-Subscription-Key", "13f4255601f04c9a993aa12492acb05c");
        con.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("");
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println(responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        if (responseCode == 200){
            return response.toString();
        } else {
            return null;
        }
    }

    public String getAccessToken() {
        try {
            return generateAccessToken();
        } catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
}
