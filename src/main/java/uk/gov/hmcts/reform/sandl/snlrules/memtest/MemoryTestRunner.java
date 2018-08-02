package uk.gov.hmcts.reform.sandl.snlrules.memtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsServiceFactory;

@Component
public class MemoryTestRunner
{
	public static final String DROOLS_SERVICE = "Sessions";

    @Autowired
    private DroolsServiceFactory droolsServiceFactory;
 
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("**************************************");
    	System.err.println("Running memory test");
    	new MemoryTest(droolsServiceFactory.getInstance(DROOLS_SERVICE)).run();
    	try
    	{
    		Thread.sleep(10000);
    	}
    	catch (InterruptedException e)
    	{
    		// discard
    	}
    	System.exit(0);
    }
}