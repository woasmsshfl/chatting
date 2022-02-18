package site.metacoding.hospitalservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import site.metacoding.hospitalservice.ResponseDto.Response.Body.Items.item;

public class HosptalDownload {
    public static List<Hospital> getHospitalList() {

        List<Hospital> hospitalList = new ArrayList<>();

        // StringBuffer sb = new StringBuffer();
        // sb.append("http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?");
        // sb.append(
        // "serviceKey=f%2BafZVkcjTIbiKy2FpST1dZWhtMXocgF70j2NsCMFqx04qe0U2MNwjS0BGqgqzZHttuGKxxK4Jh60Uj2PyMSkw%3D%3D&");
        // sb.append("pageNo=1&");
        // sb.append("numOfRows=10&");
        // sb.append("_type=json");
        try {
            URL url = new URL(
                    "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=f%2BafZVkcjTIbiKy2FpST1dZWhtMXocgF70j2NsCMFqx04qe0U2MNwjS0BGqgqzZHttuGKxxK4Jh60Uj2PyMSkw%3D%3D&pageNo=1&numOfRows=10&_type=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            String responseJson = br.readLine();
            Gson gson = new Gson();
            ResponseDto responseDto = gson.fromJson(responseJson, ResponseDto.class);

            List<item> result = responseDto.getResponse().getBody().getItems().getItem();

            // totalCount 적용
            int totalCount = responseDto.getResponse().getBody().getTotalCount();
            // StringBuffer sb2 = new StringBuffer();
            // sb.append("http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?");
            // sb.append(
            // "serviceKey=f%2BafZVkcjTIbiKy2FpST1dZWhtMXocgF70j2NsCMFqx04qe0U2MNwjS0BGqgqzZHttuGKxxK4Jh60Uj2PyMSkw%3D%3D&");
            // sb.append("pageNo=1&");
            // sb.append("numOfRows=" + totalCount + "&");
            // sb.append("_type=json");
            url = new URL(
                    "http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService?serviceKey=f%2BafZVkcjTIbiKy2FpST1dZWhtMXocgF70j2NsCMFqx04qe0U2MNwjS0BGqgqzZHttuGKxxK4Jh60Uj2PyMSkw%3D%3D&pageNo=1&numOfRows="
                            + totalCount + "&_type=json");
            conn = (HttpURLConnection) url.openConnection();
            br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            responseJson = br.readLine();
            gson = new Gson();
            responseDto = gson.fromJson(responseJson, ResponseDto.class);

            result = responseDto.getResponse().getBody().getItems().getItem();

            for (int i = 0; i < result.size(); i++) {
                Hospital hospital = new Hospital(
                        result.get(i).getAddr(),
                        result.get(i).getMgtStaDd(),
                        result.get(i).getPcrPsblYn(),
                        result.get(i).getRatPsblYn(),
                        result.get(i).getRecuClCd(),
                        result.get(i).getRprtWorpClicFndtTgtYn(),
                        result.get(i).getSgguCdNm(),
                        result.get(i).getSidoCdNm(),
                        result.get(i).getTelno(),
                        result.get(i).getXPos(),
                        result.get(i).getXPosWgs84(),
                        result.get(i).getYPos(),
                        result.get(i).getYPosWgs84(),
                        result.get(i).getYadmNm(),
                        result.get(i).getYkihoEnc());

                hospitalList.add(hospital);
            }

            return hospitalList;

        } catch (Exception e) {
            System.out.println("오류 발생 : " + e.getMessage());
        }
        return null;
    }
}