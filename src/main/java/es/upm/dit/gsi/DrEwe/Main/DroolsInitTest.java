package es.upm.dit.gsi.DrEwe.Main;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.WorkingMemoryEntryPoint;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.time.SessionPseudoClock;

import es.upm.dit.gsi.DrEwe.Beans.CalendarEvent;
import es.upm.dit.gsi.DrEwe.Beans.DniEvent;
import es.upm.dit.gsi.DrEwe.Beans.LightEvent;
import es.upm.dit.gsi.DrEwe.SPIN.SPINModule;


/**
 * This is a the init test class, without GSN
 */
public class DroolsInitTest {

	private static StatefulKnowledgeSession ksession;
	
    @SuppressWarnings("restriction")
	public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();

            KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
            conf.setOption(ClockTypeOption.get("pseudo"));
            ksession = kbase.newStatefulKnowledgeSession(conf,null);
            WorkingMemoryEntryPoint entryPoint = (WorkingMemoryEntryPoint) ksession.getWorkingMemoryEntryPoint("entrada");
            SPINModule spinModule= new SPINModule();
            ksession.setGlobal("spinModule", spinModule);
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            // go !
            new Thread() {
            	@Override
            	public void run() {
            		ksession.fireUntilHalt();
            	}
            }.start();
            
            SessionPseudoClock clock=ksession.getSessionClock();
            
            //Test Light
            LightEvent lightEvent=new LightEvent("LightEvent", Calendar.getInstance(TimeZone.getTimeZone("UTC")), 100);
            entryPoint.insert(lightEvent);
            clock.advanceTime(5,TimeUnit.SECONDS);
            
            LightEvent lightOnEvent=new LightEvent("LightEvent", Calendar.getInstance(TimeZone.getTimeZone("UTC")), 1900);
            entryPoint.insert(lightOnEvent);
            clock.advanceTime(5,TimeUnit.SECONDS);
            //Test Dni
            DniEvent dniEventFirst=new DniEvent("DniEvent",Calendar.getInstance(TimeZone.getTimeZone("UTC")) , "Carlos Fresco", 1111111);
            entryPoint.insert(dniEventFirst);
            clock.advanceTime(5,TimeUnit.SECONDS);
            
            DniEvent dniEventSecond=new DniEvent("DniEvent",Calendar.getInstance(TimeZone.getTimeZone("UTC")) , "Miguel Crowned", 777777);
            entryPoint.insert(dniEventSecond);
            clock.advanceTime(5,TimeUnit.SECONDS);
            
            //Test SPIN
//            SPINEvent spinEvent=new SPINEvent("light_on","description test");
//            entryPoint.insert(spinEvent);
            
            String[] attendees={"carlos.tempog@gmail.com","miguelcrowned@gmail.com"};
            Calendar now=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            Calendar reunionTime=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//            reunionTime.roll(Calendar.MINUTE, 15);
            clock.advanceTime(20,TimeUnit.MINUTES);
            long droolsDuration=1200000;
            CalendarEvent calendarEvent=new CalendarEvent("CalendarEvent",now,"Meeting",attendees,reunionTime, new Date(clock.getCurrentTime()),droolsDuration);
            entryPoint.insert(calendarEvent);
            clock.advanceTime(5,TimeUnit.SECONDS);
            
            DniEvent dniEventThird=new DniEvent("DniEvent",Calendar.getInstance(TimeZone.getTimeZone("UTC")) , "Miguel Crowned", 777777);
            entryPoint.insert(dniEventThird);
            clock.advanceTime(5,TimeUnit.SECONDS);
            
            DniEvent dniEventFourth=new DniEvent("DniEvent",Calendar.getInstance(TimeZone.getTimeZone("UTC")) , "Carlos Fresco", 777777);
            entryPoint.insert(dniEventFourth);
            
            
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Rules.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

    
    

}
