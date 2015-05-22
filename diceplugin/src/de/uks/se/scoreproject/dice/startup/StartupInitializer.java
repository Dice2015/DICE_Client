package de.uks.se.scoreproject.dice.startup;

<<<<<<< HEAD
import java.util.prefs.Preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.ISharedImages;
=======
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
>>>>>>> 04334824b5387627b759d9771487c42fc34f5a2d
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

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
			new NetworkClient(this, username, port,prefs.get(PreferenceConstants.P_STRING_username, ""), prefs.get(PreferenceConstants.P_STRING_pw, "")).start();;
			
		}catch(Exception e){
			showMessage("could not connect to server");
			e.printStackTrace();
		}
		
<<<<<<< HEAD
		
		
		
	}
	
	public static IProject getCurrentSelectedProject() {
	    IProject project = null;
	    ISelectionService selectionService = 
	        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();

	    ISelection selection = selectionService.getSelection();

	    if(selection instanceof IStructuredSelection) {
	        Object element = ((IStructuredSelection)selection).getFirstElement();

	        if (element instanceof IResource) {
	            project= ((IResource)element).getProject();
	        }
	    }
	    return project;
	}
	
	private void showMessage(String message) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable(){
			public void run(){
				Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(
						activeShell,
						"Sample View",
						message);
			}
		});
//		
//		PlatformUI.getWorkbench().addWorkbenchListener(new IWorkbenchListener(){
//
//			@Override
//			public boolean preShutdown(IWorkbench workbench, boolean forced) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public void postShutdown(IWorkbench workbench) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
		
		PlatformUI.getWorkbench().addWindowListener(new IWindowListener(){

			@Override
			public void windowActivated(IWorkbenchWindow window) {
				// TODO Auto-generated method stub
				System.out.println(window.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditors().length);

						
				System.out.println(window.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection());
				System.out.println("Eclipse window activated:"+window.getActivePage().getActiveEditor().getSite().getPage().getLabel());
				
			}

			@Override
			public void windowDeactivated(IWorkbenchWindow window) {
				// TODO Auto-generated method stub
				System.out.println("Eclipse window deactivated:"+window.getActivePage().getActiveEditor().getTitle());
				
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				// TODO Auto-generated method stub
				System.out.println("Eclipse window close:"+window.getActivePage().getActiveEditor().getTitle());
				
			}

			@Override
			public void windowOpened(IWorkbenchWindow window) {
				// TODO Auto-generated method stub
				System.out.println("Eclipse window open:"+window.getActivePage().getActiveEditor().getTitle());
				
			}
			
		});
		
	}

	public void setConnectionError(String message) {
		// TODO Auto-generated method stub
		showMessage(message);
=======
		/*
		 * PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		 * public void run() { Shell activeShell = PlatformUI.getWorkbench()
		 * .getActiveWorkbenchWindow().getShell();
		 * MessageDialog.openInformation(activeShell, "Dice",
		 * "Hello, Eclipse world"); } });
		 */

>>>>>>> 04334824b5387627b759d9771487c42fc34f5a2d
	}

}
