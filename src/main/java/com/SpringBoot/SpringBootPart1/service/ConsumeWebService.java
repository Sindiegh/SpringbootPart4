package com.SpringBoot.SpringBootPart1.service;

import com.SpringBoot.SpringBootPart1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class ConsumeWebService {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/v3")
    public String getGitV3ApiVersion() {
        String url = "https://api.github.com";
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class);
        return Collections.singletonList(responseEntity).toString();
    }

    @RequestMapping(value = "/users/Sindiegh/repos")
    public String ListGitRepos() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("https://api.github.com/users/Sindiegh/repos",
                HttpMethod.GET, entity, String.class).getBody();
    }

    @RequestMapping(value = "/users/Sindiegh/repos")
    public String ListCommitsOfOneRepo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("https://api.github.com/repos/Sindiegh/Dice/commits",
                HttpMethod.GET, entity, String.class).getBody();
    }

}
