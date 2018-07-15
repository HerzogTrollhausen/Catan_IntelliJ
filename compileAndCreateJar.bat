set path="C:\Program Files\Java\jdk1.8.0_91\bin"
cd src
javac -d ..\meinOutput Main.java
cd ..\meinOutput
jar cfm ..\Catan.jar ..\src\Manifest *.class
cd ..
jar cfM Catan.zip Catan.jar Bilder
pause