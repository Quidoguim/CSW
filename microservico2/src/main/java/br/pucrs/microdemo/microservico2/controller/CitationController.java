package br.pucrs.microdemo.microservico2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucrs.microdemo.commons.ClasseComumSimples;
import br.pucrs.microdemo.microservico2.business.CitationCollection;

@RestController
public class CitationController {
    @Autowired
    private CitationCollection citationCollection;

    @GetMapping("/citation")
    public String getCitation() {
        ClasseComumSimples cs = new ClasseComumSimples();
        return citationCollection.getCitation();
    }
}
