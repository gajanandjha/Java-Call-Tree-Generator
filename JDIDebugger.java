import java.io.IOException;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.spi.Connection;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.tools.jdi.SocketTransportService;


public class JDIDebugger {

	public static void main(String[] args) throws IOException, InterruptedException {
		Connection conn = new SocketTransportService().attach("localhost:40109", 0, 0);
		VirtualMachine virtualMachine = Bootstrap.virtualMachineManager().createVirtualMachine(conn);
		EventRequestManager eventRequestManager = virtualMachine.eventRequestManager();
		MethodEntryRequest methodEntryRequest = eventRequestManager.createMethodEntryRequest();
		methodEntryRequest.setSuspendPolicy(MethodEntryRequest.SUSPEND_NONE);
		methodEntryRequest.addClassFilter("com.*");
		methodEntryRequest.addClassExclusionFilter("com.zeroturnaround.*");
		MethodExitRequest methodExitRequest = eventRequestManager.createMethodExitRequest();
		methodExitRequest.setSuspendPolicy(MethodEntryRequest.SUSPEND_NONE);
		methodExitRequest.addClassFilter("com.*");
		methodExitRequest.addClassExclusionFilter("com.zeroturnaround.*");
		methodEntryRequest.setEnabled(true);
		methodExitRequest.setEnabled(true);
		EventSet eventSet = null;
		while ((eventSet = virtualMachine.eventQueue().remove()) != null) {
	        for (Event event : eventSet) {
	            if (event instanceof MethodEntryEvent) {
	                try {
						System.out.println("Entry^" + ((MethodEntryEvent) event).thread().name() + "^" + ((MethodEntryEvent) event).method().toString());
					} catch (Exception e) {
					}
	            }
	            if (event instanceof MethodExitEvent) {
	                try {
						System.out.println("Exit^" + ((MethodExitEvent) event).thread().name() + "^" + ((MethodExitEvent) event).method().toString());
					} catch (Exception e) {
					}
	            }
	        }
	    }
	}
}

