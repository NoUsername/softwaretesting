#!/bin/sh

/bin/sh wait-for.sh $WAIT_FOR -t 120 -- java -Djava.security.egd=file:/dev/./urandom -jar /app.jar