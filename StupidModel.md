# Summary #
  * Source Code  :
    * Download the [StupidModelAll.zip archive](http://code.google.com/p/cscs-repast-demos/downloads/detail?name=StupidModelAll.zip)
    * Get the latest version from the repository: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/
  * Platform     : [Repast Simphony Java](RepastSJava.md)
  * Mentor       : Dr. Eric Tatara
  * Author       : [Richard Oliver Legendi](Richard.md)



# Contents #



---


# Introduction #
This tutorial is based on the article _[StupidModel and Extensions: A template and teaching tool for agent-based modeling platforms](http://condor.depaul.edu/slytinen/abm/StupidModelFormulation.pdf)_ by Steven F. Railsback, Steven L. Lytinen and Stephen K. Jackson, and guides through a creation of a very simple model with random moving agents to a basic model with different agents interacting with each other and their surroundings in [Repast Simphony](http://repast.sourceforge.net/).

When we are finished, the running model should look like the following figure:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation.png)

## Why the StupidModel? ##
I am sure the StupidModel considered a relevant learning/comparison tool for agent-based modelling. Several reference implementations may be found on [Steven L. Lytinen's site](http://condor.depaul.edu/slytinen/abm/). Additional resources may be found at the [SwarmWiki](http://www.swarm.org/wiki/Software_templates).

## Prerequisites ##
Before we start, make sure a proper installation of Repast Simphony (version 2.0 Beta, released on 12/3/2010 or later) is available to use.

Instructions on downloading and installing Repast Simphony on various platforms can be found on the [Repast download website](http://repast.sourceforge.net/download.html).

Repast S can be used several ways: models may be created with [Flowcharts](http://repast.sourceforge.net/docs/RepastFlowGettingStarted.pdf), a Logo-like language called [ReLogo](http://repast.sourceforge.net/docs/ReLogoGettingStarted.pdf), or purely in [Java and/or Groovy](http://repast.sourceforge.net/docs/RepastJavaGettingStarted.pdf), and even C++ with [Repast HPC](http://repast.sourceforge.net/docs/RepastHPCManual.pdf).

In the current tutorial, we use Java to create the StupidModel implementation with the help of the [Java API](http://repast.sourceforge.net/docs/api/repastjava/index.html) offered by Repast Simphony. Following the tutorial does not require to read any of the referenced documents, however, they may be useful in the future.

# Notes on the Implementation #
The source code we use as example below in this document are formatted to be clear and as straightforward as possible. In the attached source code however, it is carefully written with several blocks of boilerplate code that is irrelevant from the view of the model (e.g., checking parameters of a function and making assertions about the state of the program). For clarity, these segments are omitted here, but may be found in the attached source code.

Although domain-specific languages like [ReLogo](RepastSReLogo.md) shown to have many advantages in ABM when creating usual models like the StupidModel `[3]`, this model itself is written purely in Java to illustrate the usage of the standard Repast Simphony Java API.

## Additional Resources ##
We have created extensive documentation for the final StupidModel implementation, these may be found in the `docs` directory of the Repast Simphony project, or browsed online at the following links:

  * [Javadoc of the core model](http://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/trunk/docs/javadoc_src/index.html)
  * [Javadoc of the tests](http://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/trunk/docs/javadoc_test/index.html) written to verify the behaviour of the model
  * [Cobertura code coverage report](http://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/trunk/docs/cobertura/index.html) generated for the model to verify the test coverage

# Model List #
Each section of the following tutorial first describes the StupidModel, a set of 16 simple template models designed as examples for learning how to use agent-based modeling platforms, then contains detailed notes on the implementation in Repast Simphony. Version 1 is the basic StupidModel, which is extremely simple. As the first exercise in a class for nonprogrammers, Version 1 can be implemented in 2-3 hours. The subsequent versions incrementally add features that are commonly used in real models. These features include model elements such as interaction among individuals and their environment, stopping rules, alternative orders in which individuals execute their actions, mortality and reproduction, file input for habitat variables, and multiple kinds of agents. Some versions simply add software tools for observing the model: probes, graphs, and file output.

The documentation for each version states its purpose-what tool or technique is illustrated by the version, describes its formulation, and provides notes on how the changes were implemented in the example software.

The complete StupidModel list:

  1. [Basic StupidModel](#Basic_StupidModel.md)
  1. [Bug Growth](#Bug_Growth.md)
  1. [Habitat Cells and Resource](#Habitat_Cells_and_Resource.md)
  1. [Cell and Bug Probes](#Cell_and_Bug_Probes.md)
  1. [Parameters and Parameter Displays](#Parameters_and_Parameter_Displays.md)
  1. [Histogram Output](#Histogram_Output.md)
  1. [Stopping the Model](#Stopping_the_Model.md)
  1. [File Output](#File_Output.md)
  1. [Randomized Agent Actions](#Randomized_Agent_Actions.md)
  1. [Sorted Agent Actions](#Sorted_Agent_Actions.md)
  1. [Optimal Movement](#Optimal_Movement.md)
  1. [Bug Mortality and Reproduction](#Bug_Mortality_and_Reproduction.md)
  1. [Population Abundance Graph](#Population_Abundance_Graph.md)
  1. [Random Normal Initial Size](#Random_Normal_Initial_Size.md)
  1. [Habitat Data from File Input](#Habitat_Data_from_File_Input.md)
  1. [Predators](#Predators.md)

## Basic StupidModel ##

### Purpose ###
This is the basic StupidModel, an extremely simple individual-based model used as a starting point for learning Repast (or other IBM platforms).

### Formulation ###
  * The space is a two-dimensional grid of dimensions `100 x 100`. The space is toroidal, meaning that if bugs move off one edge of the grid they appear on the opposite edge.
  * `100` bug agents are created. They have one behavior: moving to a randomly chosen grid location within `+/- 4` cells of their current location, in both the `X` and `Y` directions. If there already is a bug at the location (including the moving bug itself - bugs are not allowed to stay at their current location unless none of the neighborhood cells are vacant), then another new location is chosen. This action is executed once per time step.
  * The bugs are displayed on the space. Bugs are drawn as red circles. The display is updated at the end of each time step.
  * Instead of specifying which random number generation algorithm to use, the default generator for each platform is used.

### Implementation Notes ###
First, start up Repast Simphony. If it is the first time the modelling environment is started, it asks for a default workspace. The _workspace_ is a directory used to store the models created, along with several settings. Please also make sure to switch to the _Java perspective_ by clicking the _Java_ button on the upper right corner.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/switching_to_java_perspective.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/switching_to_java_perspective.png)

The outlook should change a bit: an Eclipse perspective determines the visible actions, buttons and views within the development environment. However, they all may be customized, and even new ones may be created afterwards.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/java_perspective.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/java_perspective.png)

The next step is to create our very fist Repast Simphony project! This may be done several ways, e.g.:

  * Click on the _File --> New --> Other..._ in the menu bar
  * Right-click on the _Project Explorer_ and select _New --> Other_ from the popup menu
  * Or, simply press the _Ctrl + N_ hotkey

A wizard dialogue shows up to create a new element on the workspace.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_new_simphony_project.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_new_simphony_project.png)

Click on the _Next >_ button. A new wizard page is shown where the name of the project may be specified. Type `stupidmodel` into the _Project name_ text field, and press _Finish_.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_sipmohy_project.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_sipmohy_project.png)

> <b>Remark</b> You may also proceed to the next wizard page instead of pressing the finish button. Since a Repast Simphony project is generally a Java project, you may set some Java-specific settings on this page (e.g., the concrete build path with additional libraries, their ordering in the `CLASSPATH` variable, source and build paths, etc.). These settings are important principally when using Eclipse for Java development or for advanced users creating simulations with several 3rd party libraries. For the current model, the default settings are perfect for us.

After pressing the _Finish_ button, the freshly created project of the model should be shown in the _Package Explorer_ view.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_project_created.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_project_created.png)

As we stated in the [preface](#Prerequisites.md), Simphony offers several ways to define a model. By default, it offers some skeleton files to start with for Groovy or Flowchart development. These files are `ModelInitializer.groovy` and `ModelInitializer.agent`, which may be deleted, since in the current tutorial we create a model using the standard Repast Simphony Java API. They may be deleted either by right-clicking on the resources and selecting _Delete_ from the popup menu as shown below, or by simply pressing the _Del_ button on the keyboard.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/delete_unnecessary_resources.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/delete_unnecessary_resources.png)

#### Creating a Bug Agent ####

Our first task is to create a simple `Bug` agent. When using the standard Repast Simphony Java API, agents have not got to be additionally annotated: they are simple Java classes. So, the first step is to create a simple Java class file by pressing _Ctrl + N_ and selecting `Class` from the possibilities. On the next screen, assign `stupidmodel.agents` as the package of the new class, and `Bug` as its name. Press _Finish_.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_new_bug_agent_class.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_new_bug_agent_class.png)

A simple, empty Java class definition is created:

```
package stupidmodel.agents;

public class Bug {

}
```

> <b>Remark</b> Java classes are shown in a special editor that has very nice features. Since we are going to refer to specific line numbers in the following sections, it is recommended to turn on the line numbering feature. Right-click on the left side of the editor and select _Show line numbers_ from the popup menu.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/enabling_line_numbers.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/enabling_line_numbers.png)

We will also need a dedicated class to store model-specific constant values. It is usually a good and common practice to put these constants into one place (so when they had to be changed it may be done by modifying a simple value). The basic model has a few such values, namely:

  1. The size of the two-dimensional grid (`100`)
  1. The number of `Bug` agents to create at initialization (`100`)
  1. Tha vision range of `Bug` agents (`4`)

Create a new class named `Constants` in the package `stupidmodel.common`, and put these constant definitions into it as follows:

```
package stupidmodel.common;

public final class Constants {
        public static final int GRID_SIZE = 100;
        public static final int BUG_COUNT = 100;
        public static final int BUG_VISION_RANGE = 4;
}
```

Now, back to the `Bug` class. In the first demo version, the `Bug` agents will be located on a simple `Grid`. In Repast Simphony, the superclass of all type of grids is the `repast.simphony.space.grid.Grid` interface. In their constructors, we assing the associated `Grid` instance with the agents:

```
import repast.simphony.space.grid.Grid;

public class Bug {
        private final Grid<Object> grid;

        public Bug(final Grid<Object> grid) {
                this.grid = grid;
        }
}
```

The grid is assigned the Object generic type parameter, because in further models some other type of agents may be placed on it (i.e., `HabitatCell` and `Predator` instances, but more of them later on).

For debugging we also add a default `toString()` implementation for the `Bug` agents. This comes handy when printing out an agent to the console or when debugging the simulation. The most important information about our agent is its current position at the grid, so we query it from the grid by the `getLocation()` function:

```
@Override
public String toString() {
	return String.format("Bug @ location %s", grid.getLocation(this));
}
```

#### Creating a Utility Class ####

We would like to create simple agent behaviour, so creating a separate class for common utility functions sounds reasonable. Create a new class named `SMUtils` in the package `stupidmodel.common`, and add the following static utility method definitions:

```
package stupidmodel.common;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.query.space.grid.GridCell;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.GridPoint;

public final strictfp class SMUtils {
        public static <T> T randomElementOf(final List<T> list) {
                return list.get(RandomHelper.nextIntFromTo(0, list.size() - 1));
        }

        public static <T> List<GridCell<T>> getFreeGridCells(
                        final List<GridCell<T>> neighborhood) {
                final ArrayList<GridCell<T>> ret = new ArrayList<GridCell<T>>();

                for (final GridCell<T> act : neighborhood) {
                        if (0 == act.size()) {
                                ret.add(act);
                        }
                }

                return ret;
        }
}
```

The first function, `randomElementOf()` is really simple: by utilizing the `repast.simphony.random.RandomHelper` class (which is the class in the Repast Simphony Java API that offers several utility functions to handle pseudorandom number generators) it returns one of the elements of the specified list randomly (based on a uniform distribution).

The other one, `getFreeGridCells()` may look a bit complicated for the first sight, but it is really simple on a second look. All of the created `Grid` objects contain `GridCell` instances on them. As the model defines, we would like to know if whether such a cell is empty or not: comparing the `size()` of the objects located on a `GridCell` to zero answers that question. The method, however, is a bit generalized, and accepts any list of `GridCell` objects.

#### Specifying an Agent Action ####

The last thing we need to add to our first `Bug` implementation is its `step()` action.

In Repast Simphony, one of the most convenient ways to define an agent action is to use the `@ScheduleMethod` annotation, which accepts several parameters, e.g., the first time step when the action should be activated, and the interval how often it should be executed (if it is a cyclic event). To define such an action, add the following method definition to the `Bug` class:

```
@ScheduledMethod(start = 1, interval = 1)
public void step() {
	// ...
}
```

> <b>Remark</b> From now on, for the sake of simplicity, we omit the fully qualified class names and `import` statements. Eclipse automatically inserts these, and in case of uncertainity the `Ctrl + Space` (_auto-complete feature_) combination can solve the situation. In case a whole code segment is inserted into the Java editor, using the `Ctrl + Shift + O` (_organize imports_) key combination solves all of the importing and namespace errors (in case of duplicate identifiers it asks for assistance).
> Using the hotkeys may really boost up the development speed. Press `Ctrl+Shift+L` to get a full list of usable hotkey combinations in the current context (e.g., in the Java editor), or see the full list of available ones through _Window -> Preferences -> General -> Keys_ settings page.

We defined an action that is executed for all `Bug` instance from the beginning (`start = 1`) of the simulation in each time step (`interval = 1`). Now, as the model defines, the first thing a `Bug` should do is to find its location, and query its neighbourhood.

Fortunately, there is a method we can use to find the neighborhood of an agent offered by the standard Repast Java API. We have to create an instance of class `GridCellNgh` and call its `getNeighborhood()` function.

  * To create a `GridCellNgh` instance, we have to specify the grid, the center location of the neighborhood and the extent (width, height) of the required neighborhood relation.
  * Calling the `getNeighborhood()` function is then quite straightforward: it requires only a logical parameter which determines to whether include the center location in the returned list or not.

The result is a list of `GridCell` instances that make up the neighborhood.

```
final List<GridCell<Bug>> bugNeighborhood = new GridCellNgh<Bug>(grid,
	location, Bug.class, Constants.BUG_VISION_RANGE,
	Constants.BUG_VISION_RANGE).getNeighborhood(false);
```

We have a utility function that returns the filtered list of empty cells, and it is easy to use:

```
final List<GridCell<Bug>> freeCells = SMUtils
		.getFreeGridCells(bugNeighborhood);
```

As the model specifies the agent should not move if there is no available free cell in its neighborhood. In this case, we simply stop the execution of the action by calling a `return` statement.

```
if (freeCells.isEmpty()) {
	return;
}
```

The model specifies to choose a random element from the list. It may be done this way:

```
SimUtilities.shuffle(freeCells, RandomHelper.getUniform());
```

The `SimUtilites` class offers a set of utility methods useful for the simulation (e.g., normalizing, scaling values or shuffling lists and swapping its elements). Take a look at the description and offered methods in the Repast Java API documentation.

However, it is more convenient to choose a random element directly from the list of free cells as follows, so it is not a vital element of the activity:

```
final GridCell<Bug> chosenFreeCell = SMUtils.randomElementOf(freeCells);
```

After evaluating a new empty place for the agent, we can move it by calling the `moveTo()` method of the used `Grid` instance in order to relocate the agent. The functino has three parameters: the agent to move on the grid, and its new `x` and `y` locations (there may be arbitrary number of coordinates if requried, but in this example model we use a two-dimensional discrete space).

```
final GridPoint newGridPoint = chosenFreeCell.getPoint();
grid.moveTo(this, newGridPoint.getX(), newGridPoint.getY());
```

The whole body of the created action method is shown in the following code listing:

```
@ScheduledMethod(start = 1, interval = 1)
public void step() {
	final List<GridCell<Bug>> bugNeighborhood = new GridCellNgh<Bug>(grid,
		location, Bug.class, Constants.BUG_VISION_RANGE,
		Constants.BUG_VISION_RANGE).getNeighborhood(false);

	final List<GridCell<Bug>> freeCells = SMUtils
			.getFreeGridCells(bugNeighborhood);

	if (freeCells.isEmpty()) {
		return;
	}

	SimUtilities.shuffle(freeCells, RandomHelper.getUniform());

	final GridCell<HabitatCell> chosenFreeCell = habitatCells.get(0);

	final GridPoint newGridPoint = chosenFreeCell.getPoint();
	grid.moveTo(this, newGridPoint.getX(), newGridPoint.getY());
}
```

#### Creating the Simulation Context ####
One last piece is needed to complete the whole picture: a custom context builder implementation which is responsible to perform the initialization. This is done by creating a class that implements the `ContextBuilder<T>` interface, where `T` refers for the types added to the used context. Since we have different type of agents (i.e., `Bug`, `HabitatCell` and `Predator` agents), as mentioned previously we use `Object` as `T` (cf. the definition of `Bug` agents containing `Grid<Object>` members).

It is pretty simple to create a custom `ContextBuilder` implementation, because it declares only one function called `buildContext()`. The role of this function is to build and return a context. A default context instance is passed to the function that may be used when no special initialization is required, however, the returned context does not necessarily have to be the passed in context.

Create a new class called `StupidModelContextBuilder` through the new class file wizard, and add `ContextBuilder` as its interface. The IDE will show up a new empty file with an empty implementation for the `build()` method:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_stupidmodel_context_builder_class.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_stupidmodel_context_builder_class.png)

The file shows some compilation errors, we have to modify it a bit in order to solve the errors and the warnings by assigning the class type parameter to `Object` as follows:

```
package stupidmodel;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;

public class StupidModelContextBuilder implements ContextBuilder<Object> {
	@Override
	public Context<Object> build(Context<Object> context) {
		// TODO Auto-generated method stub
		return null;
	}
}
```

Building a context consists of filling it with agents, adding projects and so forth. In the few code blocks, we create and initialize a space and a grid, assign them and to the context unique IDs, and populate the context and the grid with Bug `agents`.

To implement the `build()` method as described, first define a few other constants, the IDs for the used projections:

```
public final class Constants {
	public static final String CONTEXT_ID = "StupidModel";
	public static final String SPACE_ID = "space";
	public static final String GRID_ID = "grid";

	// Other definitions omitted
	// ...
}
```

These are simple strings, the only rule for these identificators is to be different.

After we have these definitions, first we assign the context ID by the `setId()` function:

```
@Override
public Context<Object> build(final Context<Object> context) {
	context.setId(Constants.CONTEXT_ID);
}	
```

As we written above contexts can also have `Projections` associated with them. A `Projection` takes the agents in a `Context` and imposes some sort of structure on them. We will use a `ContinuousSpace` and a `Grid`: both of them are projections, and they take agents and locate them in a continuous space and a matrix-like grid, respectively. In the `build()` method we are thus going to create these `Projection` instances and assign them to the `Context`.

To create a new space instance, first we need a space factory, this is done by creating a simple default factory by calling method `ContinuousSpaceFactoryFinder.createContinuousSpaceFactory()`. Creating a space may be a complex task, and in order to create an optimal representation (or for better performance) the function accepts _hints_. This is useful when creating e.g. multidimensional spaces. Here we specify `null` since the default settings are prefect for us.

Next, using this factory we can create a space instance by assigning:

  * its unique ID,
  * the context to associate the space with,
  * an instance of `Adder` that specifies how to add new elements to the space. We use a default implementation, the `RandomCartesianAdder` which adds new agents to the space to a random location.
  * an instance of `PointTranslator` that determines how to handle the border cells. We use default implementation, the `WrapAroundBorders` which makes the space a toroid one.
  * and lastly of course, the _X_ and _Y_ size of the grid.

> <b>Remark</b> There is two class here with the same name, both offering the same functionality, but `repast.simphony.space.continuous.WrapAroundBorders` is used for spaces and `repast.simphony.space.grid.WrapAroundBorders` is used for grids.

Creating the space instance may be done as follows:

```
final ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder
		.createContinuousSpaceFactory(null)
		.createContinuousSpace(
				Constants.SPACE_ID,
				context,
				new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(),
				Constants.GRID_SIZE, Constants.GRID_SIZE);
```

Creating a grid is similar to creating a space, except it has a utility class to assign its parameters called `GridBuilderParameters`. In this case, we use `SimpleGridAdder` which does not assigns any positions for the agents on the grid by default, they will be moved to their initial position later on. There is an additional logical parameter called `multi` that determines if each cell in the grid is single occupancy. The other parameters are identical.

```
final Grid<Object> grid = GridFactoryFinder
		.createGridFactory(null)
		.createGrid(
				Constants.GRID_ID,
				context,
				new GridBuilderParameters<Object>(
						new repast.simphony.space.grid.WrapAroundBorders(),
						new SimpleGridAdder<Object>(),
						false,
						Constants.GRID_SIZE, Constants.GRID_SIZE));
```


Creating the agents is an easy task after this: we create the specified number of agents by their defined constructor, and assign them to the context and to the grid to their random position where they were added initially. This is done with the `context.add()`, `space.getLocation()` and `grid.moveTo()` methods.

```
for (int i = 0; i < Constants.BUG_COUNT; ++i) {
	final Bug bug = new Bug(grid);
	context.add(bug);
	final NdPoint pt = space.getLocation(bug);
	grid.moveTo(bug, (int) pt.getX(), (int) pt.getY());
}

return context;
```

> <b>Remark</b> As the definition of the model did not specificied it in details, we allow multiple agents spawned on the same position in the first tick of the simulation. However, they are not allowed to move to occupied territories later on.

That is all! We have finished the implementation of _StupidModel 1_. The last task is to start the graphical interface and create some visualization through the point & click interface.

#### Configure Projections ####
This is the only part where a minor _"magic"_ is required. Open up the file `stupidmodel.rs/context.xml`. Repast Simphony includes a nice XML file editor, where the elements may be added through a graphical interface. After clicking on the _Source_ tab, the default content for the file should look something like this:

```
<context id="StupidModel"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:noNamespaceSchemaLocation="http://repast.org/scenario/context">
</context>
```

In order to let the observer (the Repast S GUI and its several wizards) know about the projections we added to our model, the following modifications must be done:

```
<context id="StupidModel"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:noNamespaceSchemaLocation="http://repast.org/scenario/context">
	<projection id="space" type="continuous space" />
	<projection id="grid" type="grid" /> 
</context>
```

Many important settings may be defined here, please refer to the Repast Simphony Java Getting Started Guide and to the Repast Documentation for the details.

#### Starting the Model ####
To start the simulation, click on the small black triangle pointing downwards next to the _Run_ button in the toolbar. As you can see, several run configurations are available. Actually, there are three different configurations created by default for each model: to run it in batch mode, run the model with GUI and create an exportable installer for your model (this requires at least 80 Mbytes of free space). The fourth configuration launches the Repast Simphony simulation GUI without any model initialized.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/start_stupid_model_first_time.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/start_stupid_model_first_time.png)

Click on the button of the _Model_. Next time clicking on the _Run_ button activates the previously launched run configuration, as well as using the _Ctrl + F11_ key combination, so it becomes easier to start the model.

This will start the Repast Simphony Runtime. Note that you may see some initial errors along the lines of `SpaceProjectionBuilder - Unable to build continuous space 'space' ...`. These can be ignored for now and in our next steps we are going to correct them. We need to do some runtime configuration before we run the model itself. We are going to setup the runtime to use our context builder to initialize the model and also create an initial display. We setup the runtime to use our context builder by specifying the data loader.

  1. In the _Scenario Tree_, right click on the _Data Loaders_ node and click _Set Data Loader_. If the tree is not visible use the tab controls on the left side of the runtime frame to select the _Scenario Tree_.
  1. In the _Select Data Source Type_ window, click on _Custom ContextBuilder Implementation_. Click _Next_.
  1. You should see the name of our context builder class in the combo box, `stupidmodel.StupidModelContextBuilder`, if not, use the combo box to find it. If the box is empty, go back and check your code. This typically means that there was a compilation error. Click _Next_.
  1. Click Finish.

You should now see `StupidModelContextBuilder` as the name of the _Data Loader_ (see figure below).

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/specify_context_builder.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/specify_context_builder.png)

The next task is to create a simple display to see the agents moving.

  1. Right-click on _Displays_ in the _Scenario Tree_ and click _Add Display_
  1. In the _Display configuration_ dialog, type Grid Display for name. Leave 2D as the type. Select the _grid_ projection as the one to display. Click on _grid_ in the _Projection and Value Layers_ section and then click the green right arrow. The projections on the right are the ones will be displaying and those on the left are all the possible projections to display.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation.png)
  1. Click _Next_.
  1. Select _Bug_ agents as the types to display. Do this by selecting it in the left and then clicking the green right arrow to move them to right. If we would have several agents displayed on the grid, the order how they would have been drawn on the display is from the bottom of this list to the up (e.g., the last element of the list on the right is drawn first and the first drawn last).
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation_assign_agents.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation_assign_agents.png)
  1. Click _Next_.
  1. In this panel, the appearance of `Bug` agents may be configured. This can be done programmatically by specifying a class that implements `StyleOGL2D` (we have not created such a class yet) or via a simple wizard. For the current model, we use the wizard to create a simple style for the agents. Click the button to the right of the style class combo box.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation_assign_agent_style.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation_assign_agent_style.png)
  1. A new dialogue, the _2D Shape Editor_ shows up where the default style of the agents may be customized, including the size, shape, color, icon, label and many more settings. Change the color to red by clicking the button with the blue square, and choosing a red color from the icon color dialog. Click _OK_ on the icon color box, then _OK_ on the _2D Shape Editor_ to modify settings.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation_modifying_default_agent_style.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/basic_grid_display_creation_modifying_default_agent_style.png)
  1. Click _OK_.
  1. If you would like, you can modify the outlook of the grid on this wizard page. Since the default settings are perfect for this demo model, click _Next_.
  1. If you would like, you can modify the refresh rate of the visualization on this wizard page. Since the default settings are perfect for this demo model, click click _Finish_.

The Repast Simphony Runtime has several icons on its toolbar. Click on _Save_ in order to save the created settings (i.e., the context builder and grid display settings). _Do not forget to save after modifying the visualization since Repast Simphony does not save them automatically upon exit._

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/repast_simphony_runtime_toolbars.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/repast_simphony_runtime_toolbars.png)

We are ready with the configuration of the first StupidModel version! Click on the _Initialize Run_ button to make the grid show up, and click _Start Run_ to allow agents start moving on the display.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/stupid_model_1_finished_screenshot.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/stupid_model_1_finished_screenshot.png)

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_05_26_model_01/) of the model is accessible from the proper SVN tag directly.

### Further Fun ###

  * It is quite easy to set different visualization settings, spend a little time with playing them:
    * Try setting an icon for the bugs. It is pretty easy and makes the model look nicer.
    * Set a custom background color for the simulation.
  * Try to modify the current implementation to allow multiple agents stay on the same territory.
  * When the new Repast Simphony project is instantiated, it created a several important directories. It represents a _suggested_ project layout, feel free to modify the layout if preferred, however, using conventions help other modelers understand your simulation, and make it running easier if the required resources may be found in the same places. Take some time to check the directory structure, and find a proper place to create a simple documentation for your model containing your name as the author and a link to the StupidModel documentation.
  * It is always good to know the API than to reinvent the wheel. Spend some time by checking out the utility methods offered by the Repast Simphony Java API, it can really save some time since they offer solutions and covers most of the common tasks.
  * There are alternative ways to access any grid or space elements (in particular, any defined _projection_) through the context. In this version of the model, we directly passed a reference to the created agents. Can you figure out how to access the same grid instance with the defined `GRID_ID` through the context in which the agent is? **Hint:** An agent's context may be accessed anytime with a call to `ContextUtils.getContext(this)`.
  * Try the simulation out with different different vision ranges for the agents. Run the model with different number of agents to check they are moving correctly.

## Bug Growth ##
### Purpose ###
Illustrate adding instance variables and methods to the agents.
### Formulation ###
  * Add a second bug action, `grow`. Each time step, a bug grows by a fixed amount, `1.0`. So bugs need an instance variable for their size, which is initialized to `1.0`. This action is scheduled after the `move` action.
  * The bugs' color on the display is shaded to reflect their size. Bug colors shade from white when size is zero to red when size is `10` or greater.

### Implementation Notes ###

#### Extending Agents with New Attributes and Actions ####
Adding a new field and action to the `Bug` agenst should be an easy task. First we declare a new constant for bug growth rate as the description specifies:

```
public final class Constants {
	public static final double BUG_GROWTH_RATE = 1.0;
	
	// ...
}
```

Next we extend the `Bug` class with a member variable called `size` and a getter function for the field. We also create a new function called `grow()` and annotate it as an action. The only issue here is the `priority` attribute of the `@ScheduledMethod` annotation: using this attribute the order of actions can be specified with regards to other actions scheduled for the same tick. The action with greater value is activated first, so we assign `0` to `step()` and `-1` to `grow()`.

```
public class Bug {
	private double size = 1.0;

	// ...

	public double getSize() {
		return size;
	}

	@ScheduledMethod(start = 1, interval = 1, priority = 0)
	public void step() {
		// ...
	}

	@ScheduledMethod(start = 1, interval = 1, priority = -1)
	public void grow() {
		size += Constants.BUG_GROWTH_RATE;
	}

	// ...
}
```

We also improve some other parts of the class. First, we modify the constructor to show how to acquire a reference of a projection from the context the agent is associated with through a utility function called `getGrid()`. This is why the different projection IDs are so important. The `toString()` method is updated as well and it is extended to reflect the `size` attribute too.

```
public Bug() {
	super();
}

public Grid<Object> getGrid() {
	return (Grid<Object>) ContextUtils.getContext(this)
			.getProjection(Constants.GRID_ID);
}

// ...

@Override
public String toString() {
	// This may happen when testing
	final String location = (ContextUtils.getContext(this) != null) ? getGrid()
			.getLocation(this).toString() : "[?, ?]";

	// Override default Java implementation just to have a nicer
	// representation
	return String.format("Bug @ location %s, size=%f", location, size);
}
```

Do not forget to update the constructor calls and remove the unnecessary `grid` parameter in the `build()` function of `StupidModelContextBuilder`.

#### Creating a Custom Agent Display Style ####

This may seem a bit low-level programming due to the colors but unfortulately it is unavoidable when dealing with rainbow colors.

For a new custom agent display style, create a new class `stupidmodel.observer.BugStyleOGL2D` that extends `DefaultStyleOGL2D`. It has several default implementations already for the common methods (as abstract classes usually have), we have to deal only with the `getColor()` function currently. After getting the `size` attribute of the agent, we create a color with maximum red component (`0xFF` == `255`) and a scaled `strength` value as follows:

```
public class BugStyleOGL2D extends DefaultStyleOGL2D {
	@Override
	public Color getColor(final Object agent) {
		if (agent instanceof Bug) {
			final Bug bug = (Bug) agent;

			final int strength = (int) Math.max(200 - 20 * bug.getSize(), 0);
			return new Color(0xFF, strength, strength); // 0xFFFFFF - white,
								    // 0xFF0000 - red
		}

		return super.getColor(agent);
	}
}
```

After starting up the simulation, edit the settings of _Grid Display_ by double-clicking on it and select `BugStyleOGL2D` for `Bug` agents in the _Style Class_ drop-down menu at the _Agent Style_ tab.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/edit_grid_display_use_custom_bugstyle_implementation.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/edit_grid_display_use_custom_bugstyle_implementation.png)

Click _Ok_.

We are ready with the configuration of the second StupidModel version! Click on the _Initialize Run_ button to make the grid show up, and click _Start Run_ to allow agents start moving on the display. If everything goes well the agents should be a bit transparent in the beginning:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/styled_bug_agent_screenshot_beginning.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/styled_bug_agent_screenshot_beginning.png)

And become absolutely red after a few simulation ticks:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/styled_bug_agent_screenshot_end.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/styled_bug_agent_screenshot_end.png)


> <b>Remark</b> Please do not forget to use the _Save_ button in the toolbar, otherwise your settings will be discarded upon exit.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_05_28_model_02) of the model is accessible from the proper SVN tag directly.

### Further Fun ###
  * Try to schedule the `grow()` and `move()` actions with the same priority, run the model and check in which order are the actions executed.
  * Try to create different rainbow color maps for the agents.
  * Agents' `size` property is changed by the same fixed number. Try to introduce minor heterogeneity through randomizing the `grow()` action: make the `size` property increased by a random value between the interval `[0.5, 1.5]`.
  * Try to create specific labels displaying the `size` value for the agents through the `BugStyleOGL2D` implementation (**Hint:** override the `getLabel()` function).

## Habitat Cells and Resource ##
### Purpose ###
Show how to create cell objects that represent habitat and spatial resources. Illustrate how agents and habitat cells interact.
### Formulation ###
  * A new class, `HabitatCell`, is added. Habitat cell objects have instance variables for their `foodAvailability` and `maximumFoodProductionRate`. Cells also have a variable for the bug at their location.
  * The grid space object now holds habitat cells, not bugs.
  * Food availability is initialized to `0.0`, and maximum food production rate is initialized to `0.01`.
  * Each time step, food availability is increased by food production. Food production is a random floating point number between zero and the maximum food production.
  * Food production is scheduled before agent actions.
  * Bug growth is modified so growth equals food consumption. Food consumption is equal to the minimum of (a) the bug's maximum consumption rate (set to 1.0) and (b) the bug's cell's food availability.
  * The food consumed by each bug is subtracted from the food availability of its cell.

### Implementation Notes ###

In this part of the tutorial, we demonstrate how to use a grid value layer. A grid may have any number of `IGridValueLayer` instances associated, in this current setup we use a default implementation, `GridValueLayer` where each of the cells in the grid is accessed through coordinates. Each cell in the grid value layer will contain a simple numeric value (the _available food_ in this model).

First things first, we declare a new ID again for the value layer, so we can use the `Context` class to look it up anywhere in the model (as we done it in the previous model). To do so, add the following definition to the `Constants` class.

```
public final class Constants {
	// ...	

	public static final String FOOD_VALUE_LAYER_ID = "foodValueLayer";
	
	// ...
}
```

#### Creating HabitatCell Agents ####

Creating a class for the `HabitatCell` agents would be easy for now. Cells know their `x` and `y` coordinates (`final` variables since they will not change during the simulation), and an instance variable for the `foodAvailability` at the current cell.

To create the new agent action `growFood()`, we should create a small table to keep track of the order of actions (we are going to use a clearer approach in the next models, but it is vital for now to keep an overview of how the interactions of the model ordered). Keep in mind that higher priority actions scheduled for the same tick are always executed before the ones with lower priority.

| **Tick** | **Agent**       | **Action**     | **Priority** |  **Order**           |
|:---------|:----------------|:---------------|:-------------|:---------------------|
| All      | `HabitatCell`   | `growFood()`   |          1   | First in each tick   |
| All      | `Bug`           | `move()`       |          0   |                      |
| All      | `Bug`           | `grow()`       |         -1   | Last in each tick    |

The `HabitatCell` agents during their actions increase their food availability value with a random value between the interval specified, query the grid value layer that contains the food availability map and updates the corresponding cell data. The whole agent definition in one listing:

```
public class HabitatCell {
	private static final double maximumFoodProductionRate = 0.01;

	private final int x, y;
	private double foodAvailability = 0.0;

	public HabitatCell(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public double getFoodAvailability() {
		return foodAvailability;
	}

	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void growFood() {
		foodAvailability += RandomHelper.nextDoubleFromTo(0.0,
				maximumFoodProductionRate);

		final GridValueLayer foodValueLayer = (GridValueLayer) ContextUtils
				.getContext(this).getValueLayer(Constants.FOOD_VALUE_LAYER_ID);

		foodValueLayer.set(getFoodAvailability(), x, y);
	}

	public void foodConsumed(final double eatenFood) {
		foodAvailability -= eatenFood;
	}

	@Override
	public String toString() {
		return String.format(
				"HabitatCell @ location (%d, %d), foodAvailability=%f", x, y,
				foodAvailability);
	}
}
```

We also extended the class by a utility method `foodConsumed()` that decreases the available food level of the cell by the specified `eatheFood` value, and a simple `toString()` method (that is used only for debugging).

#### Updating Bug Actions Accordingly ####

Next we have to modify the `Bug` agent actions as the model specifies.

First, we need a function that returns the underlying cell for an agent:

```
private HabitatCell getUnderlyingCell() {
	final GridPoint location = getGrid().getLocation(this);
	final Iterable<Object> objects = getGrid().getObjectsAt(
			location.getX(), location.getY());

	for (final Object object : objects) {
		if (object instanceof HabitatCell) {
			return (HabitatCell) object; 
		}
	}

	return null;
}
```

This method queries the objects located at the current position of the `Bug` agent through the `grid.getObjectsAt()` function. Then it iterates through the objects at the specified location, and returns the first `HabitatCell` instance it encounters (since there is _exactly one_ `HabitatCell` for each location `null` is never returned).


We also create a function to determine the possible food consumption level called `foodConsumption()`:

```
private final double maxConsumptionRate = 1.0;

private double foodConsumption() {
	final HabitatCell cell = getUnderlyingCell();
	final double foodAvailable = cell.getFoodAvailability();

	final double eatenFood = Math.min(maxConsumptionRate, foodAvailable);
	cell.foodConsumed(eatenFood);

	return eatenFood;
}
```

This function determines the maximum level of food that can be consumed, decreases the food level of the cell under the current `Bug` agent, and returns the evaluated value. This is useful since we can modify our existing `grow()` method to be a simple one-liner:

```
@ScheduledMethod(start = 1, interval = 1, priority = -1)
public void grow() {
	size += foodConsumption();
}
```

That is all!

#### Updating the Context Builder ####

The last thing to do is to extend the custom context builder implementation in two ways:

  1. Create the grid value layer instance. It is similar to creating the `grid` and `space` objects, but there is no need to additional utility and factory methods and/or classes. Simply call its constructor with a valid unique ID, a `GridPointTranslator` instance to handle the borders, and its `x` and `y` size. The second parameter is a logic variable indicating if the grid is either _dense_ or _sparse_ (major performance boost may be achieved by using the proeper option). Since we have a `HabitatCell` instance on each cell, we annotate it as a _dense_ layer by using specifying `true` as its value.
  1. The other task is to create the `HabitatCell` instances, add them to the context and assign the initial values for the `foodValueLayer`.

Append the following code listing to the end of the `build()` method of the class `StupidModelContextBuilder`:

```
@Override
public Context<Object> build(final Context<Object> context) {
	// ...

	final GridValueLayer foodValueLayer = new GridValueLayer(
			Constants.FOOD_VALUE_LAYER_ID,
			true,
			new WrapAroundBorders(),
			Constants.GRID_SIZE,
			Constants.GRID_SIZE);

	context.addValueLayer(foodValueLayer);

	for (int i = 0; i < Constants.GRID_SIZE; ++i) {
		for (int j = 0; j < Constants.GRID_SIZE; ++j) {
			final HabitatCell cell = new HabitatCell(i, j);
			context.add(cell);
			grid.moveTo(cell, i, j);
			foodValueLayer.set(cell.getFoodAvailability(), i, j);
		}
	}

	return context;
}
```

Also modify the creation of the grid a bit: since we may have both `Bug` and `HabitatCell` agents on it in the same position, we have to make it a multi-occupancy instead of a single-occupancy grid. Do this by specifying `true` instead of `false` as the `multi` parameter as noted below:

```
final Grid<Object> grid = GridFactoryFinder
		.createGridFactory(null)
		.createGrid(
				Constants.GRID_ID,
				context,
				new GridBuilderParameters<Object>(
						new repast.simphony.space.grid.WrapAroundBorders(),
						new SimpleGridAdder<Object>(),
						// Each cell in the grid is multi-occupancy from now
						true,
						Constants.GRID_SIZE, Constants.GRID_SIZE));
```

#### Visualizing the Resources ####

We create a new custom style for the value layer, as we created one for the `Bug` agents as well. Create a new class in the `stupidmodel.observer` package called `FoodValueLayerStyleOGL`, and implement the `ValueLayerStyleOGL` interface which is the superclass of all value layer styles. This interface declares the following methods:

  * During `init()`, the class gets a reference to the `ValueLayer` instance it manages (practically, this will be the `foodValueLayer` instance we create during the `build()` at the `StupidModelContextBuilder`).
  * The `getCellSize()` function should return a `float` value that specifies the size of the painted cells on the GUI.
  * The `getColor()` function should return a simple `Color` value for the specified coordinates.

Our implementation keeps a reference for the specified `ValueLayer` instance in a member variable, returns a default `15.0f` value for the cell size (Repast Simphony uses a grid with a width and height of 15 pixels by default, but that can be changed when creating or editing a display), and returns a heavily scaled color value between black and green based on the actual food level at the specified location.

```
public class FoodValueLayerStyleOGL implements ValueLayerStyleOGL {
	private ValueLayer layer = null;

	@Override
	public void init(final ValueLayer layer) {
		this.layer = layer;
	}

	@Override
	public float getCellSize() {
		return 15.0f;
	}

	@Override
	public Color getColor(final double... coordinates) {
		final double food = layer.get(coordinates);
		final int strength = (int) Math.min(200 * food, 255);
		return new Color(0, strength, 0); // 0x000000 - black,
						  // 0x00FF00 - green
	}
}
```

After starting up the simulation again, the new value layer style may be chosen in the options of the grid display by double-clicking on it:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/assigning_grid_value_layer.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/assigning_grid_value_layer.png)

> <b>Remark</b> Version Repast Simphony 2.0-beta has a known issue about specifying value layer styles. Please consult [this thread](http://old.nabble.com/I-can't-see-%22Value-Layer-Details%22-when-I-create-a-Display-td31594781.html) if you encounter any problems.

If everything goes well, the simulation should look like something like this:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_03.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_03.png)

### Further Fun ###
  * Try increasing the `maximumFoodProductionRate` to a relatively high value (e.g. set it to `10.0`). Make some experimental runs, and check what you see and how it fits your expectations
  * Try increasing the `maxConsumptionRate` value as well, and see how it modifies the simulation.
  * Try using some other color values to display food availability for the grid.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_05_29_model_03) of the model is accessible from the proper SVN tag directly.

## Cell and Bug Probes ##
### Purpose ###
Show how to make model objects probeable from the display.
### Formulation ###
Make the bugs, and the cells, so they can be probed via mouse clicks on the display.

### Implementation Notes ###
Using the standard Repast Simphony Java API, it is pretty easy to make an agent class probeable from the display. In the following example, we will add the possibility to change the `size` of `Bug` agents from the Repast Runtime GUI.

To be able to set the `size` property, create both a conventional getter and setter method for it, and annotate one of the methods by the `@Parameter` as the following code listing shows:

```
public class Bug {
	private double size = 1.0;

	@Parameter(displayName = "Bug Size", usageName = "size")
	public double getSize() {
		return size;
	}

	public void setSize(final double size) {
		this.size = size;
	}
	
	// ...
}
```

The used `@Parameter` annotation requires at least two parameters: the name which is displayed on the GUI (`displayName`), and the name of the member it reflects (`usageName`).

#### Probing the agents ####
Start up the Repast Simphony Runtime and press the initialize button. Then, find a `Bug` agent and double-click on it. In the bottom-left part of the window two new tabs should be shown:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/probing_general_screenshot.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/probing_general_screenshot.png)

The upper one shows the string representation of the bug clicked on, the text field allows to modify the size of the agent (since we used the `@Parameter` annotation on this field), and the location values of the bug is also shown on the shown tab (both for the `space` and for the `grid`).

The lower one shows the same data, but for the cell under the bug on the same `grid` position. Note that there is no getter/setter method and member annotated with `@Parameter`, so there is no textfield to modify anything.

Another interesting detail to notice is the little red line on both tabs: the Repast Simphony Runtime can track the changes of these values during time. Run the simulation, change the value sometime when the simulation is paused and see how the line changes and shows the trend how the value changed:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/timeseries_for_probed_agent_properties.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/timeseries_for_probed_agent_properties.png)

