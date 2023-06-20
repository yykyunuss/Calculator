package swingui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import numberconversionservice.NumberConversionService;
import swinguiservice.SwingUIService;

public class Activator implements BundleActivator {

	private BundleContext context;
    private ServiceReference<NumberConversionService> numberConversionService;
    private SwingUIService uiService;
 
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
				
		numberConversionService = context.getServiceReference(NumberConversionService.class);
		NumberConversionService serviceRef = context.getService(numberConversionService);
        
		uiService = new SwingUIService();
		uiService.setConversionService(serviceRef);
		uiService.initiliazeUI();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
        context.ungetService(context.getServiceReference(NumberConversionService.class));
		context = null;
		uiService.closeUI();
	} 
}