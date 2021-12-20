package kea.eksamen.repositories;

import kea.eksamen.models.Candidate;
import kea.eksamen.models.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepo extends JpaRepository<Candidate, Long> {

    List<Candidate> findAllByParty(Party party);



}
