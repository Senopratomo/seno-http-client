package org.senolab.httpclient;

import org.senolab.httpclient.SenoHttpRequest;
import static org.senolab.httpclient.util.InstructionUtil.printInstructions;

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
}
