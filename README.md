# Connectable Cellular Automata 

## Overview

This Cellular Automata Application provides a dynamic environment for simulating and interacting with various cellular automata rules within defined fields on a grid. Users can create fields, set individual cells, apply different rule sets to fields, and control the simulation with pause and resume functionality.

## Features

- **Dynamic Cellular Automata Fields**: Create fields within the grid where specific cellular automata rules are applied.
- **Interactive Cell States**: Set individual cells to an active state with a simple mouse click.
- **Customizable Rule Sets**: Each field can operate under its own set of rules, allowing for diverse and complex simulations.
- **Pause & Resume**: Control the simulation in real-time to pause, make changes, and resume the automata evolution.
- **Visual Grid Display**: A graphical representation of the cellular automata grid with clear distinctions between fields and cell states.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or newer
- Maven (to manage dependencies and build the project)
- JavaFX SDK compatible with JDK 17 (already included as a dependency in `pom.xml`)

### Installation

1. Clone the repository to your local machine:

   ```sh
   git clone https://github.com/ZippZopp/ConnectableCellularAutomata.git
   ```

2. Navigate to the project directory and use Maven to compile and run the application:

   ```sh
   mvn clean javafx:run
   ```

### Usage

- **select RuleSet**: press 1 for Game of Live, press 2 for High Live and press 3 for Seeds.
- **Creating a Field**: Right-click on the grid to define the top-left corner of a new field. Fields are fixed in size and will apply a specific rule set.
- **Activating Cells**: Left-click on any cell within the grid to set its state to active. This is only allowed when the simulation is paused.
- **Pausing/Resuming the Simulation**: Press the SPACE bar to toggle the simulation's paused state.

## License

Distributed under the MIT License. See `LICENSE` for more information.
