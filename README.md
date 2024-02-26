# Local Storage Management Library

The repository hosts a comprehensive Java project aimed at managing local file storage systems. It encapsulates functionalities for creating, configuring, and managing directories and files, including operations like upload, download, deletion, and movement of files within the local storage. Designed with extensibility and ease of use in mind, this project serves as a robust framework for applications requiring efficient local storage management, offering developers a solid base for building or extending their own storage solutions.

This repository showcases a practical example of implementing a specification for local file storage management as part of a software components course. It demonstrates how to create dependencies and configure a project using Maven, offering a hands-on approach to understanding component-based software engineering. This project serves as a clear illustration of applying theoretical concepts in software components to a tangible Java application, emphasizing the importance of modular design and dependency management in modern software development.



# Getting Started

## Prerequisites
Java 11 or later
Maven or Gradle (for dependency management)
Access to Google Drive API (for the Google Drive implementation)


## Building from Source
To build the Storage Management Library from source and install it on your local machine, follow these steps:

1.Clone the repository to your local machine
2.Use Maven to clean the existing builds and compile the project: mvn clean install



After successfully building and installing the project, you can include it as a dependency in your Maven projects by adding the following to your pom.xml file (make sure to replace x.y.z with the actual version of the library you've installed):
<dependency>
    <groupId>com.yourdomain</groupId>
    <artifactId>storage-management-library</artifactId>
    <version>x.y.z</version>
</dependency>





# Testing
To test the Storage Management Library, you will need to download and install two additional repositories, along with the test repository. Follow the steps below to set up your environment for testing.

Step 1: Clone the Repositories
First, clone this repository as well as two additional repositories required for testing(SK_Project1_GoogleDriveImplementation, SK_Project1_LocalStorageImplementation, SK_Project1_Specification), also you should clone SK_Project1_TestApk repository. 

Step 2: Install the Projects
Navigate to each cloned repository's root directory and run the Maven install command. This will compile the projects and install them to your local Maven repository, making them available as dependencies.
Command for all three repositories (SK_Project1_Specification, SK_Project1_GoogleDriveImplementation, SK_Project1_LocalStorageImplementation) is mvn clean install

Step 3: Run main in project SK_Project1_TestApk











