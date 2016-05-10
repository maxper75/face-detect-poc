package interfacesProducer;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.wabum.facedetection.interfaces.FaceDetectionInterface;


public class FaceDetectionInterfaceProducer {
	private Context context;
	private Hashtable<String, String> jndiProperties;

	public void init(){
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			context = new InitialContext(jndiProperties);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FaceDetectionInterface getFrontendClient() throws NamingException{
		if(context==null){
			init();
			System.out.println("è nullo");
		}
		final String appName = "faceDetect-ear";
		final String moduleName = "faceDetect-ejb";
		final String distinctName = "";
		final String beanName = "ServiceInterface";
		final String viewClassName = FaceDetectionInterface.class.getName();

		return (FaceDetectionInterface) context
				.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
	}
}
