package com.cicd.subway.controller;

import com.cicd.subway.dto.SubwayDTO;
import com.cicd.subway.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.ParseException;

@Slf4j
@RestController
public class JsonSubway {

    @RequestMapping("/find")
    public Map<String, Object> findSubway(String stnText) throws IOException, ParseException {

        String urlStr = "http://swopenAPI.seoul.go.kr/api/subway/4d6641664c70726f3132384a7a414957/json/realtimeStationArrival/0/15/";
        urlStr += URLEncoder.encode(stnText, StandardCharsets.UTF_8);

        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;

        rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line).append("\n\r");
        }

        rd.close();
        urlConnection.disconnect();

        JsonUtil jsonUtil = new JsonUtil();
        Map<String, Object> resultMap = jsonUtil.dtoMapping(result.toString());

        return resultMap;
    }

    @RequestMapping("/findDetail")
    public List<SubwayDTO> findSubway(String stnText, String subwayId) throws IOException, ParseException {
        List<SubwayDTO> resultList = new ArrayList<>();

        Map<String, Object> tmpMap = findSubway(stnText);
        List<SubwayDTO> subwayDTOList = (List<SubwayDTO>) tmpMap.get("subwayDTOList");

        for( SubwayDTO subway : subwayDTOList) {
            if( subwayId.equals(subway.getSubwayId()) ){
                resultList.add(subway);
            }
        }
        return resultList;
    }

}

