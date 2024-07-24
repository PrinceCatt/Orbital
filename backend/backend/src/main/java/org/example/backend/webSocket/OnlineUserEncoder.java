package org.example.backend.webSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class OnlineUserEncoder implements Encoder.Text<OnlineUserList> {

    @Override//Initialization does nothing
    public void init(EndpointConfig config) {}

    @Override//Encoding process(Object → JSON)
    public String encode(OnlineUserList onlineUserList) throws EncodeException {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(onlineUserList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override//Do nothing to destroy
    public void destroy() {}

}
