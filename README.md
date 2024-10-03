# ![about](https://github.com/joseserize0222/Cognifire-Plugin-Application/blob/main/src/main/resources/META-INF/pluginIcon.svg) IntelliJ IDEA Kotlin Stats Plugin

This plugin offers a comprehensive analysis tool for Kotlin files within your IntelliJ IDEA environment. Built on the [intellij-platform-plugin-template](https://github.com/JetBrains/intellij-platform-plugin-template), this plugin provides real-time insights into your Kotlin source code, including line counts, TODO comment tracking, and detailed function analysis.

## Features

- **Kotlin File Statistics**: Displays the total number of lines and the number of lines containing TODO comments.
- **Function Analysis**: Identifies and lists the longest function in terms of lines, along with its name and body content.
- **Real-time Updates**: Automatically updates the displayed statistics as you make changes to the file or switch between files in the editor.
<img src="./src/data/Editing%20the%20file%20audio%20removed.gif" alt="Modifying the current file">



<img src="./src/data/Switching%20files%20and%20resizing%20the%20windows.gif" alt="Switching between files and window resizing">


- **Theme friendly**: Smartly adapts the UI to be compatible with your theme.


<img src="./src/data/Changing%20the%20theme.gif" alt="Changing the theme">


## Main Components

### `FileAnalyzerService.kt`

- **Purpose**: The core of the plugin, responsible for analyzing Kotlin files.
- **Implementation Details**:
  - Utilizes IntelliJ's **PSI (Program Structure Interface)** to analyze and understand Kotlin files.
  - **Listeners**: Listens for document changes and file selection events. When a change occurs, the statistics are automatically recalculated and updated via a `FileStatsListener`.
  - **Key Functions**:
    - `calculateStats(file: VirtualFile)`: Performs the analysis of a Kotlin file and returns a `KotlinFileStats` object, containing the total number of lines, TODO comment count, the longest function, and its content.

`FileAnalyzerService` is the central component that manages file analysis and real-time statistics updates. By leveraging PSI, it allows for deep analysis of Kotlin source code.

### `ProjectStatsPanel.kt`

- **Purpose**: The user interface component that displays statistics within the IntelliJ IDEA panel.
- **Implementation Details**:
  - **Key Attributes**:
    - `content`: The main panel content that displays the statistics.
  - **Key Methods**:
    - `callback(allStats: KotlinFileStats)`: Called when statistics are updated. It receives a `KotlinFileStats` object and refreshes the panel content with the latest data.

`ProjectStatsPanel` bridges the backend analysis with the graphical user interface, displaying real-time statistics for the currently selected Kotlin file.

### `KotlinFileStats.kt`

- **Purpose**: A container for the results of the Kotlin file analysis.
- **Key Attributes**:
  - `totalLines`: The total number of lines in the file.
  - `todoLines`: The number of lines containing TODO comments.
  - `function`: The longest function in the file.
  - `fileName`: The name of the analyzed file.
- **Key Methods**:
  - `getFunctionContent()`: Returns the content of the longest function.
  - `getFunctionLines()`: Returns the number of lines in the longest function.
  - `getFunctionName()`: Returns the name of the longest function.

This file encapsulates the data obtained from analyzing a Kotlin file and provides easy access to the calculated metrics.

## General Architecture

### 1. **PSI Utilization**:
The plugin leverages **PSI (Program Structure Interface)** to analyze the syntactic structure of Kotlin files. This allows access to specific nodes like classes, functions, and expressions within the file.

### 2. **Real-time Updates**:
The plugin uses **listeners** to monitor file changes and automatically update the displayed statistics. These listeners are connected to events like document edits and file selection changes within the IntelliJ editor.

### 3. **User Interface**:
The `ProjectStatsPanel` panel updates in real-time, displaying precise and detailed information about the Kotlin file that is currently being edited or viewed.

## Benefits for Developers

- **Code Quality Monitoring**: Track TODO comments and large functions, helping identify areas that may need refactoring or extra attention.
- **Efficient Navigation**: Quickly get an overview of the structure and complexity of a Kotlin file.
- **Continuous Feedback**: Stay informed about the file’s metrics while developing, enabling better decision-making and code management.

## Recent Changes

- A system of **listeners** has been implemented to allow real-time updates to statistics as the user edits or switches files in the editor.
- **Optimized Analysis**: The analysis now includes anonymous functions (lambdas) while excluding constructors and initialization blocks.
- The system now uses the `FileAnalyzerService` component for analyzing Kotlin files, improving code modularity and scalability.
- Changes to the UI now uses EditorTextField to display the statistics.


## Installation (manually)

1.Install Git;

2.Clone the repo using `ssh`: `git clone git@github.com:joseserize0222/Cognifire-Plugin-Application.git`

3.Open the basic_plugin directory in a console

4.Build the plugin: `gradlew buildPlugin`

5.Run the plugin: `gradlew runIde`

## Installation by latest release

1.Go to Releases, and click tags, then you will find a .zip with the latest uploaded version of the plugin.

2.Install the plugin from disk in IntelliJ IDEA, for more information you can click <a href="https://www.jetbrains.com/help/idea/managing-plugins.html#install_plugin_from_disk"> here </a>

Note(This could not be up to date)


## Acknowledgements

This plugin was created using the [intellij-platform-plugin-template](https://github.com/JetBrains/intellij-platform-plugin-template). Special thanks to JetBrains for providing robust tools and templates for plugin development.
