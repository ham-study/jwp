package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Handler.ResourceHandler;
import Handler.UserHandler;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final ResourceHandler resourceHandler;
    private final UserHandler userHandler;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.resourceHandler = new ResourceHandler();
        this.userHandler = new UserHandler();
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()
        ) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = HttpRequest.from(bufferedReader);

            log.info("HttpRequest: {}", httpRequest);

            byte[] body = "Hello World".getBytes();
            if (resourceHandler.isPossible(httpRequest)) {
                body = resourceHandler.serve(httpRequest.getRequestUri());
            } else if (userHandler.isPossible(httpRequest)) {

                if (httpRequest.getRequestUri().equals("/user/create")) {
                    userHandler.createUser(
                        HttpRequestUtils.parseQueryString(
                            httpRequest.getHttpBody()
                        )
                    );

                    DataOutputStream dos = new DataOutputStream(out);
                    response302Header(dos);
                    responseBody(dos, body);
                    return;
                }
            }

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