### Implementation Notes ###

### Further Fun ###
  * Following the example above, can you create a way to modify the location of a `Bug` agent on demand?
  * There are several other ways how the `@Parameter` annotation may be used in the future versions of the model. Try to utilize it after you finished the tutorial.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_05_29_model_04) of the model is accessible from the proper SVN tag directly.

## Parameters and Parameter Displays ##
### Purpose ###
Show howto define variables as parameters, and how to put parameters in the parameter settings window that appears at startup.
### Formulation ###
Make these variables into parameters that can be accessed through the settings window:
  * Initial number of bugs (a model parameter)
  * The maximum daily food consumption (a bug parameter), and
  * The maximum food production (a cell parameter).

### Implementation Notes ###

So far we have used dedicated constants to specify the values enumerated in the formulation of this model. This way it is easier to modify them, however, it still requires to restart a simulation if any of them is modified to view the effect of the new value on the simulation. By defining these values as _model specific parameters_, it becomes really easy to modify them on the GUI and evaluete their effect. Using model parameters is also the proper way to create parameter sweeps through batch runs.

#### Creating Model Parameters ####

To convert these values into model parameters, start up the Repast Simphony Runtime. Use the tabs in on the left side of the GUI (where the _Scenario Tree_ is) to select _Parameters_ panel, and click on the _Add Parameter_ button at the bottom of it.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/parameters_tab_location.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/parameters_tab_location.png)

