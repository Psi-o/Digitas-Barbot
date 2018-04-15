package be.pix.pewpewbar.com;

public interface JsscCommunication {

    String[] pokePorts();

    void setBaudRate(int baudRate);

    void sendMessageOverPort(String port, String msg);

    void openNewPort(String port);
}
