package es.upm.dit.gsi.DrEwe.Main;

import java.util.Timer;
import java.util.TimerTask;

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

import es.upm.dit.gsi.DrEwe.Utils.GsnToExpert;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsInit {

	private static StatefulKnowledgeSession ksession;
	
    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();

            KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
            conf.setOption(ClockTypeOption.get("realtime"));
             ksession = kbase.newStatefulKnowledgeSession(conf,null);
            WorkingMemoryEntryPoint entryPoint = (WorkingMemoryEntryPoint) ksession.getWorkingMemoryEntryPoint("entrada");
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            // go !
            new Thread() {
            	@Override
            	public void run() {
            		ksession.fireUntilHalt();
            	}
            }.start();
            final GsnToExpert gte=new GsnToExpert(entryPoint);
            Timer timer = new Timer();
            long delay=0;
            long interval=1000;
			timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                	System.out.println("Updating events");
            		gte.updateEvents(40);
            		gte.updateLastCheck();
                }

            }, delay, interval);

            
            
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

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}