This will bring up the _Add Parameter_ dialogue where the following properties may be set for a new parameter:

  * _Name:_ A unique identifying name for this parameter. Each model parameter should have a unique name.
  * _Display Name:_ The label that will be used in the _Parameters_ panel for this model parameter. This does not have to be unique, however, it is recommended to avoid confusion.
  * _Type:_ This can be any of `int`, `long`, `double`, or `string`, but the fully qualified name of any other type may also be used.
  * _Default Value:_ The initial value of the parameter.
  * _Converter:_ _(Optional)_ The name of a class that extends `StringConverter`. A `StringConverter` can convert non-standard types to and from a `String`. This is not necessary unless you use a type other than `int`, `long`, `double` or `String`.
  * _Values:_ _(Optional)_ A space-separated list of values of the chosen type. The parameter will be restricted to these values.

To add the required model parameters, follow these steps:

  1. Type _bugCount_ for the _Name_.
  1. Type _Initial Number of Bugs_ for _Display Name_.
  1. Type _int_ for the _Type_.
  1. Type _100_ for the _Default Value_.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_parameter_bugCount.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_parameter_bugCount.png)

Presss _OK_. From now on, when the GUI is launched or when the model is reseted, the new parameter is assigned the default value specified previously (`100`), and it can be changed before the model initialization on the _Parameters_ tab, right below the _Default Random Seed_ value.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_parameter_bugCount_added.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_parameter_bugCount_added.png)

