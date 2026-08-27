package br.pucrs.microdemo.microservico2.business;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class CitationCollection {
    private ArrayList<String> citations;

    public CitationCollection() {
        citations = new ArrayList<>();
        citations.add("Don't hold onto things. that require a tight grip.");
        citations.add("I didn't come this far. to only come this far.");
        citations.add("Vulnerability sounds like faith and looks like courage.");
        citations.add("Each time you break your own boundaries to ensure that someone else likes you, you like yourself a little less.");
        citations.add("There is nothing stronger than a broken person who has rebuilt him/herself.");
    }

    public String getCitation() {
        int index = (int) (Math.random() * citations.size());
        return citations.get(index);
    }
}
