package info.nmrony.spring.tutorials.springplayground.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import info.nmrony.spring.tutorials.springplayground.domain.httpclient.HttpRequester;
import lombok.Data;

/**
 * @author Nur Rony
 */
@RestController
@RequestMapping("/api/ssl-tester")
public class BadSslController {

    @GetMapping("self-signed")
    public EchoResponse get() throws Exception {
        // String url = "https://self-signed.badssl.com/";
        String url = "https://httpbin.org/get?working=fine";
        HttpRequester request = new HttpRequester(true);
        return request.getJson(url, EchoResponse.class, new HashMap<String, String>());
    }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class EchoResponse {
    Map<String, Object> args;
}
