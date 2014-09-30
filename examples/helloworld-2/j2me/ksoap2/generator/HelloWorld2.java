package ksoap2.generator;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;

public final class HelloWorld2 {
    public  void say(java.lang.String input) throws Exception {
        SoapObject _client = new SoapObject("", "say");
        _client.addProperty("input", input);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.bodyOut = _client;
        HttpTransport _ht = new HttpTransport(Configuration.getWsUrl());
        _ht.call("", _envelope);
    }


    public  void say1(ksoap2.generator.complexe.Composite composite) throws Exception {
        SoapObject _client = new SoapObject("", "say1");
        _client.addProperty("composite", composite);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.bodyOut = _client;
        HttpTransport _ht = new HttpTransport(Configuration.getWsUrl());
        _ht.call("", _envelope);
    }


    public  ksoap2.generator.complexe.Composite say2(ksoap2.generator.complexe.Composite com) throws Exception {
        SoapObject _client = new SoapObject("", "say2");
        _client.addProperty("com", com);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.bodyOut = _client;
        HttpTransport _ht = new HttpTransport(Configuration.getWsUrl());
        _ht.call("", _envelope);
        SoapObject _ret = (SoapObject) _envelope.getResponse();
        int _len = _ret.getPropertyCount();
        ksoap2.generator.complexe.Composite _returned = new ksoap2.generator.complexe.Composite();
        for (int _i = 0; _i < _len; _i++) {
            _returned.setProperty(_i, _ret.getProperty(_i));        }
        return _returned;
    }


    public  void say3(ksoap2.generator.complexe.Composite com, java.lang.String st) throws Exception {
        SoapObject _client = new SoapObject("", "say3");
        _client.addProperty("com", com);
        _client.addProperty("st", st);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.bodyOut = _client;
        HttpTransport _ht = new HttpTransport(Configuration.getWsUrl());
        _ht.call("", _envelope);
    }


}
