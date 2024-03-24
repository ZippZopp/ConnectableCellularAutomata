# Connectable Cellular Automata

## Overview

This Cellular Automata Application offers a dynamic platform for simulating and interacting with a variety of cellular automata rules on a grid. Users can dynamically apply rules to specific areas of the grid, activate individual cells, switch between different rule sets, adjust the influence radius, and control the simulation with pause and resume functionality.

## Features

- **Dynamic Rule Application**: Dynamically apply cellular automata rules to specific areas of the grid by drawing.
- **Interactive Cell Activation**: Activate individual cells by clicking or dragging the mouse, allowing for interactive and real-time modifications to the simulation.
- **Customizable Rule Sets and Radius**: Easily switch between different rule sets and adjust the radius of influence for complex and varied simulations.
- **Real-Time Simulation Control**: Seamlessly pause and resume the simulation to observe, modify, or reset the current state of the automata.
- **Visual Grid Display**: A graphical interface represents the cellular automata grid, highlighting the distinction between active and inactive cells, as well as the areas affected by different rules.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or newer is required.
- Maven is used to manage dependencies and build the project.
- JavaFX SDK compatible with JDK 17, included as a dependency in `pom.xml`.

### Installation

1. Clone the repository to your local machine using:
   ```sh
   git clone https://github.com/ZippZopp/ConnectableCellularAutomata.git
   ```

2. Navigate to the project directory and compile and run the application using Maven:
   ```sh
   mvn clean javafx:run
   ```

### Usage

- **Selecting Rule Sets**: Use F1 for Game of Life, F2 for High Life, and F3 for Seeds to switch between different rule sets.
- **Adjusting Radius**: The radius of influence can be adjusted using number keys 0 to 9.
- **Activating Cells**: Left-click or drag with the left mouse button over cells to activate or deactivate them, allowing for interactive modifications even during simulation.
- **Applying Rule Sets**: Hold and drag the right mouse button over cells to apply the currently selected rule set, enabling dynamic and flexible customization of the simulation grid.
- **Pausing/Resuming Simulation**: The SPACE bar toggles the simulation's pause and resume state, offering control over the simulation's progress.

## License

This project is distributed under the MIT License. See the `LICENSE` file for more information.

