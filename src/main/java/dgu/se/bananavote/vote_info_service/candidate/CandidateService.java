package dgu.se.bananavote.vote_info_service.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PromiseRepository promiseRepository;
    @Autowired
    private CareerRepository careerRepository;

    // 모든 후보자를 가져옵니다.
    public List<Candidate> getCandidate() {
        return candidateRepository.findAll();
    }

    // ID로 후보자를 가져옵니다.
    public Candidate getCandidateById(int id) {
        return candidateRepository.findById(id).orElse(null);
    }

    // 정당 이름으로 후보자를 가져옵니다.
    public List<Candidate> getCandidateByJdName(String jdName) {
        return candidateRepository.findByJdName(jdName);
    }

    // 후보자를 저장합니다.
    public void saveCandidate(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    // 모든 후보자를 가져옵니다.
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
    public List<CandidateResponse> getAllCandidatesWithCareers() {
        // 모든 후보자 데이터를 가져오고 경력 정보를 포함한 CandidateResponse로 변환
        return candidateRepository.findAll().stream()
                .map(candidate -> {
                    // 후보자의 경력 가져오기
                    List<Career> careers = careerRepository.findByCnddtId(candidate.getCnddtId());
                    return new CandidateResponse(candidate, careers);
                })
                .collect(Collectors.toList());
    }

}
