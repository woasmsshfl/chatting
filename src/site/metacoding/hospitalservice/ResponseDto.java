package site.metacoding.hospitalservice;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDto {
    private Response response;

    @AllArgsConstructor
    @Data
    class Response {
        private Header header;
        private Body body;

        @AllArgsConstructor
        @Data
        class Header {
            private String resultCode; // 결과코드
            private String resultMsg; // 결과메시지
        }

        @AllArgsConstructor
        @Data
        class Body {
            private int numOfRows; // 한 페이지 결과 수
            private int pageNo; // 페이지번호
            private int totalCount; // 전체 결과 수

            private Items items;

            @AllArgsConstructor
            @Data
            class Items {
                private List<item> item;

                @AllArgsConstructor
                @Data
                class item {
                    private String addr; // 의료기관 주소
                    private String mgtStaDd; // 운영시작일자
                    private String pcrPsblYn; // PCR 검사 가능 여부
                    private String ratPsblYn; // RAT(신속항원검사)가능 여부
                    private String recuClCd; // 11:종합병원 / 21:병원 / 31:의원
                    private String rprtWorpClicFndtTgtYn; // 호흡기전담클리닉 여부
                    private String sgguCdNm; // 시군구명
                    private String sidoCdNm; // 시도명
                    private String telno; // 요양기관전화번호
                    private String XPos; // x좌표
                    private String XPosWgs84; // 세계지구x좌표
                    private String YPos; // y좌표
                    private String YPosWgs84; // 세계지구y좌표
                    private String yadmNm; // 요양기관명
                    private String ykihoEnc; //
                } // item
            }// items
        }// body
    }// response
}// responseDto
