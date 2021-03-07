package com.expertrec.assignment;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ResponseHandler {
    private String url;

    ResponseHandler(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    HttpResponse callURL() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(getUrl());
        return client.execute(request);
    }

    int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    StringBuilder getResponseBody(HttpResponse response) throws IOException {
        String line = null;
        StringBuilder  responseString = new StringBuilder ();
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = in.readLine()) != null) {
            String decoded = new String(line.getBytes(), StandardCharsets.UTF_8);
            responseString.append("\n").append(decoded);
        }
        return responseString;
    }

    HashMap<String, String> getHeaders(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        HashMap<String, String> headerList = new HashMap<>();
        for (Header header : headers) {
            //System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
            headerList.put(header.getName(), header.getValue());
        }
        return headerList;
    }

    String getTitle(StringBuilder  tmp) {
        Document doc = Jsoup.parse(String.valueOf(tmp));
        return doc.title();
    }

}
