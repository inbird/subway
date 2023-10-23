package com.cicd.subway.util;

import com.cicd.subway.dto.LineDTO;
import com.cicd.subway.dto.SubwayDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

@Slf4j
public class JsonUtil {

    public Map<String, Object>  dtoMapping(String jsonStr) throws ParseException, ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonStr);

        JSONObject jsonErrorMessage = (JSONObject) jsonObject.get("errorMessage");
        log.info("jsonErrorMessage={}", jsonErrorMessage.toString());

        JSONArray jsonSubwayList = (JSONArray) jsonObject.get("realtimeArrivalList");
        log.info("jsonSubwayList={}", jsonSubwayList.toString());

        List<SubwayDTO> subwayDTOList = new ArrayList<>();
        List<LineDTO> lineDTOList = new ArrayList<>();

        for (Object o : jsonSubwayList) {
            JSONObject jsonSubway = (JSONObject) o;
            subwayDTOList.add(makeDTO(jsonSubway));
            set.add( (String) jsonSubway.get("subwayId") );
        }

        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            lineDTOList.add( makeLineDTO(iterator.next()));
        }

        log.info("subwayDTOList={}", subwayDTOList.toString());
        log.info("lineDTOList={}", lineDTOList.toString());

        map.put("subwayDTOList", subwayDTOList);
        map.put("lineDTOList", lineDTOList);

        return map;
    }

    private String changeSubwayLine(String subwayId){
        String[] noArr  = new String[]{"1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009", "1061", "1063", "1065", "1067", "1075", "1077", "1092", "1093", "1081"};
        String[] lineArr = new String[]{"1호선","2호선","3호선","4호선","5호선","6호선","7호선","8호선","9호선","중앙선","경의중앙선","공항철도","경춘선","수의분당선","신분당선","우이신설선","서해선","경강선"};

        Map<String, String> map = new HashMap<>();
        for( int i=0 ; i< noArr.length; i++ ){
            map.put(noArr[i], lineArr[i]);
        }

        return map.get(subwayId);
    }

    private SubwayDTO makeDTO(JSONObject jsonSubway) {
        SubwayDTO subwayDTO = SubwayDTO.builder()
                .totalCount((long) jsonSubway.get("totalCount"))
                .rowNum((long) jsonSubway.get("rowNum"))
                .subwayId((String) jsonSubway.get("subwayId"))
                .updnLine((String) jsonSubway.get("updnLine"))
                .trainLineNm((String) jsonSubway.get("trainLineNm"))
                .statnFid((String) jsonSubway.get("statnFid"))
                .statnTid((String) jsonSubway.get("statnTid"))
                .statnId((String) jsonSubway.get("statnId"))
                .statnNm((String) jsonSubway.get("statnNm"))
                .btrainSttus((String) jsonSubway.get("btrainSttus"))
                .barvlDt((String) jsonSubway.get("barvlDt"))
                .btrainNo((String) jsonSubway.get("btrainNo"))
                .bstatnId((String) jsonSubway.get("bstatnId"))
                .recptnDt((String) jsonSubway.get("recptnDt"))
                .arvlMsg2((String) jsonSubway.get("arvlMsg2"))
                .arvlMsg3((String) jsonSubway.get("arvlMsg3"))
                .arvlCd((String) jsonSubway.get("arvlCd"))
                .build();
        return subwayDTO;
    }

    private LineDTO makeLineDTO(String subwayId) {
        LineDTO lineDTO = LineDTO.builder()
                .lineNumber(subwayId)
                .lineName( changeSubwayLine(subwayId) )
                .build();
        return lineDTO;
    }
}
