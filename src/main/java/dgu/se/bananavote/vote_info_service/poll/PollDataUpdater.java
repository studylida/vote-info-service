package dgu.se.bananavote.vote_info_service.poll;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dgu.se.bananavote.vote_info_service.district.District;
import dgu.se.bananavote.vote_info_service.district.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class PollDataUpdater {

    private final PollService pollService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final DistrictService districtService;

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final String API_URL_TEMPLATE =
            "https://apis.data.go.kr/9760000/PolplcInfoInqireService2/getPolplcOtlnmapTrnsportInfoInqire"
                    + "?resultType=json&serviceKey=%s&pageNo=%d&numOfRows=100&sgId=20240410&sdName=%s&wiwName=%s";

    @Autowired
    public PollDataUpdater(PollService pollService, RestTemplate restTemplate, ObjectMapper objectMapper, DistrictService districtService) {
        this.pollService = pollService;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.districtService = districtService;
    }

    @Scheduled(cron = "0 5 * * * *")
    @Transactional
    public void updatePollData() {
        List<District> districts = districtService.getDistrict(); // 모든 District 가져오기

        for (District district : districts) {
            updatePollDataForDistrict(district); // 각 District에 대해 투표소 정보 업데이트
        }
    }

    private void updatePollDataForDistrict(District district) {
        int pageNo = 1;
        int totalPages = 1;

        List<Poll> allPolls = new ArrayList<>();

        try {
            while (pageNo <= totalPages) {
                // District의 sdName, sggName을 인코딩
                String encodedSdName = URLEncoder.encode(district.getSdName(), StandardCharsets.UTF_8);
                String encodedSggName = URLEncoder.encode(district.getSggName(), StandardCharsets.UTF_8);

                // API URL 생성
                String apiUrl = String.format(API_URL_TEMPLATE, serviceKey, pageNo, encodedSdName, encodedSggName);
                URI uri = new URI(apiUrl);

                // API 호출
                String jsonResponse = restTemplate.getForObject(uri, String.class);
                JsonNode root = objectMapper.readTree(jsonResponse);
                JsonNode body = root.path("response").path("body");
                JsonNode items = body.path("items").path("item");

                if (pageNo == 1) {
                    int totalCount = body.path("totalCount").asInt();
                    int numOfRows = body.path("numOfRows").asInt();
                    totalPages = (int) Math.ceil((double) totalCount / numOfRows);
                }

                for (JsonNode item : items) {
                    Poll poll = new Poll();
                    poll.setSgId(item.path("sgId").asText());
                    poll.setPsName(item.path("psName").asText());
                    poll.setSdName(item.path("sdName").asText());
                    poll.setWiwName(item.path("wiwName").asText());
                    poll.setEmdName(item.path("emdName").asText());
                    poll.setPlaceName(item.path("placeName").asText());
                    poll.setAddr(item.path("addr").asText());
                    poll.setFloor(item.path("floor").asText());
                    pollService.savePoll(poll); // Poll 데이터 저장
                    allPolls.add(poll);
                }

                pageNo++;
            }
        } catch (Exception e) {
            System.err.println("Error updating poll data for district: " + district.getSdName() + ", " + district.getSggName());
            e.printStackTrace();
        }
    }
}
