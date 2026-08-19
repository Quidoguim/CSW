package br.pucrs.microdemo.microservico2;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import br.pucrs.microdemo.microservico2.business.CitationCollection;

// @SpringBootTest

class Microservico2ApplicationTests {

	@Test
	void citationCollectionTest() {
		CitationCollection cc = new CitationCollection();
		
		assertNotNull(cc.getCitation());
	}

}
