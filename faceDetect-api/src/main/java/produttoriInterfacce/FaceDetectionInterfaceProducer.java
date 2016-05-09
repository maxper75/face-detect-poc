package produttoriInterfacce;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import interfacce.FaceDetectionInterface;


public class FaceDetectionInterfaceProducer {
	private static final String REMOTING_ENDPOINT = "http-remoting://localhost:8080";
	private Context context;
	private Hashtable<String, String> jndiProperties;
	
	public void init(){
		jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL,
				REMOTING_ENDPOINT);
		jndiProperties.put("jboss.naming.client.ejb.context", "1");
		initContext();
	}
	
	private void initContext(){
		try {
			context = new InitialContext(jndiProperties);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FaceDetectionInterface getFrontendClient() throws NamingException{
		if(context==null)
			initContext();
		return (FaceDetectionInterface) context
				.lookup("java:frontend-ear/frontend-ejb/BackofficeEventManager!it.wabum.remoting.interfaces.BackofficeEventInterface");
	}
}
