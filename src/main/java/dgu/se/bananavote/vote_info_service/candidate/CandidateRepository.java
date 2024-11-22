package dgu.se.bananavote.vote_info_service.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    // findById는 기본 제공
    //Optional<Candidate> findById(Integer id);
    List<Candidate> findByJdName(String jdName);
    List<Candidate> findByWiwName(String wiwName);
    List<Candidate> findByName(String name);
}
