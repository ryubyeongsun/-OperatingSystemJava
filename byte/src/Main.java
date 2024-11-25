import java.util.*;


public class Main {

    public static List<Byte> byteStuff(List<Byte> data) {
        List<Byte> stuffedData = new ArrayList<>();

        for (byte b : data) {
            if (b == 0x7E || b == 0x1B) {
                stuffedData.add((byte) 0x1B);
            }
            stuffedData.add(b);
        }

        return stuffedData;
    }

    public static List<Byte> byteUnstuff(List<Byte> stuffedData) {
        List<Byte> unstuffedData = new ArrayList<>();
        boolean dcode = false;

        for (byte b : stuffedData) {
            if (dcode) {
                unstuffedData.add(b);
                dcode = false;
            } else {
                if (b == 0x1B) {
                    dcode = true;
                } else {
                    unstuffedData.add(b);
                }
            }
        }

        return unstuffedData;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for(int i=0; i<10; i++) {
        System.out.print("D ");
        String input = scanner.nextLine();
        String[] hex = input.split("\\s+");

        List<Byte> inputData = new ArrayList<>();
        for (String hexnum : hex) {
            int value = Integer.parseInt(hexnum, 16);
            inputData.add((byte) value);
        }

        List<Byte> stuffedData = byteStuff(inputData);
        List<Byte> unstuffedData = byteUnstuff(stuffedData);

        System.out.print("=>Tx ");
        for (byte b : stuffedData) {
            System.out.print(String.format("%02X ", b));
        }
        System.out.println();
       System.out.print("RX ");
       for (byte b : stuffedData) {
           System.out.print(String.format("%02X ", b));
       }

       
        System.out.println();

        System.out.print("=> D ");
        for (byte b : unstuffedData) {
            System.out.print(String.format("%02X ", b));
        }
        System.out.println();
    }
    }
}//20194354류병선