However, it has currently no effect on the model. Let's modify the initialization to use the new parameter instead of the dedicated `BUG_COUNT` constant.

> <b>Remark</b> If you save the current configuration on the GUI and check the `StupidModel.rs/parameters.xml` file, you can see the following entry added:
```
<parameter name="bugCount" displayName="Initial Number of Bugs" type="int" 
		defaultValue="100" 
		isReadOnly="false" 
		converter="repast.simphony.parameter.StringConverterFactory$IntConverter" />
```
> Advanced users may utilize this so they can edit this file directly (either by hand or programmatically) instead of using the point & click wizard on the GUI.

Save and exit the GUI for now. Next, we utilize our brand new parameter programmatically from the simulation.

First, edit the `Constants` class, and remove `BUG_COUNT` (since we replace it with a parameter). The code will show some errors, but that is fine for now, we will fix it immediately. However, we used an ID for the parameter (`bugCount`), it is a good idea to declare a new `String` constant to avoid errors caused by typos in the name:

```
public final class Constants {
	// ...

	public static final String PARAMETER_ID_BUG_COUNT = "bugCount";
}
```

Now, the interesting part about how to access the current parameter values set by the user. Well, each parameter has a different meaning, it may have sense for some of them to modify during the run (e.g., to allow direct supervised experiments), but the `bugCount` parameter has an influence only during initialization. Open the `StupidModelContextBuilder` class and extend it with the following segment:

```
@Override
public Context<Object> build(final Context<Object> context) {
	context.setId(Constants.CONTEXT_ID);

	final Parameters parameters = RunEnvironment.getInstance()
				.getParameters();

	// ...
}
```

The `RunEnvironment` class is a singleton class that provides access to the environment in which a particular model run executes. For example, the schedule is made available to running simulation code in this way. The current set of model parameters and their values may be accessed through this class as well, in a form of a `Parameters` class. The role of this class is to encapsulate model parameters for the simulation, and offers several handy utility functions for this task.

The creation of `Bug` agent is currently done as follows:

```
for (int i = 0; i < Constants.BUG_COUNT; ++i) {
	final Bug bug = new Bug();

	// ...
}
```

Instead of using the dedicated constant (which is already removed previously), we query the current parameter value of `bugCount`. This can be done by a `getParameter()` function call on the `parameters` instance we got a reference on previously, by specifying the parameter name we assigned to it. This returns an `Object` by default, but we can cast it to an `Integer` (since we defined the parameter as `int`, we will get its wrapper class, `Integer`). The current `int` value is then accessed by the `intValue()` function:

```
final int bugCount = ((Integer) parameters
		.getValue(Constants.PARAMETER_ID_BUG_COUNT)).intValue();

for (int i = 0; i < bugCount; ++i) {
	final Bug bug = new Bug();
	
	// ...
}
```

Other parameters (like rational numbers or string) are handled absolutely in the same way, identically.

#### Creating Agent Parameters ####

This task is just identical to the method described in section [Cell and Bug Probes](#Cell_and_Bug_Probes.md). Just remove the `final` modifier, create simple a getter and setter for the required fields, and add a proper `@Parameter` annotation to either of them.

The modification of the `Bug` class:

```
public class Bug {
	// ...
	
	private double maxConsumptionRate = 1.0;

	@Parameter(displayName = "Bug maximum food consumption rate", usageName = "maxConsumptionRate")
	public double getMaxConsumptionRate() {
		return maxConsumptionRate;
	}

	public void setMaxConsumptionRate(final double maxConsumptionRate) {
		this.maxConsumptionRate = maxConsumptionRate;
	}

	// ...
}
```


The modification of the `HabitatCell` class:

```
public class HabitatCell {
	// ...
	
	private double maximumFoodProductionRate = 0.01;

	@Parameter(displayName = "Cell maximum food production rate", usageName = "maximumFoodProductionRate")
	public double getMaximumFoodProductionRate() {
		return maximumFoodProductionRate;
	}

	public void setMaximumFoodProductionRate(double maximumFoodProductionRate) {
		this.maximumFoodProductionRate = maximumFoodProductionRate;
	}

	// ...
}
```

> <b>Remark</b> Checking if the parameter values are valid is advisable in the setter methods, but for simplicity, they were omitted from the code listings. The full source code contains a version that checks if the parameters are non-negative.

The agent parameters may be modified by double-clicking on the agents on the graphical interface.

### Further Fun ###
  * Can you find a way to pause the simulation in the _100th_ tick through the API offered by a class introduced in this part of the tutorial? This knowledge comes handy for the [7th section](#Stopping_the_Model.md) (maybe you can do it on your own without even taking a look on the next section!).
  * We defined the maximum daily food consumption and the maximum food production as a bug and a cell parameter, respectively. It is very easy and straightforward to convert these parameters into model-level parameters, just like the `bugCount` parameter. Refactor and modify the code to make this two values model parameters as well.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_05_29_model_05) of the model is accessible from the proper SVN tag directly.

## Histogram Output ##
### Purpose ###
Illustrate how to add graphs to the display. Provide the ability to see the size distribution of the agents.
### Formulation ###
Add a histogram display showing the number of agents in each size class. (It works reasonably well to use 10 bins, set minimum to zero, and set maximum to 10.)

### Implementation Notes ###

The model is in a state where no additional programming is required to create visualizations, so there is no Java code for this part ;]

In Repast Simphony, we can define different _data sets_ above the simulation which can be saved, displayed on a chart, and/or used to create other data sets in the same time. Think of it like an aggregated and sorted set of data.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_datasets_charts.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_datasets_charts.png)

#### Data Collection ####

We need a histogram, so first we have to _collect the data_ that will be shown in the chart:

  1. Right-click on _Data Sets_ in the _Scenario Tree_, and select _Add Data Set_ from the menu.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_first_data_set.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_first_data_set.png)
  1. The _Data Set Editor_ shows up. On the first page, we can assign a name for the new data set. Type _Data Set of Bug Sizes_ as its name, and select _Bug_ as the _Agent Class_.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/creating_histogram_dataset.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/creating_histogram_dataset.png)
  1. The next wizard page allows us to create different _mappings_ for the dataset, i.e., declare what kind of data should it produce. Basically, there are simple mappings (e.g., a sequence of all agent variable values), aggregated mappings (e.g., sum, min, max, variance of values), and custom mappings that can be created through a separate wizard by implementing them e.g. in BeanShell. Please refer to the Repast Documentation about the details of this topic. For the current model, we need a simple sequence of values (i.e., the enumeration of `size` values of the agents). Press _Add_ and select _Add Simple Mapping_.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/select_histogram_simple_mapping.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/select_histogram_simple_mapping.png)
  1. A new line in the table above the buttons should appear. Click on the _Source_ component of the new line, and a drop-down list should appear. Select `getSize()` from the list. This automatically updates the _Column Name_ attribute as well.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_histogram_simple_mapping.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_histogram_simple_mapping.png)
  1. Click on _Next_.
  1. If you would like, you can modify the refresh rate of the visualization on this wizard page (like when we created the grid visualization). Since the default settings are perfect for this demo model, click click _Finish_.

If everything goes well, the new dataset should be displayed under the _Data Sets_ section of the _Scenario Tree_ and is ready to use!

#### Creating a Chart ####

  1. Right-click on _Charts_ in the _Scenario Tree_, and select _Add Chart_ from the menu.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_a_new_chart.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_a_new_chart.png)
  1. Select _Statically Sized Histogram_ from the list.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/select_statically_sized_histogram.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/select_statically_sized_histogram.png)
  1. Type _Histogram of Bug Sizes_ for the name of the new chart. Also make sure _Data Set of Bug Sizes_ and _Size_ is automatically selected as _Data Set_ and _Value Column_, respectively.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/histogram_data_mappings.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/histogram_data_mappings.png)
  1. Click _Next_.
  1. On the _Bin Information_ wizard page we can set the data bins the chart should display. Enter _10_ as _Number of bins_, _0_ as _Minimum Value_ and _10_ to _Maximum Value_, as the problem formulation specified the histogram.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/histogram_bin_information.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/histogram_bin_information.png)
  1. Click _Next_.
  1. The data mapping is complete, press _Finish_ to add the new chart.

If everything goes well, the new chart should be displayed under the _Charts_ section of the _Scenario Tree_ and is ready to use!

After initializing the model, the new histogram should be displayed behind the grid we created previously in a different tab.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/location_of_the_new_histogram.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/location_of_the_new_histogram.png)

Start the simulation, and see how the new chart shows the size distribution of the agents!

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_06.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_06.png)

### Further Fun ###
  * The current model allows `Bug` agents to grow greater than `10.0`, however the current chart settings does not show the outliers. Can you modify the chart to do so?

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_08_model_06) of the model is accessible from the proper SVN tag directly.

## Stopping the Model ##
### Purpose ###
Show how to cause a model to stop itself upon a certain condition. Show how to "clean up" when a model stops.

### Formulation ###
  * The model stops when the largest bug reaches a size of 100.
  * Close the graphic windows (and do any other cleanup steps) when the program stops.

### Implementation Notes ###
This model version is a simple one, we just have to modify the `grow()` action method for the `Bug` agent class . One way to do this could be to simply call the usual `System.exit(0)` method, like in any Java applications. However, there is a nicer way that allows us to visually observe the end of the simulation by using the class `RunEnvironment` which we already introduced at the section about [Creating Model Parameters](#Creating_Model_Parameters.md).

Simply add the following conditional statement to the agent action:

```
@ScheduledMethod(start = 1, interval = 1, priority = -1)
public void grow() {
	size += foodConsumption();

	if (size > 100.0) {
		RunEnvironment.getInstance().endRun();
	}
}
```

### Further Fun ###
  * The `RunEnvironment` class has several other useful utility functions. Try to modify the model to pause (this stops the model in a way that it may be resumed either programmatically or by pressing the run button) when either one of the bug size reaches `50.0` or the `HabitatCell` food availability reaches `25.0`.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_09_model_07) of the model is accessible from the proper SVN tag directly.

