package de.uks.se.scoreproject.dice.startup;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.IStartup;

import de.uks.se.scoreproject.dice.network.NetworkClient;
import de.uks.se.scoreproject.dice.preferences.PreferenceConstants;

/**
 * Class used to initialize default preference values.
 */
public class StartupInitializer implements IStartup {

	@Override
	public void earlyStartup() {

		System.out.println("im run at startup");
		IEclipsePreferences prefs = new InstanceScope().getNode("de.uks.se.scoreproject.dice");
		  // you might want to call prefs.sync() if you're worried about others changing your settings
		 // this.someStr = prefs.get(KEY1);
		 // this.someBool= prefs.getBoolean(KEY2);
		System.out.println("adresse: "+prefs.get(PreferenceConstants.P_STRING_Address,""));
		System.out.println("username: "+prefs.get(PreferenceConstants.P_STRING_username,""));
		System.out.println("pw: "+prefs.get(PreferenceConstants.P_STRING_pw,""));//"pwstringPreference", ""));
		String ipport = prefs.get("addrstringPreference","");//PreferenceConstants.P_STRING_Address, "");
		
		try{
			String username ="";
			int port =1;
			String arr[] = ipport.split(":");
			username = arr[0];
			port = Integer.parseInt(arr[1]);
			new NetworkClient(username, port,prefs.get(PreferenceConstants.P_STRING_username, ""), prefs.get(PreferenceConstants.P_STRING_pw, "")).start();;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*
		 * PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		 * public void run() { Shell activeShell = PlatformUI.getWorkbench()
		 * .getActiveWorkbenchWindow().getShell();
		 * MessageDialog.openInformation(activeShell, "Dice",
		 * "Hello, Eclipse world"); } });
		 */

	}

}
