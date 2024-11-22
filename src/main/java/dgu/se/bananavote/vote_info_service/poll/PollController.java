package dgu.se.bananavote.vote_info_service.poll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poll")
public class PollController {

    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("/sdName/{sdName}")
    public List<Poll> getPollsBySdName(@PathVariable String sdName) {
        return pollService.getPollsBySdName(sdName);
    }

    @GetMapping("/wiwName/{wiwName}")
    public List<Poll> getPollsByWiwName(@PathVariable String wiwName) {
        return pollService.getPollsByWiwName(wiwName);
    }

    @GetMapping("/emdName/{emdName}")
    public List<Poll> getPollsByEmdName(@PathVariable String emdName) {
        return pollService.getPollsByEmdName(emdName);
    }
}
