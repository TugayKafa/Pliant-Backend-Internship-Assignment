import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        File source = new File("D:\\IntelliJ IDEA\\Pliant\\Assignment2\\fibonacci.png");
        File output = new File("D:\\IntelliJ IDEA\\Pliant\\Assignment2\\result.png");

        int sourceFileHeight = ImageIO.read(source).getHeight();
        int n = Integer.parseInt(scanner.nextLine());
        int m = Integer.parseInt(scanner.nextLine());

        extendImage(n, m, source, output);
        writeTitle(output, output);

        int iteration = 1;
        for (int i = n; i <= m; i++) {

            int num = fibonacci(i - 1);

            writeNumber(num, iteration, sourceFileHeight, output, output);

            iteration++;
        }
    }

    private static int fibonacci(int i) {

        if (i == 0 || i == 1) {
            return 1;
        }

        return fibonacci(i - 1) + fibonacci(i - 2);
    }

    private static void writeTitle(File source, File destination) throws IOException {

        String title = "ФибоНачи";

        BufferedImage image = ImageIO.read(source);

        Graphics2D w = (Graphics2D) image.getGraphics();

        w.drawImage(image, 1, 2, null);
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);

        w.setComposite(alpha);
        w.setColor(Color.WHITE);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        FontMetrics fontMetrics = w.getFontMetrics();

        int positionX = (image.getWidth() - fontMetrics.stringWidth(title)) / 2;
        int positionY = 30;

        w.drawString(title, positionX, positionY);
        ImageIO.write(image, "png", destination);

        w.dispose();
    }

    private static void extendImage(int n, int m, File source, File destination) throws IOException {

        BufferedImage image = ImageIO.read(source);
        int imageType = "png".equalsIgnoreCase("png") ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;

        BufferedImage bold = new BufferedImage(image.getWidth(), image.getHeight() + (m - n + 1) * 31, imageType);

        Graphics2D w = (Graphics2D) bold.getGraphics();

        w.drawImage(image, 0, 0, null);
        w.setColor(Color.BLACK);
        w.fillRect(0, image.getHeight(), image.getWidth(), (m - n + 1) * 31);

        ImageIO.write(bold, "png", destination);

        w.dispose();
    }

    private static void writeNumber(int number, int iteration, int sourceFileHeight, File source, File destination) throws IOException {

        BufferedImage image = ImageIO.read(source);

        String text;
        if (number == 1) {
            text = "Като зема 1 дърво ...";
        } else {
            text = String.format("Като зема %d дървета ...", number);
        }

        Graphics2D w = (Graphics2D) image.getGraphics();

        w.drawImage(image, 0, 0, null);
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);

        w.setComposite(alpha);
        w.setColor(Color.WHITE);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        int positionX = 10;
        int positionY = sourceFileHeight + iteration * 30;

        w.drawString(text, positionX, positionY);
        ImageIO.write(image, "png", destination);

        w.dispose();
    }
}
