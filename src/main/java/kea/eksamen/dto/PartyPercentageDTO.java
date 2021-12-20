package kea.eksamen.dto;

import kea.eksamen.models.Party;

public class PartyPercentageDTO implements Comparable<PartyPercentageDTO> {

    public Party party;
    public int votes;
    public Integer percentage;

    public PartyPercentageDTO(Party party, int votes) {
        this.party = party;
        this.votes = votes;
    }


    @Override
    public int compareTo(PartyPercentageDTO o) {
        return o.percentage.compareTo(percentage);
    }
}