## File Output ##
### Purpose ###
Show how to write results to an output file. Illustrate how to iterate over a list.
### Formulation ###
Each time step, write the _minimum_, _mean_, and _maximum bug size_ on one line of an output file.

### Implementation Notes ###
This task may be done several ways, e.g., by creating a `save()` action and using the standard Java API like a simple `PrintWriter`, iterating through the list of all agents and evaluating the min, max and mean elements by hand.

However, Repast Simphony offers a bit friendlier way for these tasks through _Outputters_. To utilize this feature, we have to tasks: create a new _Data Set_ to define the data to save (as we did for the [Histogram Output](Histogram_Output.md) model).

#### Define a New Data Set of File Output ####
Create a new _Data Set_ called _Data Set of File Output_ over the `Bug` agent class. In the output file, we would like to see the following columns:

  1. The current _tick_ of the simulation
  1. The minimum size of `Bug` agents
  1. The maximum size of `Bug` agents
  1. The average size of `Bug` agents

On the _Agent Mappings_ page, add a _Simple Mapping_ for the _current tick_. Creating the min/max/mean for the bug sizes requires a few more clicks :-) Click on _Add_ and add a new _Aggregate Mapping_. Select `getSize()` as `Method` and `Min` as its function. This way Repast Simphony iterates through all `Bug` agents currently assigned to the simulation context when the data set is evaluated, conctructs a collection from all of their `size` value and queries the minimal element.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_aggregate_mapping_for_min_bug_size.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/create_aggregate_mapping_for_min_bug_size.png)

Press _OK_, and name the new column by double-clicking on the _Column Name_ entry and type _Min_.

Repeat these steps and add the _Max_ and _Mean_ aggregated values for the new mapping. When finished, the page should look like something similar:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/constructing_file_outputter_agent_mappings.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/constructing_file_outputter_agent_mappings.png)

Press _OK_ then _Finish_ to create the new dataset.

#### Define a New File Outputter ####

Right-click on _Outputters_ in the _Scenario Tree_, and select _Add File Outputter_ from the menu.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_new_file_outputter.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_new_file_outputter.png)

Select _Dat Set of File Output_ as _Data Set Id_. Note that the _File Columns_ list is populated with the available elements. Add all of the needed columns in the list to the right to specify which ones to save into the file.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_file_outputter_columns.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_file_outputter_columns.png)

Click on _Next_. On the new wizard page several outputter settings may be specified, e.g., to include headers in the output file, append to the file instead of deleting its contents if it exists, and so on. It is also possible to append different postfixes (like the timestamp) to the end of the file to prevent accidental data loss.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/specific_file_output_settings.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/specific_file_output_settings.png)

Click finish, and the new _File Outputter_ should appear under the _Outputter_ section of the _Scenario Tree_.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_file_outputter_location.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_file_outputter_location.png)

It is all done!

Initialize the simulation, and let it run until it stops: it should stop as we defined a stopping condition previously when one of the agents reach the size of `100`.


> <b>Remark</b> By default, Repast Simphony places the output files into the project root of the simulation. However, it requires an explicit _Refresh_ to see the new file in the workspace. Press _F5_ when the project is selected in the _Project Explorer_ of Repast Simphony, or simply right-click and select refresh from the popup-menu.

> ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/refresh_icon_location.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/refresh_icon_location.png)

The new file should appear in the resources of the project:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_model_output_file.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_model_output_file.png)

Open up the automatically created output file and verify its contents. See the header of the file for the order of columns, and check if it the last row of the file contains a _Max_ value above `100`.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_08.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_08.png)

### Further Fun ###
  * Are you able to create a new file outputter for `HabitatCell`, and both display and save the sum of available food levels?
  * Check if the created output files can be opened in Excel and recognized as a usual CSV data. If not, try to change the _delimeter_ to `;` for the outputter.
  * Could you create a simple action in the `StupidModelContextBuilder` that iterates through all agents and save the data through the standard Java API (e.g., by a `PrintWriter` instance).

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_10_model_08/) of the model is accessible from the proper SVN tag directly.

## Randomized Agent Actions ##
### Purpose ###
Show how to randomize the order in which agents execute an action.
### Formulation ###
The bugs' _move_ action is altered so that the order in which bugs execute the action is shuffled each time step.

### Implementation Notes ###
> <b>Remark</b> Repast Simphony executes actions scheduled for the same time step with the same priority in a randomized order by default, so there is nothing to modify in the model to complete this model. However, we perform some minor refactor in order to improve the code quality, and make the implementation of the following models a bit easier.

In the [previous versions of the model](#Creating_HabitatCell_Agents.md) we used several different schedules. It was really easy to create actions through the `@ScheduleMethod` annotation, however, these decentralized definitions make harder to keep in mind the order how the actions are executed, modify the order of execution is a hard and may be an error-prone task. For instance, we needed a table for even such a small model like the StupidModel to keep track of the executed actions and their priorities.

In order to avoid such issues, it is generally a good practice to create a simple method that executes actions sequentially in the required order.

First of all, remove all `@ScheduleMethod` annotations from the `Bug` class (specifically before the `step()` and `grow()` methods), we replace them with a single scheduled method and execute these actions in the required order within a new function.

Add the following new method declaration to the `StupidModelContextBuilder`:

```
@ScheduledMethod(start = 1, interval = 1, priority = 0)
public void activateAgents() {
	final ArrayList<Bug> bugList = getBugList();

	SimUtilities.shuffle(bugList, RandomHelper.getUniform());

	for (final Bug bug : bugList) {
		bug.step();
	}

	for (final Bug bug : bugList) {
		bug.grow();
	}
}
```

This way we have a direct control over the simulation flow. Of course, it is not always solvable, but comes handy when extending the model.

There is only one thing to notice, a new function called `getBugList()` which queries the current root context for all of the `Bug` instances. Of course, there are several ways to get a list of all agents (e.g., by creating a list during the context initialization and keep a reference for all of the created agents in it). However, our goal with this tutorial is to demonstrate the usage of the standard Repast Simphony Java API, and show general solutions for such tasks that can be used everywhere in the simulation code.

So, in order to get the current list of agents, we first get a refenrece to the current master context (since we have only that context) from the `RunState` instance assigned to the simulation, and query all of the `Bug` agents by specifying their class instance to the `getObjects()` function. We simply create a list by iterating through the result and return it at the end of the function.

```
private ArrayList<Bug> getBugList() {
	@SuppressWarnings("unchecked")
	final Iterable<Bug> bugs = RunState.getInstance().getMasterContext()
			.getObjects(Bug.class);
	final ArrayList<Bug> bugList = new ArrayList<Bug>();

	for (final Bug bug : bugs) {
		bugList.add(bug);
	}

	return bugList;
}
```

### Further Fun ###
  * In some of the cases it may be important to execute agent actions in a specific fix order. Since the actions are executed in a randomized order by default, the above described method may be used to create a fix order how agent actions should be executed. Can you make agents perform their action based on an arbitrary fix order in each turn (e.g., based on their default `hashCode()` value)?
  * Note that the `HabitatCell` actions are still handled in a different `@ScheduleMethod` definition. Can you modify the code and merge the activation of `HabitatCell` agents into the new `activateAgents()` definition?

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_10_model_09/) of the model is accessible from the proper SVN tag directly.

## Sorted Agent Actions ##
### Purpose ###
Show how to sort a list of agents, and cause an agent action to be executed in `size` order.
### Formulation ###
  * The list of bugs is sorted by _descending size order_ at the start of each time step.
  * The bugs' _move_ action is un-randomized so it is executed in descending size order.

