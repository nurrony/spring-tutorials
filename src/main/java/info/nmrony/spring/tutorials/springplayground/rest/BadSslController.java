package info.nmrony.spring.tutorials.springplayground.rest;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.nmrony.spring.tutorials.springplayground.domain.httpclient.HttpRequester;
import lombok.Data;

@RestController
@RequestMapping("/api/ssl-tester")
public class BadSslController {

    @GetMapping("selfsigned")
    public void get() throws Exception {
        String selfsigned = "https://self-signed.badssl.com/";
        // String validSsl = "https://reqbin.com/echo/get/json";

        HttpRequester request = new HttpRequester(true);
        var json = request.getJson(selfsigned, EchoResponse.class, new HashMap<String, String>());
        System.out.println("test " + json);
    }
}

@Data
class EchoResponse {
    String success;
}
