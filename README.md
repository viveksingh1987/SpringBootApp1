# Spring Boot- Task Management API Suite


The table structure is provided below:

**Table name** - *tasks*

**Table columns:**
- *id* int not null generated always as identity,
- *title* varchar(256) not null,
- *description* varchar(1024),
- *due_date* date,
- *status* varchar(10),
- *creation_date* date not null,
- *primary key (id)*


## API Endpoints

You will need to provide APIs for the following actions:  
 
1. Fetch all tasks.
1. Fetch all overdue tasks.
1. Fetch data for a single task.
1. Add a new task.
1. Modify a task.
1. Delete a task.


## Run the application

The application will be available at [http://localhost:8080](http://localhost:8080)  
  
