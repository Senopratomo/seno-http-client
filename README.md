<h1>Seno HTTP Client</h1>
Seno HTTP Client is a wrapper for Java 11 new HTTP Client API.

To use it, compile and create .jar (using Maven or IDE)
It takes 1 required argument - full URL and 1 optional argument - full path to 
text file containing comma-delimeted string with the following pattern: 
request_header, request_header_value, request_header, request_header_value,...  

Sample:
java -jar seno-http-client.jar "https://www.example.com" "C:\INPUT\input.txt"

where "C:\INPUT\input.txt" contains:
content-type, application/json, accept-encoding, gzip, pragma, no-cache


