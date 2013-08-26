package alma.perftest.BasePerfCompImpl;

import java.util.logging.Logger;

import org.omg.PortableServer.Servant;

import alma.ACS.ACSComponentOperations;
import alma.acs.component.ComponentLifecycle;
import alma.acs.container.ComponentHelper;

/**
 * @author alma-component-helper-generator-tool
 */
public class BasePerfCompImplHelper extends ComponentHelper
{
	/**
	 * Passes a logger to the callback object.
	 * @param containerLogger
	 */
	public BasePerfCompImplHelper(Logger containerLogger)
	{
		super(containerLogger);
	}

    /**
     * Gets an instance of the implementation class of the LampAccess component.
     * @return ComponentLifecycle
	 * @see alma.acs.container.ComponentHelper#_createComponentImpl()
	 */
	protected ComponentLifecycle _createComponentImpl()
	{
		return new BasePerfCompImpl();
	}

	/**
	 * Gets an instance of the POATie class of the LampAccess component.
	 * @return Class
	 * @see alma.acs.container.ComponentHelper#_getPOATieClass()
	 */
	protected Class<? extends Servant> _getPOATieClass()
	{
		return alma.perftest.BasePerfCompPOATie.class;
	}

	/**
	 * Gets an instance of the operations of the LampAccess component.
	 * @return Class
	 * @see alma.acs.container.ComponentHelper#getOperationsInterface()
	 */
	protected Class<? extends ACSComponentOperations> _getOperationsInterface()
	{
		return alma.perftest.BasePerfCompOperations.class;
	}

}
