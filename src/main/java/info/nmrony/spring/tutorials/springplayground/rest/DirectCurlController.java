package info.nmrony.spring.tutorials.springplayground.rest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping("/api/tryout")
public class DirectCurlController {

    // private ObjectMapper mapper = new ObjectMapper();

    private String getPasswordDigest(String appKey, String appSecret) throws NoSuchAlgorithmException {
        Random rand = new Random();
        var timespan = DateTimeFormatter.ISO_INSTANT.format(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        var dateWithoutZ = timespan.replace("Z", "");
        var dateTimeArray = dateWithoutZ.split("T");
        var dateArray = dateTimeArray[0].split("-");
        var timeArray = dateTimeArray[1].split(":");
        var noncetime = dateArray[0] + dateArray[1] + timeArray[0] + timeArray[1] + timeArray[2]
                + rand.nextInt(59) * 1000;
        var nonveArray = noncetime.getBytes(StandardCharsets.UTF_8);
        var nonce = Base64.getEncoder().encodeToString(nonveArray);
        var rawStr = nonce + timespan + appSecret;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawStr.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        String wss = "UsernameToken Username='" + appKey + "', PasswordDigest='" + encoded + "', Nonce='" + nonce
                + "', Created='" + timespan + "'";
        return wss;
    }

    @GetMapping("/curl")
    public String curlByProcess() throws NoSuchAlgorithmException {
        return getPasswordDigest("935903a1-386d-4780-9b7b-e2e344d583b1", "52c34785d212a12b168df7a415a90247");
        // try {
        // String url = "https://httpbin.org/post";
        // ProcessBuilder pb = new ProcessBuilder("curl", "--silent", "--location",
        // "--request", "POST", url,
        // "--header",
        // "Content-Type:application/json",
        // "--data",
        // "{ \"color\" : \"Black\", \"type\" : \"BMW\" }");
        // // errorstream of the process will be redirected to standard output
        // pb.redirectErrorStream(true);
        // // start the process
        // Process proc = pb.start();
        // /*
        // * get the inputstream from the process which would get printed on
        // * the console / terminal
        // */
        // InputStream ins = proc.getInputStream();
        // // creating a buffered reader
        // BufferedReader read = new BufferedReader(new InputStreamReader(ins));
        // StringBuilder sb = new StringBuilder();
        // read
        // .lines()
        // .forEach(line -> {
        // sb.append(line);
        // });
        // // close the buffered reader
        // read.close();
        // /*
        // * wait until process completes, this should be always after the
        // * input_stream of processbuilder is read to avoid deadlock
        // * situations
        // */
        // proc.waitFor();
        // /*
        // * exit code can be obtained only after process completes, 0
        // * indicates a successful completion
        // */
        // int exitCode = proc.exitValue();
        // System.out.println("exit: " + exitCode);
        // // finally destroy the process
        // proc.destroy();
        // CurlResponse response = mapper.readValue(sb.toString(), CurlResponse.class);
        // System.out.println("response## " + response.getJson());
        // return sb.toString();
        // } catch (UnsupportedOperationException | IOException e) {
        // e.printStackTrace();
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // return null;
    }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CurlResponse {
    Map<String, String> json;
}
