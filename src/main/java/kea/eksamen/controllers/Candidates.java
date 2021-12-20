package kea.eksamen.controllers;

import kea.eksamen.models.Candidate;
import kea.eksamen.models.Party;
import kea.eksamen.repositories.CandidateRepo;
import kea.eksamen.repositories.PartyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Candidates {

    private CandidateRepo candidateRepo;
    private PartyRepo partyRepo;


    @GetMapping("/candidates")
    public List<Candidate> getCandidates() {
        return candidateRepo.findAll(Sort.by(Sort.Direction.ASC,"party.letter"));
    }

    @GetMapping("/candidates/party/{partyId}")
    public List<Candidate> getCountInParty(@PathVariable Long partyId){
        Optional<Party> party = partyRepo.findById(partyId);
        if (party.isPresent())
            return candidateRepo.findAllByParty(party.get());
        return null;
    }

    @PostMapping("/candidates")
    public Candidate addParty(@RequestBody Candidate candidate) {
        return candidateRepo.save(candidate);
    }

    @DeleteMapping("/candidates/{id}")
    public String deleteParty(@PathVariable Long id) {
        try {
            candidateRepo.deleteById(id);
            return "Candidate deleted";
        } catch (Exception e) {
            return "Error, Candidate not deleted";
        }
    }

    @PutMapping("/candidates/{id}")
    public String updateParty(@PathVariable Long id, @RequestBody Candidate candidate) {
        if (candidateRepo.existsById(id)) {
            candidate.setId(id);
            candidateRepo.save(candidate);
            return "Candidate updated";
        } else {
            return "Candidate not found;";
        }
    }


    @Autowired
    public void setCandidateRepo(CandidateRepo candidateRepo) {
        this.candidateRepo = candidateRepo;
    }

    @Autowired
    public void setPartyRepo(PartyRepo partyRepo){
        this.partyRepo = partyRepo;
    }

}