### Implementation Notes ###
In the [previous version of the model](#Randomized_Agent_Actions.md) we made a little modifications regarding to the agent actions. We can utilize this to implement sorted agent actions.

First we need to define a default ordering over the `Bug` agents. In Java this is done by implementing either a custom `Comparable` or `Comparator` instance. For simplicity, we implement the Comparable interface with the `Bug` by comparing the `size` attribute of the agents in the standard way.

```
public class Bug implements Comparable<Bug> {
	// ...

	@Override
	public int compareTo(final Bug other) {
		return -Double.compare(size, other.size);
	}
}
```

If you are not familiar with these features of the Java language, please refer either to the [Java Tutorial](http://download.oracle.com/javase/tutorial/collections/interfaces/order.html) or the Java Documentation of [Comparable](http://download.oracle.com/javase/6/docs/api/java/lang/Comparable.html) and/or [Comparator](http://download.oracle.com/javase/6/docs/api/java/util/Comparator.html) for further details.

To activate agents in the new ordering, modify the `activateAgents()` in the `StupidModelContextBuilder` class with a simple call of `Collections.sort()` that sorts the specified agent list depending on the natural ordering of the elements we specified above:

```
@ScheduledMethod(start = 1, interval = 1, priority = 0)
public void activateAgents() {
	final ArrayList<Bug> bugList = getBugList();

	// Model 9 requires random agent activation
	// SimUtilities.shuffle(bugList, RandomHelper.getUniform());

	// Model 10 requires sorted agent activation
	Collections.sort(bugList);

	// ...
}
```

That is all!

### Further Fun ###
  * For simplicity, we implemented the `Comparable` interface with the agents. However, it is unadvised in some cases. Specifically, the contract of `compareTo()` declares that if it returns `0` for two items then the `equals()` function should return `true` as well. Clearly, this contract is broken currently (for the sake of simplicity, however, it was a simpler solution). The flawless solution for this case is to create a new implementation of a `Comparator`. The final implementation of the model follows this approach. Create such a new class.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_10_model_10/) of the model is accessible from the proper SVN tag directly.

## Optimal Movement ##
### Purpose ###
Show how agents can identify and rank neighbor cells. Illustrate how to iterate over a list.
### Formulation ###
  * In its move method, a bug identifies a list of all cells that are within a distance of _4_ grids but do not have another bug in them. (The bug's current cell is included on this list.)
  * The bug iterates over the list and identifies the cell with highest food availability. The bug then moves to that cell.

### Implementation Notes ###

In order to sort `HabitatCell` instances on different `GridCell` positions, we create a new `Comparator` instance (as it was suggested in the Further Fun section of the previous model) as follows:

```
public class HabitatCell {
	public final static class HabitatCellFoodAvailabilityComparator implements
			Comparator<GridCell<HabitatCell>> {

		@Override
		public int compare(final GridCell<HabitatCell> gc1,
				final GridCell<HabitatCell> gc2) {
			final HabitatCell cell = gc1.items().iterator().next();
			final HabitatCell bell = gc2.items().iterator().next();
			return -Double.compare(cell.foodAvailability, bell.foodAvailability);
		}
	}

	// ...
}
```

Since all of the grid locations contain exactly one `HabitatCell` element, we can access them easily by callint the first `next()` over its `iterator()` of its `items()`. The comparison is done by the default double comparison in Java based on the `foodAvailability` level.

To utilize our new `Comparator` and make agents do an optimal movement, modify the `step()` function of the `Bug` agents as follows:

```
public void step() {
	final Grid<Object> grid = getGrid();
	final GridPoint location = grid.getLocation(this);

	// ...

	// Before Model 11, random movement was used
	// SimUtilities.shuffle(freeCells, RandomHelper.getUniform());

	// Get a random free location within sight range
	// final GridCell<Bug> chosenFreeCell =
	// 	SMUtils.randomElementOf(freeCells);

	final List<GridCell<HabitatCell>> habitatCells = getHabitatCellsForLocations(freeCells);

	Collections.sort(habitatCells,
			new HabitatCellFoodAvailabilityComparator());

	// The first element has the most available food, it is the optimal
	// target for displacement
	final GridCell<HabitatCell> chosenFreeCell = habitatCells.get(0);

	// We have our new GridPoint to move to, so relocate agent
	final GridPoint newGridPoint = chosenFreeCell.getPoint();
	grid.moveTo(this, newGridPoint.getX(), newGridPoint.getY());
}
```

The difference is that the `chosenFreeCell` is determined by sorting the list of `HabitatCell` instances having no `Bug` agents by their food availability, and choosing the first one of them (they are ordered in descending order).

The `getHabitatCellsForLocations()` method is a new one that creates a list of `HabitatCell` instances whose location is specified in the list of free cells (containing no `Bug` agents on them). To locate the `HabitatCell` instances, we use the `GridCellNgh` class we used [in the beginning of the tutorial](#Specifying_an_Agent_Action.md) to query the surroundings of an agent (we query a neighbourhood of `0` but including the centrail point by specifying `true` for the argument of `getNeighborhood()`):

```
private List<GridCell<HabitatCell>> getHabitatCellsForLocations(
		final List<GridCell<Bug>> freeCells) {
	final ArrayList<GridCell<HabitatCell>> ret = new ArrayList<GridCell<HabitatCell>>();
	final Grid<Object> grid = getGrid();

	// Iterate over the specified location with no associated Bug agents
	for (final GridCell<Bug> gridCell : freeCells) {
		final GridPoint point = gridCell.getPoint();

		// Query the HabitatCell of that location
		final List<GridCell<HabitatCell>> cells = new GridCellNgh<HabitatCell>(
				grid, point, HabitatCell.class, 0, 0).getNeighborhood(true);

		ret.add(cells.get(0));
	}

	return ret;
}
```

That is all!

### Further Fun ###
  * Instead of comparing the free `HabitatCell` instances by their available food level, try modifying the model in a way when `Bug` agents choose a preferable cell based on the maximum food production rate.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_12_model_11/) of the model is accessible from the proper SVN tag directly.

## Bug Mortality and Reproduction ##
### Purpose ###
Show how to "kill" and drop objects from a model, and how to create new objects during a run.
### Formulation ###
  * When a bug's size reaches `10`, it reproduces by splitting into `5` new bugs. Each new bug has an initial size of `0.0`, and the old bug disappears.
  * New bugs are placed at the first empty location randomly selected within `+/- 3` cells of their parent's last location. If no location is identified within `5` random draws, then the new bug dies.
  * A new bug parameter `survivalProbability` is initialized to `0.95`. Each time step, each bug draws a uniform random number, and if it is greater than `survivalProbability`, the bug dies and is dropped.
  * This mortality action is scheduled after the bug moves and grows.
  * The model stopping rule is changed: the model stops after `1000` time steps have been executed or when the number of bugs reaches zero.

### Implementation Notes ###

The first task is to create a new action, `mortality`. Generally its scheme is pretty simple:

```
public class Bug implements Comparable<Bug> {
	// ...
	
	public void mortality() {
		// If size is great enough, reproduce and disappear
		if (size > 10.0) {
			reproduce();
			die();
		}
	}

	private void reproduce() {
		// TODO
	}

	private void die() {
		// TODO
	}
}
```

We check if the size of the agent is great enough, and if it is, we call a reproduce() function to create and place the new `Bug` agents, and call a `die()` function to remove the agent from the simulation.

We also created two placeholder functions to make things simpler. The `die()` function is very straightforward: to remove an agent from the simulation, we simply have to remove it from the context which may be done through the `remove()` function of the current `Context` instance (by specifying any object to it, currently we use the actual agent through the `this` pseudo variable). Removing an object from the context automatically removes it from any projections associated with that context. So, in our case, the current `Bug` is removed from the `space` and `grid` projections as well (if it was assigned to the `space` during initialization).

```
private void die() {
	ContextUtils.getContext(this).remove(this);
}
```


The `reproduce()` function is a bit longer one. First wee need the references of the `context`, `grid` and the current `location` of the agent:

```
private void reproduce() {
	// Get the current context, grid and location
	@SuppressWarnings("unchecked")
	final Context<Object> context = (Context<Object>) ContextUtils
			.getContext(this);
	final Grid<Object> grid = getGrid();
	final GridPoint location = grid.getLocation(this);
	
	// ...
}
```

Next we need to declare two constants, one for the _reproduction rate_ (the number of new `Bug` instances an agent may spawn) and one for the _range of the reproduction_ (the distance how far agents may spawn descendants). Edit the `Constants` class and add the following definition to it:

```
public final class Constants {
        // ...
        
        public static final int BUG_REPRODUCTION_RATE = 5;
	public static final int BUG_REPRODUCTION_RANGE = 3;
}
```

Next, back to the `reproduce()` function, we need a simple iteration to create the specified number of new `Bug` agents.

```
private void reproduce() {
	final Context<Object> context = (Context<Object>) ContextUtils
			.getContext(this);
	final Grid<Object> grid = getGrid();
	final GridPoint location = grid.getLocation(this);

	// Spawn the specified number of descendants
	for (int i = 0; i < Constants.BUG_REPRODUCTION_RATE; ++i) {
		// ...
	}
}
```

In each iteration, we create a new bug, and set its size to `0.0`, as the model formulation specifies.

```
private void reproduce() {
	final Context<Object> context = (Context<Object>) ContextUtils
			.getContext(this);
	final Grid<Object> grid = getGrid();
	final GridPoint location = grid.getLocation(this);

	for (int i = 0; i < Constants.BUG_REPRODUCTION_RATE; ++i) {
		// Create new bug with specified default size
		final Bug child = new Bug();
		child.setSize(0.0);

		// ...
	}
}
```

To query the neighbourhood and find the free cells containing no bugs is done exactly the same way as we did in the implementation of the `step()` function:

```
private void reproduce() {
	final Context<Object> context = (Context<Object>) ContextUtils
			.getContext(this);
	final Grid<Object> grid = getGrid();
	final GridPoint location = grid.getLocation(this);

	for (int i = 0; i < Constants.BUG_REPRODUCTION_RATE; ++i) {
		final Bug child = new Bug();
		child.setSize(0.0);

		// Get the reproduction range of the current bug
		final List<GridCell<Bug>> bugNeighborhood = new GridCellNgh<Bug>(
				grid, location, Bug.class,
				Constants.BUG_REPRODUCTION_RANGE,
				Constants.BUG_REPRODUCTION_RANGE).getNeighborhood(false);

		// We have a utility function that returns the filtered list of
		// empty GridCells objects
		final List<GridCell<Bug>> freeCells = SMUtils
				.getFreeGridCells(bugNeighborhood);
		// ...
	}
}
```

After that we have the available free cells the `Bug` agent has around it. If it is empty, there is no place for any other `Bug` agent, so we interrupt the execution of reproduce() by a simple `return` statement, allowing the agent to continue with the execution of the `mortality` action. Otherwise we choose a random territory from the available free cells:

```
private void reproduce() {
	final Context<Object> context = (Context<Object>) ContextUtils
			.getContext(this);
	final Grid<Object> grid = getGrid();
	final GridPoint location = grid.getLocation(this);

	for (int i = 0; i < Constants.BUG_REPRODUCTION_RATE; ++i) {
		final Bug child = new Bug();
		child.setSize(0.0);

		final List<GridCell<Bug>> bugNeighborhood = new GridCellNgh<Bug>(
				grid, location, Bug.class,
				Constants.BUG_REPRODUCTION_RANGE,
				Constants.BUG_REPRODUCTION_RANGE).getNeighborhood(false);

		final List<GridCell<Bug>> freeCells = SMUtils
				.getFreeGridCells(bugNeighborhood);

		// Model specifies if there is no empty location in vision range,
		// no new child should be spawned
		if (freeCells.isEmpty()) {
			break;
		}

		// Choose one of the possible cells randomly
		final GridCell<Bug> chosenFreeCell = SMUtils
				.randomElementOf(freeCells);

		// ...
	}
}
```

If we found a possible candidate where the new agent may be placed on, we add the new `Bug` agent to the `context` and move it to the proper location on the `grid`. The whole method in one:

```
private void reproduce() {
	@SuppressWarnings("unchecked")
	final Context<Object> context = (Context<Object>) ContextUtils
			.getContext(this);
	final Grid<Object> grid = getGrid();
	final GridPoint location = grid.getLocation(this);

	for (int i = 0; i < Constants.BUG_REPRODUCTION_RATE; ++i) {
		final Bug child = new Bug();
		child.setSize(0.0);

		final List<GridCell<Bug>> bugNeighborhood = new GridCellNgh<Bug>(
				grid, location, Bug.class,
				Constants.BUG_REPRODUCTION_RANGE,
				Constants.BUG_REPRODUCTION_RANGE).getNeighborhood(false);

		final List<GridCell<Bug>> freeCells = SMUtils
				.getFreeGridCells(bugNeighborhood);

		if (freeCells.isEmpty()) {
			break;
		}

		final GridCell<Bug> chosenFreeCell = SMUtils
				.randomElementOf(freeCells);

		// Add the new bug to the context and to the grid
		context.add(child);

		// We have our new GridPoint to move to, so locate agent
		final GridPoint newGridPoint = chosenFreeCell.getPoint();
		grid.moveTo(child, newGridPoint.getX(), newGridPoint.getY());
	}
}
```

#### Adding Survival Probability to Bug Agents ####

First, we need a simple `prob` function in the `SMUtils` class that returns `true` or `false` with the specified probability. It is specified the the actual `threshold` level to use, and returns `true` if a random number chosen from the `[0,1)` interval is smaller than the given number, and `false` otherwise. Extend the utility functions of the `SMUtils` class with the following sipmle one:

```
public final strictfp class SMUtils {
	// ...

	public static boolean prob(final double threshold) {
		return (threshold < RandomHelper.nextDouble());
	}
}
```

Then we have to add the new agent parameter to the `Bug` class as we added agent parameters before in [Model 4](#Cell_and_Bug_Probes.md):

```
public class Bug implements Comparable<Bug> {
	// ...	

	private double survivalProbability = 0.95;
	
	// ...
	
	@Parameter(displayName = "Bug survival probability", usageName = "survivalProbability")
	public double getSurvivalProbability() {
		return survivalProbability;
	}

	public void setSurvivalProbability(final double survivalProbability) {
		this.survivalProbability = survivalProbability;
	}
	
	// ...
}
```

One more thing to do is to add a simple check for the `mortality` action of the `Bug` agents that checks the specified 95% probability for the agents to survive by utilizing the new `prob()` function we created:

```
public void mortality() {
	if (size > 10.0) {
		reproduce();
		die();
		return;
	}

	// Check the uniform random survival probability, and drop agent if necessary
	if (SMUtils.prob(survivalProbability)) {
		die();
	}
}
```

Also note the `return` statement after the code block of checking if the `size` of the `Bug` agent is great enough. It is used because if an agent is reproduced then it is also killed explicitly afterwards, so there is no need to check the survival probability to check if it should die again (it would have no effect).

#### Scheduling the Mortality Action ####
As the formulation of the model specifies, the new `mortality` action is scheduled after the `move` and `grows` actions. Here we may be proud of our work in generalizing the agent actions and collecting them to one place. Bless to our foresight, this way it is very easy to overview, modify or update the agent actions.

Our only task is to extend the list of actions with the new one as the following code listing shows:

```
@ScheduledMethod(start = 1, interval = 1, priority = 0)
public void activateAgents() {
	final ArrayList<Bug> bugList = getBugList();
	Collections.sort(bugList);

	for (final Bug bug : bugList) {
		bug.step();
	}

	for (final Bug bug : bugList) {
		bug.grow();
	}

	// Model 12: Added mortality, scheduled after the bug move and grow
	for (final Bug bug : bugList) {
		bug.mortality();
	}
}
```

#### Changing the Stopping Rule ####
The last thing to implement to finish this version of the model is to change the stopping condition. First, the previous stopping condition should be disabled by commenting out it in the `grow()` function of the `Bug` class:

```
public void grow() {
	size += foodConsumption();

	// The model stopping rule is changed in Model 12
	// if (size > 100.0) {
	// 	System.out.println("Agent reached maximal size: " + this);
	// 	RunEnvironment.getInstance().endRun();
	// }
}
```

The tick counter is a magic number, so it is advised to create a constant for it. Edit `Constants.java` and add the following definition to the class:

```
public final class Constants {
	// ...
	
	public static final double DEFAULT_END_AT = 1000.0;
}
```

To stop the simulation (abruptly or in a specified time step) we can use the utility functions featured by the class `RunEnvironment`, as we used previously in [Model 7](#Stopping_the_Model.md).

The best place to declare when the model should stop is at the initialization, i.e., add the following code segment to the end of the `build()` method in class `StupidModelContextBuilder`:

```
@Override
public Context<Object> build(final Context<Object> context) {
	// ...

	RunEnvironment.getInstance().endAt(Constants.DEFAULT_END_AT);
	return context;
}
```

We also have to stop the model when all of the `Bug` agents die. A practical way to check this is right after the `mortality` action. So, the full `activateAgents()` function should look like the following code listing:

```
@ScheduledMethod(start = 1, interval = 1, priority = 0)
public void activateAgents() {
	final ArrayList<Bug> bugList = getBugList();
	Collections.sort(bugList);

	for (final Bug bug : bugList) {
		bug.step();
	}

	for (final Bug bug : bugList) {
		bug.grow();
	}

	// Model 12: Added mortality, scheduled after the bug move and grow
	for (final Bug bug : bugList) {
		bug.mortality();
	}

	// Model 12: Model should also stop when the number of bugs reaches zero.
	if (0 == getBugList().size()) {
		RunEnvironment.getInstance().endRun();
	}
}
```

Also note that we did not used the already queried list of bugs (`bugList`). We have to re-collect all of the bug agents (i.e., call the `getBugList()` function again), since after executing the `mortality` actions the list of agents may be show major differences between the two calls, and the previously accessed list become obsolete (e.g., several new agents may born or die).

### Further Fun ###
  * If you followed this section of the tutorial carefully, you may have noticed that we used a hard-coded constant for the `Bug` size in the `mortality()` function. Can you make it a model parameter? (_Note that the final model has a proper constant for this value._)
  * Try to make experiments with different default values for the `survivalProbability`.
  * Also, modifying the consntants `BUG_REPRODUCTION_RATE` and `BUG_REPRODUCTION_RANGE` may also result in interesting results. Try a few different combinations.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_13_model_12/) of the model is accessible from the proper SVN tag directly.

## Population Abundance Graph ##
### Purpose ###
Show how to add a simple time series graph to a model. This graph is important for understanding results now that reproduction and mortality change the abundance of bugs.
### Formulation ###
No change is made to the model formulation. A graph is added to display the number of bugs alive at each time step.

### Implementation Notes ###

This model requires only a minimal coding. A simple method is needed to get the total number of current `Bug` population which is used to create the _Population Abundance Graph_. Add the following new function to the class `StupidModelContextBuilder`:

```
public int bugCount() {
	final RunState runState = RunState.getInstance();

	// If simulation is not yet started or initialized correctly
	if (null == runState) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	final Context<Object> masterContext = runState.getMasterContext();

	// If simulation is not initialized correctly and there is no root
	// context
	if (null == masterContext) {
		return 0;
	}

	return masterContext.getObjects(Bug.class).size();
}
```

We check that the simulation is started, or it is assigned a root context properly, and return `0` if not. Otherwise, we simply return the number of `Bug` agents in the context. This is required because the function may be called during the initialization, where either of the required references may not be available.

That was all the implementation part. Next, we need to define a new _Data Set_ and of course, a new the _Population Abundance Graph_.

#### Data Set of Population Abundance ####
Start the simulation. We add the new graph as we did it previously through the point & click interface of Repast Simphony.

  1. Right-click on _Data Sets_ in the _Scenario Tree_, and add a new data set named _Data Set of Population Abundance_.
  1. Choose `StupidModelContextBuilder` as _Agent Class_. This may sound strangely first, but think of it we have to specify on which object should Repast Simphony call a function used to create the required data set. Since we declared the `bugCount()` function in that class, we have to specify it.
  1. Click _Next_.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/data_set_of_population_abundance.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/data_set_of_population_abundance.png)
  1. On the next wizard screen, add the `Tick` and `bugCount()`. We need a nice title for the chart, so we are going to use the _scripting feature_ of the dataset creator wizard. Click on _Add_ --> _Add Using Wizard_.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_using_wizard.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/add_using_wizard.png)
    1. A new dialog opens where we can select a proper mapping type. We create a simple constant string title, so select _Bean Shell Scripted Mapping (Non-Aggregate)_ from the list.
> > > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/bean_shell_scripted_mapping.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/bean_shell_scripted_mapping.png)
    1. Click _Next_.
    1. On the next wizard screen, we can type in any valid _BeanShell_ expression. Type in the following expression which returns a simple string that we can use as a title for our chart:
