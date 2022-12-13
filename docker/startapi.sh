

echo "hello web :)"
if [ -f "/config/configfile" ]; 
then
    echo "Starting with  supplied  config file  :) "
    java -jar /opt/app/app.jar --spring.config.location=/config/configfile

else
echo "Starting with default config file  :( \n please provide config file"
     java -jar /opt/app/app.jar
fi 

