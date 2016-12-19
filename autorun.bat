call cd vagrant
call vagrant up
cd ..
call mvn test
cd vagrant
call vagrant halt
cd ..
.\target\surefire-reports\html\index.html