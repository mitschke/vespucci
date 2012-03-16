 #!/bin/bash

curl --digest --user admin:ADMINPWD --request POST http://localhost:9001/vespucci/shutdown -H "accept: application/xml" -H "Content-Type: application/xml" -d "<shutdown/>"
echo "\nWaiting for 10 seconds so that SADServer can close gracefully..."
sleep 10
