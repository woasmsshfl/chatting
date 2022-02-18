package site.metacoding.hospitalservice;

import lombok.Data;

@Data
public class Hospital {
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

    public Hospital(
            String addr,
            String mgtStaDd,
            String pcrPsblYn,
            String ratPsblYn,
            String recuClCd,
            String rprtWorpClicFndtTgtYn,
            String sgguCdNm,
            String sidoCdNm,
            String telno,
            String XPos,
            String XPosWgs84,
            String YPos,
            String YPosWgs84,
            String yadmNm,
            String ykihoEnc)

    {
        this.addr = addr;
        this.mgtStaDd = mgtStaDd;
        this.pcrPsblYn = pcrPsblYn;
        this.ratPsblYn = ratPsblYn;
        this.recuClCd = recuClCd;
        this.rprtWorpClicFndtTgtYn = rprtWorpClicFndtTgtYn;
        this.sgguCdNm = sgguCdNm;
        this.sidoCdNm = sidoCdNm;
        this.telno = telno;
        this.XPos = XPos;
        this.XPosWgs84 = XPosWgs84;
        this.YPos = YPos;
        this.YPosWgs84 = YPosWgs84;
        this.yadmNm = yadmNm;
        this.ykihoEnc = ykihoEnc;
    }
}
