package be.pix.pewpewbar.com;

import jssc.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Component
public class JsscCommunicationImpl implements JsscCommunication {

    private static boolean init = false;

    private static String[] demo = {"M121","G90","M92 X80 Y80 Z80"};

    private int baudrate = SerialPort.BAUDRATE_57600;

    private SerialPort serialPort;

    /**
     * Defaults to the first port.
     * Should probably look for successful port.
     */
    public JsscCommunicationImpl(){
        try {
            String[] ports = pokePorts();
            if(ports.length!=0)
            init(ports[0]);
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] pokePorts() {
        String[] portNames = SerialPortList.getPortNames();
        if (portNames.length == 0) {
            return new String[0];
        }
        return portNames;
    }

    @Override
    public void setBaudRate(int baudRate){
        this.baudrate = baudRate;
    }


    private void init(String port) throws SerialPortException, InterruptedException {
        if(!init) {
            System.out.println("Initialising port");
            serialPort = new SerialPort(port);
            serialPort.openPort();
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR  );
            serialPort.setParams(baudrate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

            TimeUnit.SECONDS.sleep(5);

            if(!init) {
                for (String s : demo) {
                    serialPort.writeString(s + "\n");
                }
                init = true;
            }
            System.out.println("Port initialised.");
        }

    }

    @Override
    public void sendMessageOverPort(String port, String msg){
        try {
            serialPort.writeString(msg + "\n");
            serialPort.writeString("M84\n");

        }
        catch (SerialPortException ex) {
            System.out.println("There are an error on writing string to port т: " + ex);
        }
    }


    @Override
    public void openNewPort(String port){
        if(serialPort.isOpened()) {
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }

        init = false;
        try {
            init(port);
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class PortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    System.out.println("Received response: " + receivedData + "\n\n");
                }
                catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }

    }

}
