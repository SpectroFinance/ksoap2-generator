package helloworld;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

public final class HelloWorld {
    public  java.lang.String say(java.lang.String input) throws Exception {
        SoapObject _client = new SoapObject("", "say");
        _client.addProperty("input", input);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        _ht.call("", _envelope);
        return (java.lang.String) _envelope.getResponse();
    }


    public  int getYear() throws Exception {
        SoapObject _client = new SoapObject("", "getYear");
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.bodyOut = _client;
        AndroidHttpTransport _ht = new AndroidHttpTransport(Configuration.getWsUrl());
        _ht.call("", _envelope);
        return Integer.parseInt(_envelope.getResponse().toString());
    }


}
