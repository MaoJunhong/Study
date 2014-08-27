package own.cmd;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CustomizedCommand implements CommandProvider, BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		context.registerService(CommandProvider.class.getName(),
				new CustomizedCommand(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

	public void _say(CommandInterpreter ci) {
		ci.println("said:" + ci.nextArgument());
	}

	@Override
	public String getHelp() {
		return "\tsay - say what you input\n";
	}

}
