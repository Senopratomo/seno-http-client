<h1>Seno HTTP Client</h1>
Seno HTTP Client is a wrapper for Java 11 new HTTP Client API.

<p>To use it, compile and create .jar (using Maven or IDE)</p>
<p>This script accept 2 - 4 arguments (see below)<br>
Command line + argument description:<br>
$http <http method> <Full URL including hostname and protocol surrounded with double quotes> <comma-delimeted file containing headerName, headerValue> <p containing the HTTP request body to be send>
</p>
<p>Example:<br>
Sample with 2 arguments:<br>
$http GET "https://www.senolab.org/"<br>
<br>
Sample with 3 arguments:<br>
$http GET "https://www.senolab.org/" /home/esenopra/headers.txt<br>
where headers.txt contains 'content-type, application/json, accept-encoding, gzip'<br>
<br>
Sample with 3 arguments:<br>
$http POST "https://www.senolab.org/api/v1/" /home/esenopra/headers.txt /home/esenopra/json_body.json<br>
where headers.txt contains 'content-type, application/json'<br>
where json_body.json contains '{"name":"Ernesto"}<br>

