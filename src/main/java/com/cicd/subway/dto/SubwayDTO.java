package com.cicd.subway.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class SubwayDTO {
    private long totalCount; //데이터 건수
    private long rowNum;
    private String subwayId; //노선
    private String updnLine;
    private String trainLineNm;
    private String statnFid;
    private String statnTid;
    private String statnId;
    private String statnNm;
    private String btrainSttus;
    private String barvlDt;
    private String btrainNo;
    private String bstatnId;
    private String recptnDt;
    private String arvlMsg2;
    private String arvlMsg3;
    private String arvlCd;
}

