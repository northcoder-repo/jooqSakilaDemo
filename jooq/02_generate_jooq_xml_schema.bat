@echo off

REM this allows us to use the sakila_schema.xml file as 
REM the database source, in the sakila project (no need for a 
REM hard-coded DB connection in that project's jooq config file).

del generated-sources\schema\information_schema.xml

java -classpath lib/* org.jooq.codegen.GenerationTool jooq-xml-schema-config.xml

copy /Y generated-sources\schema\information_schema.xml ^
  ..\src\main\resources\sakila_schema.xml

pause
