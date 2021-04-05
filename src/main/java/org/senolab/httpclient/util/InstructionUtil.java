package org.senolab.httpclient.util;

public class InstructionUtil {
    public static void printInstructions() {
        String instruction = """
                SenoHttpClient v1.1.0
                
                You have pass on an invalid number of arguments. This script accept 2 - 4 arguments (see below)
                Command line + argument description
                $http <http method> <Full URL including hostname and protocol surrounded with double quotes> <comma-delimeted file containing headerName, headerValue or special string** - see below> <file containing the HTTP request body to be send in the request
                **you can specify the below shortcut as the 3rd argument for the following headers
                   - json --> 'content-type: application/json'
                   - xml --> 'content-type: application/xml'
                   
                Example:
                Sample with 2 arguments:
                $http GET "https://www.senolab.org/"
                
                Sample with 3 arguments:
                $http GET "https://www.senolab.org/" /home/esenopra/headers.txt 
                     where headers.txt contains 'content-type, application/json, accept-encoding, gzip'
                        
                Sample with 4 arguments:
                $http POST "https://www.senolab.org/api/v1/" json /home/esenopra/json_body.json
                     where json_body.json contains '{"name":"Ernesto"}'
                     
                """;
        System.out.println(instruction);
    }
}