```
beanshell: value = "Number of Bug agents";
```
> > > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/chart_title_bean_shell_script.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/chart_title_bean_shell_script.png)
    1. Click _Next_, and then _Finish_.
  1. We get back to the _Data Set Editor_ wizard. Rename the new scripted mapping from _Mapping0_ to _Title_ by double-clicking on its name. After adding all of the necessary data to the data set, it should look like as the following screenshot shows:

> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/title_mapping_created.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/title_mapping_created.png)
  1. Click _Next_.
  1. Since we do not want to change the update frequency of the data set, press _Finish_ on the last wizard page.

The new data set should be displayed under the available _Data Sets_ in the _Scenario Tree_:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/data_set_of_population_abundance_created.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/data_set_of_population_abundance_created.png)


> <b>Remark</b> If you are interested in the details of scripting, please refer to the Repast Simphony Documentation (where it is covered in depth) for further information about the topic.

#### Population Abundance Graph ####
At last, we are ready to create the _Population Abundance Graph_. Create a new chart by right-clicking on _Charts_ in the _Scenario Tree_, and select _Add Chart_.

  1. Select _XY Line or Bar Chart, or Scatterplot_ from the list of available charts.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/select_xyline.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/select_xyline.png)
  1. Click _Next_.
  1. Type _Population Abundance_ as the _Name_ of the chart.
  1. Select the freshly created _Data Set of Population Abundance_ as _Data Set_.
  1. Leave _Tick_ as the _X-Axis Column_.
  1. Click on _Add_, and select _Title_ as _Series Name_, _bugCount_ as _Y-Axis Value_. The wizard page should look like this:
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/creation_of_population_abundance_chart.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/creation_of_population_abundance_chart.png)
  1. Click _Next_.
  1. On the next screen, the visual properties of the chart may be set (e.g., ranges of the x and y-axes, auto-fitting or the history length). The default values are perfect for the current tutorial, click _Next_.
> > ![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/default_line_chart_settings.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/default_line_chart_settings.png)
  1. The data mapping for the new chart is complete, click _Finish_ to add the it to the simulation.

The _Population Abundance_ chart should be displayed under the available _Charts_ in the _Scenario Tree_:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_population_abundance_chart.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/new_population_abundance_chart.png)

Save the settings by pressing the _Save the Model_ button at the toolbar. Initialize the simulation, and the new chart should be displayed next to the previously created histogram. Start the simulation, and watch the trend how the number of `Bug` agent changes over time!

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_12.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_12.png)

That is all!

### Further Fun ###
  * Can you add a second chart, a simple XY Line or Scatterplot chart that shows the _average_ agent size in each simulation step?
  * Can you extend this chart with the addition of the _minimum_ and _maximum_ `size` values as well?

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_13_model_13/) of the model is accessible from the proper SVN tag directly.

## Random Normal Initial Size ##
### Purpose ###
Illustrate use of random number distributions. A common use of them is to induce variability among initial individuals.
### Formulation ###
  * Two new model parameters are added, and put on the parameter settings window: `initialBugSizeMean` and `initialBugSizeSD`. Values of these parameters are `0.1` and `0.03`.
  * Instead of initializing bug sizes to `1.0` (See [here](#Bug_Growth.md)), sizes are drawn from a normal distribution defined by `initialBugSizeMean` and `initialBugSizeSD`. The initial size of bugs produced via reproduction is `0.0`.
  * Negative values are very likely to be drawn from normal distributions such as the one used here. To avoid them, a check is introduced to limit initial bug size to a minimum of zero.

### Implementation Notes ###

As we did in [Model 5: Parameters and Parameter Displays](#Parameters_and_Parameter_Displays.md), we have to assign specific IDs to the new parameters. Declare the following two new constant values in class `Constants`:

```
public final class Constants {
	// ...
	
	public static final String PARAMETER_INITIAL_BUG_SIZE_MEAN = "initialBugSizeMean";
	public static final String PARAMETER_INITIAL_BUG_SIZE_SD = "initialBugSizeSD";
```

Next, start up the simulation, and declare the new parameters. Select _Parameters_ panel, and click on the _Add Parameter_ button at the bottom of it.

To create the parameter for the initial bug size mean:

  1. Type _initialBugSizeMean_ for the _Name_.
  1. Type _Initial Bug Size Mean_ for _Display Name_.
  1. Type _double_ for the _Type_.
  1. Type _0.1_ for the _Default Value_.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_parameter_initialBugSizeMean.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_parameter_initialBugSizeMean.png)

To create the parameter for the initial bug size standard deviation:

  1. Type _initialBugSizeSD_ for the _Name_.
  1. Type _Initial Bug Size SD_ for _Display Name_.
  1. Type _double_ for the _Type_.
  1. Type _0.03_ for the _Default Value_.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_parameter_initialBugSizeSD.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_parameter_initialBugSizeSD.png)

The _Parameters_ panel should look like this after adding the new parameters:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/bug_size_distribution_parameters_added.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/bug_size_distribution_parameters_added.png)

#### Setting Bug Initial Sizes ####

We need only a simple utility function. As its parameter, it gets a properly initialized `cern.jet.random.Normal` distribution (based on the values of the previously defined model parameters during the initialization phase). We simply generate a new double from the distribution, and set the size of the bug to this value. We also have to check if it is negative as the model formulation specifies, and use zero instead in such cases (this is done by the standard Java way by using the `Math.max()` method from the API).

Also, to make sure the size of all other agents is zero, we modify the value of the `size` member to `0.0`;

```
public class Bug {
	private double size = 0.0; // Changed to zero 

	// ...

	public void setInitialSize(final Normal normal) {
		// Generate a new random initial size value, and if it is negative, use
		// 0.0 instead
		final double initialSize = Math.max(normal.nextDouble(), 0.0);
		setSize(initialSize);
	}
}
```

#### Initializing Bug Agent Size from Normal Distribution ####

Since the model initialization has to be modified, open up class `StupidModelContextBuilder` and add the following code to the `build()` function, somewhere after the `Parameters` instance is ready to use. We placed it after the query of the `bugCount` parameter to group the parameter queries into one place:

```
@Override
public Context<Object> build(final Context<Object> context) {
	context.setId(Constants.CONTEXT_ID);
	final Parameters parameters = RunEnvironment.getInstance()
			.getParameters();

	// ...

	final int bugCount = ...;

	// Model 14 defines a new random normal distribution to use for the
	// initially created agent sizes
	final double initialBugSizeMean = ((Double) parameters
			.getValue(Constants.PARAMETER_INITIAL_BUG_SIZE_MEAN))
			.doubleValue();

	final double initialBugSizeSD = ((Double) parameters
			.getValue(Constants.PARAMETER_INITIAL_BUG_SIZE_SD))
			.doubleValue();

	final Normal normal = RandomHelper.createNormal(initialBugSizeMean,
			initialBugSizeSD);
	
	// ...
}
```

What we do here is that we query the currently used parameter values for both `initialBugSizeMean` and `initialBugSizeSD` in [the way we used before](#Creating_Model_Parameters.md), and create a new `Normal` distribution through the utility method `createNormal()` of class `RandomHelper` which requires nothing but the used mean and standard deviation.

Now we have the `Normal` distribution required to initialize a `Bug` with its utility method `setInitialSize()`. A few rows later when creating the agents, add the initialization method call:

```
for (int i = 0; i < bugCount; ++i) {
	final Bug bug = new Bug();
	bug.setInitialSize(normal); // Set the initial size of the Bug

	context.add(bug);
	final NdPoint pt = space.getLocation(bug);
	grid.moveTo(bug, (int) pt.getX(), (int) pt.getY());
}
```

That is all! We have induced variability among the initial `Bug` population that may be manipulated through model parameters.

### Further Fun ###
  * The basic parameter settings of the _mean_ and _standard deviation_ do not really have a visible influence on the histogram of size distribution. Modify the parameters and check the histogram if you see what you expect from the modified parameter values (e.g., set `initialBugSizeMean` to `5` and `initialBugSizeSD` to `2`)!
  * Size of the reproduced bug agents after initialization is still homogeneous. Can you include new parameters for the spawned bug agent size to be drawn from a different normal distribution (e.g., `reproducedBugSizeMean` and `reproducedBugSizeSD` with values `1.5` and `0.7`, respectively)?

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_15_model_14/) of the model is accessible from the proper SVN tag directly.

## Habitat Data from File Input ##
### Purpose ###
Show how to read spatial data in from a file.
### Formulation ###
  * Instead of assuming the space size and assuming cell food production is random (See [here](#Habitat_Cells_and_Resource.md)), food production rates are read in from a file. The file also determines the space size.
  * The file contains one line per cell, with (a) X coordinate, (b) Y coordinate, and (c) food production rate.
  * Food production in a cell is now equal to the production rate read in from the file, and is no longer random.
  * Now, because we are representing real habitat with real data, it no longer makes sense for the space to be toroidal. So the space objects and movement-related methods must be modified so bugs cannot move off the edge of their space.
  * The input file is `Stupid_Cell.Data`. It has X, Y, and food production data for a grid space. X ranges from 0 to 250; Y ranges from 0 to 112. The file starts with three lines of header information that is ignored by the model.
  * The cells are now displayed and colored to indicate their current food availability. Cell colors scale from black when cell food availability is zero to green when food availability is 0.5 or higher.
  * A change to the bug move method is required to avoid a very strong artifact now that cell food production is no longer random. Near the start of a simulation, many cells will have exactly the same food availability, so a bug simply would move to the first cell on its list of neighbor cells. This is always the top-left cell among the neighbors, so bugs move constantly up and left if all the cells available to them have the same food availability. This artifact is removed by randomly shuffling the list of available cells before the bug loops through it to identify the best.

### Implementation Notes ###
First, download the required [Stupid\_Cell.Data](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/data/Stupid_Cell.Data) file and copy it into the `StupidModel` project directory (it is the same file you can find in the original model specification).

This section describes a rather Java-based method for file reading, since Java has a roboust and general API that may be used from Repast Simphony as well.

#### Creating a Representation for the Parsed Cell Data ####
It is always a good idea to create a wrapper class when reading data from file instead of handling the raw data directly. Hence we create a new class `CellData` and put it into the `stupidmodel.common` package with the following content:

```
public class CellData {
	private final int x, y;
	private final double foodProductionRate;

	public static final CellDataXComparator CELL_DATA_X_COMPARATOR = new CellDataXComparator();
	public static final CellDataYComparator CELL_DATA_Y_COMPARATOR = new CellDataYComparator();

	private static final class CellDataXComparator implements
			Comparator<CellData>, Serializable {
		private static final long serialVersionUID = 4149351630089726905L;

		@Override
		public int compare(final CellData o1, final CellData o2) {
			return o1.getX() - o2.getX();
		}
	}

	private static final class CellDataYComparator implements
			Comparator<CellData>, Serializable {
		private static final long serialVersionUID = -5955739679291874417L;

		@Override
		public int compare(final CellData o1, final CellData o2) {
			return o1.getY() - o2.getY();
		}
	}

	public CellData(final int x, final int y, final double foodProductionRate) {
		this.x = x;
		this.y = y;
		this.foodProductionRate = foodProductionRate;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getFoodProductionRate() {
		return foodProductionRate;
	}

	@Override
	public String toString() {
		return String.format("CellData [x=%d, y=%d, foodProductionRate=%f",
				x, y, foodProductionRate);
	}

}
```

The class definition is a simple wrapper class, containing members for both the `x`, `y` coordinates and for the `foodProductionRate`. Conventional getter methods and a simple `toString()` is implemented just for debugging reasons.

The only tricky part here is two simple `Comparator<CellData>` definitions that can compare `CellData` instances based on their `x` and `y` coordinates. They are not necessary but they help easily identify the bounds of the world that has to be created.

#### Updating Constants ####

As usual, we have a few constant values again. Open up class `Constants` and perform the following modifications:

```
public final class Constants {
	// public static final int GRID_SIZE = 100;
	
	public static final String STUPID_CELL_DATA_FILE = "Stupid_Cell.data";
	public static final int CELL_DATA_FILE_HEADER_LINES = 3;
}
```

Since we have to determine the size of the grid based on the maximum coordinate values in the specified file the `GRID_SIZE` constant become unnecessasry, hence it can be commented out.

The two new constants we declare is for the data file: `STUPID_CELL_DATA_FILE` defines the name of the file, and `CELL_DATA_FILE_HEADER_LINES` contains the number of header lines in the file as described in the model formulation.

#### Reading the File Data ####

To parse the data from the file we create a new utility function in the `SMUtils` class file. It gets the name of the data file as a parameter, reads the contents of the file, and populates and returns a list of properly initialized `CellData` objects. The method uses the standard Java API by creating a BufferedReader object to read lines from the file, and the split() function to split data between whitespace characters.

```
public static List<CellData> readDataFile(final String cellDataFileName) {
	final ArrayList<CellData> ret = new ArrayList<CellData>();
	BufferedReader br = null;

	try {
		br = new BufferedReader(new FileReader(cellDataFileName));

		// Skip header
		for (int i = 0; i < Constants.CELL_DATA_FILE_HEADER_LINES; ++i) {
			br.readLine();
		}

		// Read lines, parse data and add a new CellData for each one
		String line = null;
		while ((line = br.readLine()) != null) {
			// Split the line around whitespaces
			final String[] data = line.split("\\s+");

			int idx = 0;
			final int x = Integer.parseInt(data[idx++]);
			final int y = Integer.parseInt(data[idx++]);
			final double foodProductionRate = Double
					.parseDouble(data[idx++]);

			ret.add(new CellData(x, y, foodProductionRate));
		}

	} catch (final FileNotFoundException e) {
		e.printStackTrace();
	} catch (final IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	return Collections.unmodifiableList(ret);
}
```


> <b>Remark</b> The above described function is a bit simplified version of the where a few error checking is omitted (when reading data from a file a lot of things may broke, e.g., wrong number of data in a line, missing data in a file, malformed data, etc.). The above described function should work fine in most of the situations, however, we supplied a bit sophisticated version in the implementation to help identify what went wrong during the parsing of the data file.

#### Updating HabitatCell Implementation ####
There is a few update that has to be done for the current `HabitatCell` implementation. First, you can remove `maximumFoodProductionRate` member variable, since the production rate is now read from the specified file, and not random any more.

However, we need to store specified `foodProductionRate`.

```
protected double foodProductionRate = 0.01;
```

It is set by the constructor, but not declared as `final`, since we may want to manipulate it at the graphical interface. For this reason, add both a new getter and setter function to the class, and add the `@Parameter` annotation as shown below:

```
@Parameter(displayName = "Cell maximum food production rate", usageName = "foodProductionRate")
public double getFoodProductionRate() {
	return foodProductionRate;
}

public void setFoodProductionRate(final double foodProductionRate) {
	this.foodProductionRate = foodProductionRate;
}
```

Then, the constructor is updated from requiring an `x` and `y` coordinate to accept a properly initialized `CellData` instance:

```
public HabitatCell(final CellData cellData) {
	this.x = cellData.getX();
	this.y = cellData.getY();
	this.foodProductionRate = cellData.getFoodProductionRate();
}
```


Also, the `grow` action should be updated properly to use `foodProductionRate` instead of the random value created by `RandomHelper.nextDoubleFromTo(0.0, maximumFoodProductionRate)`:

```
public void growFood() {
	foodAvailability += foodProductionRate;
	
	// ...
}
```


#### Updating Bug Actions ####

```
public void step() {
	if (freeCells.isEmpty()) {
		return;
	}

	// Model 15: A change to the bug move method is required to avoid a very
	// strong artifact now that cell food production is no longer random.
	// Near the start of a simulation, many cells will have exactly the same
	// food availability, so a bug simply would move to the first cell on
	// its list of neighbor cells. This is always the top-left cell among
	// the neighbors, so bugs move constantly up and left if all the cells
	// available to them have the same food availability. This artifact is
	// removed by randomly shuffling the list of available cells before the
	// bug loops through it to identify the best.
	SimUtilities.shuffle(freeCells, RandomHelper.getUniform());

	final List<GridCell<HabitatCell>> habitatCells = getHabitatCellsForLocations(freeCells);

	Collections.sort(habitatCells,
			new HabitatCellFoodAvailabilityComparator());

	// ...
}
```

#### Updating the Initialization ####
The last thing to add is to update the initialization of `HabitatCell` instances. Most importantly, we have to parse the `CellData` and get the properly initialized list of cell information. Next, we create a `gridSizeX` and `gridSizeY` variables (since we removed the `GRID_SIZE` constant previously). Here we use the previously defined comparators to find the maximum coordinates in one line.

```
@Override
public Context<Object> build(final Context<Object> context) {
	context.setId(Constants.CONTEXT_ID);

	// Model 15 extension: read the cell data from a specified file
	final List<CellData> cellData = SMUtils
			.readDataFile(Constants.STUPID_CELL_DATA_FILE);

	// The first model specified a two-dimensional grid 100 * 100, but from
	// Model 15 it has to be evaluated from the file input

	// The +1s are added because the grid is indexed from 0, so we have to
	// increase the actual size by 1

	final int gridSizeX = Collections.max(cellData,
			CellData.CELL_DATA_X_COMPARATOR).getX() + 1;

	final int gridSizeY = Collections.max(cellData,
			CellData.CELL_DATA_Y_COMPARATOR).getY() + 1;

	// ...
}
```

Of course, we have to replace all references to `GRID_SIZE` with either `gridSizeX` or `gridSizeX`.

Creating the `HabitatCell` instances should also be modified to use the created `cellData` instances. We iterate throgh the cellData list instead of a double `GRID_SIZE*GRID_SIZE` iteration.

```
for (final CellData act : cellData) {
	final HabitatCell cell = new HabitatCell(act);
	context.add(cell); 
	grid.moveTo(cell, act.getX(), act.getY());
	foodValueLayer.set(cell.getFoodAvailability(), act.getX(),
			act.getY());
}
```

That is all! The cells of the simulation now works based on real data. Start up the simulation, and see how the cells grow different rate of food!

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_15.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation_model_15.png)

### Further Fun ###
  * Most of the code created in this section is based on standard Java API. The section that may be improved is the `readDataFile()` function, try to make it a bit roboust by adding custom error handling (e.g., check if every line in the specified file is well formed, contains only the expected numerical data, etc.).

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_16_model_15/) of the model is accessible from the proper SVN tag directly.

