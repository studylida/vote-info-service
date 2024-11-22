package dgu.se.bananavote.vote_info_service.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public List<CandidateResponse> getAllCandidates() {
        // Service를 호출하여 응답 반환
        return candidateService.getAllCandidatesWithCareers();
    }

//
//    @GetMapping
//    public List<Candidate> getCandidate() { return candidateService.getCandidate(); }
    @GetMapping("/{id}")
    public Candidate getCandidateById(@PathVariable int id) { return candidateService.getCandidateById(id); }
    @GetMapping("/jdName/{jdName}")
    public List<Candidate> getCandidateByJdName(@PathVariable String jdName) { return candidateService.getCandidateByJdName(jdName); }
}
