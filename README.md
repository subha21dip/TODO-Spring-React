### Set up

Install NodeJs.

#### [Frontend](./frontend/todo-app-pwa) - 


Open ./frontend/todo


Install the modules - 
`npm i` 


Start the application on local host - 
`npm start`


The application will run on `http://localhost:4200`


1. React JS
2. Material UI
3. Axios - API Calls

#### [Backend](./restful-web-services) - 

Install JDK 8


Open ./restful-web-services on any IDE (IntelliJ/Eclipse etc.) and run the application.


The backend will run on `http://localhost:8080`

OR with maven

run maven clean install
mvn spring-boot:run

Design
------------
React based UI application connecting with Spring boot based rest api platform through http. CORS is enabled in server side only for port 4200 where the UI client is running.

API Documentation
------------------

Find API call details in Resources folder.

GetAll - get all existing tasks.
GetByID - get an unique task with id.
GetTaskWithFilter - find tasks with provided filter as task name and description
GetTaskWithPagination - Get tasks for a particular page with link to previous and next apge.
CreateNewTask - Post call to cretae a new task.
UpdateTask - PUT call to update an existing task.


