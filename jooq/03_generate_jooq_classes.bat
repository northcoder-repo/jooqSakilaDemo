@echo off

rmdir /S /Q generated-sources\org\web\ici\jooq\
java -classpath lib/* org.jooq.codegen.GenerationTool jooq-classes-config.xml

pause
