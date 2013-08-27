package es.upm.dit.gsi.DrEwe.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.topbraid.spin.vocabulary.SPIN;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DCTerms;

import es.upm.dit.gsi.DrEwe.Beans.SPINEvent;
import es.upm.dit.gsi.DrEwe.SPIN.SPINModule;

public class SPINModuleTest {

	private SPINModule spinModule;
	
    @Before
    public  void setUp() {
    	this.spinModule=new SPINModule();
    }
	
	
	@Test
	public void testSPINModule() {
		
		OntModel ontmodel=spinModule.getOntModel();
		boolean isEmpty=ontmodel.isEmpty();
		assertTrue("Ontology loaded",!isEmpty);

	}

	@Test
	public void testInsertEvent() {
		SPINEvent event=new SPINEvent("my title","my description");
		spinModule.insertEvent(event);
		OntModel ontmodel=spinModule.getOntModel();
		Resource eventResource=ontmodel.getResource("http://gsi.dit.upm.es/ontologies/ewe/ns/Event_1");
		String titleFromModel=eventResource.getProperty(DCTerms.title).getString();
		assertEquals("Event inserted",event.getTitle(),titleFromModel);
	}

	@Test
	public void testLoadRules() {
		OntModel ontModel=spinModule.getOntModel();
		Resource eventResource=ontModel.getResource("http://gsi.dit.upm.es/ontologies/ewe/ns/Event");
		boolean loaded=eventResource.hasProperty(SPIN.rule);
		assertTrue("Rules loaded",loaded);
	}

	@Test
	public void testRunInferences() {
		SPINEvent event=new SPINEvent("light_on","my description");
		spinModule.insertEvent(event);
		spinModule.runInferences();
		long size=spinModule.getNewTriples().size();
		assertTrue("Inferences run",size>0);
	}



}
