package numberconversion;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import numberconversionservice.NumberConversionService;
import numberconversionserviceimpl.NumberConversionServiceImpl;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;		
		this.registerNumberConversionService();
	}

	public void registerNumberConversionService() {
		NumberConversionService numberConversionService = new NumberConversionServiceImpl();
		context.registerService(NumberConversionService.class, numberConversionService, null);
	}
	
	public void stop(BundleContext bundleContext) throws Exception {
		context.ungetService(context.getServiceReference(NumberConversionService.class));
		Activator.context = null;
	}

}