## Predators ##
### Purpose ###
Show how to create multiple classes of agents that interact.
### Formulation ###
  * 200 predator objects are intialized and randomly distributed as the bugs are. A cell can contain a predator as well as a bug. Predators are created after bugs are.
  * Predators have one method: hunt. First, a predator looks through a shuffled list of its immediately neighboring cells (including its own cell). As soon as the predator finds a bug in one of these cells it "kills" the bug and moves into the cell. (However, if the cell already contains a predator, the hunting predator simply quits and remains at its current location.) If these cells contain no bugs, the predator moves randomly to one of them.
  * Predator hunting is scheduled after all the bug actions.

### Implementation Notes ###

We approached the final version of the _StupidModel_series. Well done! Keep up and the model is finished at the end of this section!

First, we need a few constant definitions again. Open up class `Constants` and add this two new definitions to the end of the class:

```
public final class Constants {
	// ...
	
	public static final int PREDATOR_COUNT = 200;
	public static final int PREDATOR_SIGHT_RANGE = 1;
}
```

#### Updating the Utility Functions ####

Note that we have a `getGrid()` member method in class `Bug` created previously. Now, since we have more than one agent type moving on the grid (i.e., the `Predator` class), it seems a good idea to generalize that method and move it to the `SMUtils` class as a utility function (since this function comes handy for both classes). Open up `SMUtils` and add the following definition to the end of the class:

```
public final strictfp class SMUtils {
	// ...
	
	public static Grid<Object> getGrid(final Object o) {
		@SuppressWarnings("unchecked")
		final Grid<Object> grid = (Grid<Object>) ContextUtils.getContext(o)
				.getProjection(Constants.GRID_ID);
		return grid;
	}
}
```

This way if we extend our model with additional agents moving on the same grid, we can utilize this function there too. It accepts any objects (since our grid is defined to contain `Object` types), queries the context that object is assigned to, and returns the according grid projection.

Now, the `getGrid()` function in the `Bug` class may be removed, and replaced by a simple `getGrid(this)` to perform the refactoring and update the code to use the new function.

#### Creating the Predator Class ####
We need a representation in our program for the `Predator` agents as well, just like `Bug` and `HabitatCell`. Create the new `Predator` class in the `agents` package, and copy the following content:

```
public class Predator {

	public Predator() {
		super();
	}

	@ScheduledMethod(start = 1, interval = 1, priority = -1)
	public void hunt() {
		// Get the reference for the used grid
		final Grid<Object> grid = SMUtils.getGrid(this);

		// Get the grid location of this agent
		final GridPoint location = grid.getLocation(this);

		// First, a predator looks through a shuffled list of its immediately
		// neighboring cells (including its own cell)

		final List<GridCell<HabitatCell>> cellNeighborhood = new GridCellNgh<HabitatCell>(
				grid, location, HabitatCell.class,
				Constants.PREDATOR_SIGHT_RANGE, Constants.PREDATOR_SIGHT_RANGE)
				.getNeighborhood(true);

		SimUtilities.shuffle(cellNeighborhood, RandomHelper.getUniform());

		// As soon as the predator finds a bug in one of these cells it "kills"
		// the bug and moves into the cell

		for (final GridCell<HabitatCell> cell : cellNeighborhood) {
			if (hasAgent(grid, cell, Bug.class)) {
				// (However, if the cell already contains a predator, the
				// hunting predator simply quits and remains at its current
				// location)
				if (hasAgent(grid, cell, Predator.class)) {
					return;
				}

				killBugAt(grid, cell);
				moveTo(grid, cell);
				return;
			}
		}

		// If these cells contain no bugs, the predator moves randomly to one of
		// them
		final GridCell<HabitatCell> randomCell = SMUtils
				.randomElementOf(cellNeighborhood);
		moveTo(grid, randomCell);
	}

	private <T> boolean hasAgent(final Grid<Object> grid,
			final GridCell<HabitatCell> cell, final Class<T> clazz) {
		final List<GridCell<T>> neighborhood = new GridCellNgh<T>(grid,
				cell.getPoint(), clazz, 0, 0).getNeighborhood(true);

		final int ctr = neighborhood.get(0).size();
		return (ctr > 0);
	}

	private void killBugAt(final Grid<Object> grid,
			final GridCell<HabitatCell> cell) {
		final List<GridCell<Bug>> bugsAt = new GridCellNgh<Bug>(grid,
				cell.getPoint(), Bug.class, 0, 0).getNeighborhood(true);
		bugsAt.get(0).items().iterator().next().die();
	}

	private void moveTo(final Grid<Object> grid,
			final GridCell<HabitatCell> cell) {
		// We have our new GridPoint to move to, so relocate agent
		final GridPoint newGridPoint = cell.getPoint();
		grid.moveTo(this, newGridPoint.getX(), newGridPoint.getY());
	}

	@Override
	public String toString() {
		final String location = (ContextUtils.getContext(this) != null) ? SMUtils
				.getGrid(this).getLocation(this).toString()
				: "[?, ?]";

		// Override default Java implementation just to have a nicer
		// representation
		return String.format("Predator @ location %s", location);
	}
}
```

The class has a nullary constructor, because it does not need any information for proper initialization. Predators have a `move` action to move randomly and search for bugs in their surroundings. If they find the first, the found `Bug` agent is killed and they move to the location of the killed `Bug`. Also note that it is declared with the standard `@ScheduledMethod` annotation, with the help of the `priority` member: the action is scheduled after all the `Bug` actions.

The `hasAgent()` function is a simple utility function for `Predator` agents to determine if a given cell has an agent from a specified type located at or not. It is used to check if a cell contains a `Bug` agent to hunt down or contains a `Predator` and cannot move to that position (in the method `hunt()`).

There is also a small utility function that performs the query of a `Bug` agent on a specified `GridCell` object, and calls its `die()` method.

The `moveTo()` function does nothing more just moves the `Predator` to the location of the specified `GridCell` object.

The new agent class also has a simple `toString()` method just for debugging.

#### Updating Model Initialization ####

In order to instantiate the `Predator` agents, we also have to update the model initialization, i.e., the `build()` function of class `StupidModelContextBuilder`. Right after creating the `Bug` agents, add the following code segment to create the new agents:

```
@Override
public Context<Object> build(final Context<Object> context) {
	// ...
	
	// Creating Bug agents here
	
	for (int i = 0; i < Constants.PREDATOR_COUNT; ++i) {
		final Predator predator = new Predator();
		
		context.add(predator);
		final NdPoint pt = space.getLocation(predator);
		grid.moveTo(predator, (int) pt.getX(), (int) pt.getY());
	}
	
	// ...
}
```

#### Updating the Simulation GUI ####
We need to edit the current visualization settings of the model. Start up the simulation, and double-click on _Grid with Food Display_ in the _Scenario Tree_. The _Options Editor_ should show up, which contains the settings of the current element you chose (practically the options you set during its creation).

Click on the _Agent Selection_ tab on the left, and use the arrows to add the `Predator` agents to the grid display too.

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_predators_to_grid_display.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/adding_predators_to_grid_display.png)

Also, on the _Agent Style_ tab on the left, select _Predator_ from the _Agents_ list and click on the _Edit style_ button on the right. On the appearing wizard, use the following settings:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/predators_agent_style.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/predators_agent_style.png)

Click on _OK_, and the model is finished!

That is all! We have created multiple classes of agents that interact in the same environment. Good job!

Start the simulation run, and if everything goes well, the simulation should look like something like this:

![https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation.png](https://cscs-repast-demos.googlecode.com/svn/wiki/StupidModel/simulation.png)

### Further Fun ###

  * The `hunt` action is declared with the standard `@ScheduledMethod` annotation. To be consistent, and make the dynamics of the model clearer, put the action definition into the `activateAgents()` function of the class `StupidModelContextBuilder`. This way all of the actions may be found in one place, and the model is easier to understand, update and maintain.
  * The number of `Predator` agents in the simulation is defined by a simple constant value. Make it a model parameter similiarly to the `bugCount` parameter:
    1. Use _predatorCount_ for the _Name_.
    1. Use _Initial Number of Predators_ for _Display Name_.
    1. Use _int_ for the _Type_.
    1. Use _200_ for the _Default Value_.

### Complete Model ###

  * The [complete source code](https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_18_model_16/) of the model is accessible from the proper SVN tag directly.

# Conclusion and Final Words #

Congratulations, you have created a complex model from basic building blocks offered by the standard Repast Simphony Java API. You should have no problems from now on to start an own project and use the features of Simphony you become familiar with.

It was a pleasure to create the demo models and write this tutorial, I hope You enjoyed it while completing it. Anyway, the tutorial is not perfect and always can be improved. I would be more than happy to hear your feedback about the tutorial, so I may be able to improve sections that were not so clear as I thought to be. You can contact me either [personally](Richard.md) or through the [repast-interest mailing list](https://lists.sourceforge.net/lists/listinfo/repast-interest), where others may help you with common issues.

All the best, and thank you for going through this tutorial!

--- Richard O. Legendi

# References #
  1. Steven F. Railsback, Steven L. Lytinen and Stephen K. Jackson: _"StupidModel and Extensions: A template and teaching tool for agent-based modeling platforms"_, (December 20, 2005) http://condor.depaul.edu/~slytinen/abm/StupidModelFormulation.pdf
  1. Isaac, Alan G.: _"The ABM Template Models: A Reformulation with Reference Implementations"_, Journal of Artificial Societies and Social Simulation 14 (2) 5, (2011) http://jasss.soc.surrey.ac.uk/14/2/5.html
  1. Richard O. Legendi, Attila Szabo: _["Advantages of a Multi-Paradigm Programming Language in Agent-Based Model Definition"](http://distributedabm.elte.hu/letoltesek/publikaciok/2010_01_ICAI_Advantages_of_Fables_paper.pdf)_, ICAI 2010 - 8th International Conference on Applied Informatics (Eger, Hungary, January 27 - 30, 2010)