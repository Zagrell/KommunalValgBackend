package kea.eksamen.controllers;

import kea.eksamen.dto.PartyPercentageDTO;
import kea.eksamen.models.Candidate;
import kea.eksamen.models.Party;
import kea.eksamen.repositories.PartyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class Parties {
    private PartyRepo partyRepo;


    @GetMapping("/parties")
    public List<Party> getParties() {
        return partyRepo.findAll(Sort.by(Sort.Direction.ASC, "letter"));
    }

    @GetMapping("/parties/totalvotes/percentages")
    public List<PartyPercentageDTO> getPartyPercentageOfVotes() {
        List<PartyPercentageDTO> partyPercentages = new ArrayList<>();
        List<Party> parties = partyRepo.findAll();

        int totalVotes = 0;
        for (Party party : parties) {
            int votes = 0;
            for (Candidate candidate : party.getCandidates()) {
                votes += candidate.getVotes();
            }
            partyPercentages.add(new PartyPercentageDTO(party, votes));
            totalVotes += votes;
        }
        for(PartyPercentageDTO dto : partyPercentages){
            if(totalVotes > 0) {
                dto.percentage = (dto.votes * 100) / totalVotes;
            }else{
                dto.percentage = 0;
            }
            System.out.println(totalVotes);
            System.out.println(dto.votes);
        }
        Collections.sort(partyPercentages);

        return partyPercentages;
    }


    @GetMapping("/parties/{id}")
    public Party getParty(@PathVariable Long id) {
        return partyRepo.findById(id).orElse(null);
    }

    @GetMapping("/parties/{id}/totalvotes")
    public int getPartyVotes(@PathVariable Long id) {
        Party party = partyRepo.findById(id).orElse(null);
        int votes = 0;
        if (party != null) {
            for (Candidate candidate : party.getCandidates()) {
                votes += candidate.getVotes();
            }
        }
        return votes;
    }


    @PostMapping("/parties")
    public Party addParty(@RequestBody Party party) {
        return partyRepo.save(party);
    }

    @DeleteMapping("/parties/{id}")
    public String deleteParty(@PathVariable Long id) {
        try {
            partyRepo.deleteById(id);
            return "Party deleted";
        } catch (Exception e) {
            return "Error, Party not deleted";
        }
    }

    @PutMapping("/parties/{id}")
    public String updateParty(@PathVariable Long id, @RequestBody Party party) {
        if (partyRepo.existsById(id)) {
            party.setId(id);
            partyRepo.save(party);
            return "party updated";
        } else {
            return "party not found;";
        }
    }


    @Autowired
    public void setPartyRepo(PartyRepo partyRepo) {
        this.partyRepo = partyRepo;
    }

}
