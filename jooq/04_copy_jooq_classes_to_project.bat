@echo off

rmdir /S /Q ..\src\main\java\org\northcoder\sakila\jooq\

xcopy generated-sources\org\northcoder\sakila\jooq\ ..\src\main\java\org\northcoder\sakila\jooq\ /E

pause
