#  Project Management Mobile Application 📝

This Android-based application streamlines project and task management for professionals of all levels. Whether you're a seasoned project manager, a team leader, or simply someone striving to enhance personal productivity, this app provides the tools you need to stay organized and on top of your work. 

## Features 🚀

- 🔐 **User Authentication**: Users can create an account and securely log in to access their personal projects and tasks.
- 📁 **Project Management**: Create new projects, view a list of existing ones, and delete projects when they are no longer needed.
- ✅ **Task Management**: For each project, users can create tasks, update their status and delete tasks as needed.
- ⏳**Real-time updates and Progress Tracking**: Projects and tasks are updated in real-time, ensuring that progress is always visible and accessible.

## Tech Stack 🛠️

- **Android Studio**: Development IDE
- **Android SDK**: Minimum SDK 21, for handling app logic.
- **Kotlin** and **Java**: Programming languages in this project.
- **SQLite**: For local data storage and project/task management.
- **XML**: Used for designing the app layout

## Project Structure 🗂️

The app follows a clean architecture pattern with separation between the UI, helper functions, and database access. Here's a brief overview of the core components:

```bash
📂 app\src\main 
 ┣ 📂 java\com\example\projectmanagerapplication
 ┃ ┣ 📜 AddProjectActivity.kt	# Allows users to create a new project
 ┃ ┣ 📜 AddTaskActivity.kt		# Allows users to add tasks to a project
 ┃ ┣ 📜 Helper.java       		# Handles SQLite operations for projects and tasks
 ┃ ┣ 📜 LoginActivity.kt		# Handles user login
 ┃ ┣ 📜 MainActivity.kt		# Displays the list of projects
 ┃ ┣ 📜 Project.java			# Defines the Project data model
 ┃ ┣ 📜 ProjectActivity.kt		# Manages individual projects and their tasks
 ┃ ┣ 📜 SignUpActivity.kt		# Handles new user registration
 ┃ ┣ 📜 Task.java			# Defines the Task data model
 ┃ ┗ 📜 User.java			# Defines the User data model
 ┣ 📂 res
 ┃ ┣ 📂 drawable			# Icons and image assets
 ┃ ┣ 📂 layout		  	# XML layout files for UI design
 ┃ ┗ 📂 values		    	# Colors, strings, and other resources
 ┗  …

```

## Setup Instructions 🔧

To get the project up and running on your local machine:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Hiba-Chaabnia/Project-Management-Mobile-App.git
   ```

2. **Open with Android Studio**: 
   - Launch **Android Studio** and select `Open an existing project`.
   - Navigate to the cloned repository and open it.

3. **Build the app**:
   - Make sure to sync your Gradle files and resolve dependencies.
   - Connect your Android device or emulator and run the app.

4. **Run the App**:  
   - Make sure to connect a device or start an emulator in Android Studio.
   - Click the ▶️ "Run" button to build and launch the app.


## Diagrams 🖼️

To better understand the application's structure and flow, refer to the following diagrams:

* **Class Diagram** 🔗

<p align="center">
  <img src="./Class%20Diagram.svg" alt="Class Diagram" width="80%"/>
</p>

* **Sequence Diagram** 🔄
<div style="text-align: center;">
  
</div>

<p align="center">
  <img src="./Sequence%20Diagram.svg" alt="Sequence Diagram" width="80%"/>
</p>

## Future Improvements 🚧

- 🔐 **Password Encryption**: Implement better security measures for storing user passwords.
- 🔔 **Notifications**: Add push notifications to remind users of upcoming task deadlines.
- 🌐 **Cloud Sync**: Add support for cloud synchronization so users can access their projects across multiple devices.
- 📊 **Analytics Dashboard**: Provide users with analytics view to track their progress over time.
 
## Contribution Guidelines 🤝

Contributions are welcome! If you'd like to make changes to the project or report bugs, feel free to open an issue or submit a pull request.

## Contact 📬

If you have any questions or feedback, don't hesitate to reach out:

<p align="center">
  <a href="https://linkedin.com/in/hiba-chaabnia" target="_blank">
    <img src="https://skillicons.dev/icons?i=linkedin" alt="LinkedIn" width="40" height="40"  />
  </a>
  
  <a href="mailto:hiba.chaabnia.pro@gmail.com">
    <img src="https://skillicons.dev/icons?i=gmail" alt="Gmail" width="40" height="40" />
  </a>
</p>
 
