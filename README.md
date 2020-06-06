# Summary
This repository contains my solution's source code for Mountain State Software Solutions' coding challenge.

## Running the project
Note: This application requires openJDK 11 and openJFX 11 in order to run.

1.You can download this source code as ZIP and extract then open the project in IntelliJ <br/> or
by cloning this repository by running the following command and open the downloaded project in IntelliJ.

* $ git clone https://github.com/BananaTigeer/maven-javafx-ms3.git


2.Once opened in IntelliJ, maven will download dependencies.


3.To compile, go to Maven -> plugins -> javafx -> javafx:compile


4.To run, Maven -> plugins -> javafx -> javafx:run 

5.Then load the CSV file and begin parsing file.
\
\
The output files will be placed in the project's root directory.
\
\
The output files and their descriptions are the following:

* The <input-filename>.db contains valid records parsed from input csv file

* The <input-filename>.log contains statistics for received, valid, and failed records.

* The <input-filename>.csv contains the invalid records from input csv file. 
  
\
Dependencies/third-party libraries used:
* univocity-parser

* slf4j-simple

* sqlite-jdbc

## Overview
#### Approach
The development process follows the following steps:<br/>
1. Defining and breaking down requirements - Identifying requirements and breaking them down <br/> 
into smaller pieces followed by defining assumptions for each requirement and figuring out tools needed.<br/> 
                    
2. Designing - Structuring flow of a requirement and its corresponding assumptions.
3. Building - Developing code following design structure.
4. Testing - Testing output for errors and if it matches expected result.
5. Reviewing - Reviewing code if there needs to be simplified or shortened code, refactoring for readability, or optimized.
6. Improving - Making small modifications based on review and putting comments where it needs to be applied.
7. Going back to step 2 and develop the next requirement.
\
\
Each requirements are added piece by piece. I've also tried to keep my code simple as much as I can <br/> 
and named methods such that it makes the code self-explanatory.

#### Design Choices
Simplicity, readability, maintainability, and speed<br/>
* Requirements functionality are split into classes that carry out different processes.

* The code design consists of loosely coupled classes for simplicity, easier readability, and maintainability.

* The code relies on PreparedStatements and batching using transactions provided by JDBC to optimize insertion of  <br/> 
thousands of data for fast insertions.

#### Assumptions

###### Graphical User Interface
* The application has to have a GUI for selecting an input file. 

###### Validation
* Any row that contains null in the first 10 columns and rows with data on columns greater than 10 will be <br/>
identified as a bad record.

* Rows with invalid email address patterns will be tagged as bad record.

* Name and Gender columns must not contain symbols and numbers.

* Price column must be validated to contain only decimal values.

* Boolean columns must be validated to contain either true or false.

* Columns containing company names, image data, city will only be checked for null values.

* If a column fails validation, the row containing the column data will be identified as a bad record.

###### Writing files
* Existing output files with similar file name to input file must not be overwritten. Create a new file and have <br/>


###### Performance
* Database insertion must be done in batches and transaction as inserting rows one by one will be slow.






