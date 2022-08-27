package info.nmrony.spring.tutorials.ssltest.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import info.nmrony.spring.tutorials.ssltest.domain.HttpRequester;

/**
 * @author Nur Rony
 */
@RestController
@RequestMapping("/api/ssl-tester")
public class BadSslController {

    @GetMapping("self-signed")
    public EchoResponse slefSigned() throws Exception {
        String url = "https://self-signed.badssl.com/";
        HttpRequester request = new HttpRequester(false);
        return request.getJson(url, EchoResponse.class, new HashMap<String, String>());
    }

    @GetMapping("validssl")
    public EchoResponse validssl() throws Exception {
        String url = "https://httpbin.org/get?working=fine";
        HttpRequester request = new HttpRequester(false);
        return request.getJson(url, EchoResponse.class, new HashMap<String, String>());
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class EchoResponse {
    private Map<String, Object> args;

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "EchoResponse [args=" + getArgs() + "]";
    }
}
