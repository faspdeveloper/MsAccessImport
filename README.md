# MsAccessImport
Ms Access Import
This is a Spring Boot command line application that imports a MS Access file into a MySQL database.

You need to have the following setup before running it
MySql Db: pipeline
Username: pipelineUser
Password: pass
The user should have access to the pipeline db.

Make sure the pipeline db does not have any tables in it.

To run the application from command line use the following command

java -jar importMsAccess-v0.001.jar <%MS Access db path%>

