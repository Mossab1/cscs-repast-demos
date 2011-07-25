package smallworld.relogo

import repast.simphony.relogo.factories.AbstractReLogoGlobalsAndPanelFactory

/**
*
* @author Tim Sweda
*
*/

public class UserGlobalsAndPanelFactory extends AbstractReLogoGlobalsAndPanelFactory{
	public void addGlobalsAndPanelComponents(){
	
		/**
		 * Uncomment by removing "//" below to include ReLogo Tick Display
		 */
		//addReLogoTickCountDisplay()
		
		/**
		 * Place custom panels and globals below, for example:
		 * 
		  		addButtonWL("setup","Setup") 	// Button with label ( method name, button label )
		  		addButton("action1")			// Button without label ( method name )
		  		addToggleButtonWL("go","Go")	// Toggle Button with label ( method name, button label )
		  		addToggleButton("action2")		// Toggle Button without label  ( method name )
		  		addGlobal("globalVariable1")	// Globally accessible variable ( variable name)
		  		// Slider with label ( variable name, slider label, minimum value, increment, maximum value, initial value )
		        addSliderWL("sliderVariable", "Slider Variable", 0, 1, 10, 5)
		        // Slider without label ( variable name, minimum value, increment, maximum value, initial value )
		        addSlider("sliderVariable2", 0.2, 0.1, 0.8, 0.5)
		        // Chooser with label  ( variable name, chooser label, list of choices , zero-based index of initial value )
		        addChooserWL("chooserVariable", "Chooser Variable", ["yes","no","maybe"], 2)
		        // Chooser without label  ( variable name, list of choices , zero-based index of initial value )
		        addChooser("chooserVariable2", [1, 66, "seven"], 0)
		 */
		
		addGlobal("nextNode")
		addGlobal("rewiredColor")
		addGlobal("clusterCoeff")
		addGlobal("avgPathLength")
		
		addButton("setup")
		addButton("rewireOne")
		addButton("rewireAll")
		addSlider("numNodes", 3, 1, 150, 24)
		addSlider("initialNodeDegree", 2, 2, 74, 4)
		addSlider("rewireProb", 0, 0.01, 1, 0.5)
		addButton("reportStats")

	}
}