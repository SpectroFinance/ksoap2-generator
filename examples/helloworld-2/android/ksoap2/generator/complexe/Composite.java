package ksoap2.generator.complexe;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public final class Composite extends SoapObject {
    private double d;

    private java.lang.Integer i1;

    private java.lang.String st;

    public Composite() {
        super("", "");
    }
    public void setD(double d) {
        this.d = d;
    }

    public double getD(double d) {
        return this.d;
    }

    public void setI1(java.lang.Integer i1) {
        this.i1 = i1;
    }

    public java.lang.Integer getI1(java.lang.Integer i1) {
        return this.i1;
    }

    public void setSt(java.lang.String st) {
        this.st = st;
    }

    public java.lang.String getSt(java.lang.String st) {
        return this.st;
    }

    public int getPropertyCount() {
        return 3;
    }

    public Object getProperty(int __index) {
        switch(__index)  {
        case 0: return new Double(d);
        case 1: return i1;
        case 2: return st;
        }
        return null;
    }

    public void setProperty(int __index, Object __obj) {
        switch(__index)  {
        case 0: d = Double.parseDouble(__obj.toString()); break;
        case 1: i1 = (java.lang.Integer) __obj; break;
        case 2: st = (java.lang.String) __obj; break;
        }
    }

    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
        switch(__index)  {
        case 0:
            __info.name = "d";
            __info.type = Double.class; break;
        case 1:
            __info.name = "i1";
            __info.type = java.lang.Integer.class; break;
        case 2:
            __info.name = "st";
            __info.type = java.lang.String.class; break;
        }
    }

}
