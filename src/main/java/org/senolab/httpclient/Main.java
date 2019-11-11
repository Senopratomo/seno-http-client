package org.senolab.httpclient;

import org.senolab.httpclient.SenoHttpRequest;

public class Main {

    public static void main(String[] args) {
        SenoHttpRequest senoHttpRequest;
        if(args.length >= 2 || args.length <= 4) {
            switch (args.length) {
                case 2:
                    senoHttpRequest = new SenoHttpRequest(args[0], args[1]);
                    senoHttpRequest.execute();
                    break;
                case 3:
                    senoHttpRequest = new SenoHttpRequest(args[0], args[1], args[2]);
                    senoHttpRequest.execute();
                    break;
                case 4:
                    senoHttpRequest = new SenoHttpRequest(args[0], args[1], args[2], args[3]);
                    senoHttpRequest.execute();
                    break;
                default:
                    printInstructions();
            }
        } else {
            printInstructions();
        }
    }

    private static void printInstructions() {
        System.out.println("You have pass on an invalid number of arguments. This script accept 2 - 4 arguments (see below)");
        System.out.println("Command line + argument description\n$http <http method> <Full URL including hostname and protocol surrounded with double quotes> " +
                "<comma-delimeted file containing headerName, headerValue or special string* - see below> <file containing the HTTP request body to be send>");
        System.out.println("* you can specify the below shortcut as the 3rd argument for the following headers:\n" +
                "- json --> 'content-type: application/json'\n" +
                "- xml --> 'content-type: application/xml'\n\n");
        System.out.println("Example:");
        System.out.println("Sample with 2 arguments:\n$http GET \"https://www.senolab.org/\"" );
        System.out.println("Sample with 3 arguments:\n$http GET \"https://www.senolab.org/\" /home/esenopra/headers.txt\n where headers.txt contains 'content-type, application/json, accept-encoding, gzip'");
        System.out.println("Sample with 4 arguments:\n$http POST \"https://www.senolab.org/api/v1/\" /home/esenopra/headers.txt /home/esenopra/json_body.json\n " +
                "where headers.txt contains 'content-type, application/json'\n " +
                "where json_body.json contains '{\"name\":\"Ernesto\"}");
    }


}
