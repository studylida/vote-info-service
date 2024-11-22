package dgu.se.bananavote.vote_info_service.poll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findBySdName(String sdName);
    List<Poll> findByWiwName(String wiwName);
    List<Poll> findByEmdName(String emdName);
}
