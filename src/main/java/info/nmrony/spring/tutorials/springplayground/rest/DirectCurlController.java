package info.nmrony.spring.tutorials.springplayground.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@RestController
@RequestMapping("/api/tryout")
public class DirectCurlController {

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/curl")
    public String curlByProcess() throws NoSuchAlgorithmException {
        try {
            String url = "https://httpbin.org/post";
            ProcessBuilder pb = new ProcessBuilder("curl", "--silent", "--location",
                    "--request", "POST", url,
                    "--header",
                    "Content-Type:application/json",
                    "--data",
                    "{ \"color\" : \"Black\", \"type\" : \"BMW\" }");
            // errorstream of the process will be redirected to standard output
            pb.redirectErrorStream(true);
            // start the process
            Process proc = pb.start();
            /*
             * get the inputstream from the process which would get printed on
             * the console / terminal
             */
            InputStream ins = proc.getInputStream();
            // creating a buffered reader
            BufferedReader read = new BufferedReader(new InputStreamReader(ins));
            StringBuilder sb = new StringBuilder();
            read
                    .lines()
                    .forEach(line -> {
                        sb.append(line);
                    });
            // close the buffered reader
            read.close();
            /*
             * wait until process completes, this should be always after the
             * input_stream of processbuilder is read to avoid deadlock
             * situations
             */
            proc.waitFor();
            /*
             * exit code can be obtained only after process completes, 0
             * indicates a successful completion
             */
            int exitCode = proc.exitValue();
            System.out.println("exit: " + exitCode);
            // finally destroy the process
            proc.destroy();
            CurlResponse response = mapper.readValue(sb.toString(), CurlResponse.class);
            System.out.println("response## " + response.getJson());
            return sb.toString();
        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CurlResponse {
    Map<String, String> json;
}
