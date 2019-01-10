rem mvn clean package -DskipTests=true org.codehaus.cargo:cargo-maven2-plugin:1.7.0:run
call mvn -B -s settings.xml -DskipTests=true clean package
call java -jar target/dependency/webapp-runner.jar target/*.war
