package sem2_1.common;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class SnapshotSaver {
    private SnapshotSaver() {
    }

    public static void save(Node node, File file) throws IOException {
        int width = Math.max(1, (int) Math.ceil(node.getLayoutBounds().getWidth()));
        int height = Math.max(1, (int) Math.ceil(node.getLayoutBounds().getHeight()));
        save(node, width, height, file);
    }

    public static void save(Node node, int width, int height, File file) throws IOException {
        WritableImage image = new WritableImage(Math.max(1, width), Math.max(1, height));
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        node.snapshot(parameters, image);

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        String format = detectImageFormat(file);
        ImageIO.write(bufferedImage, format, file);
    }

    private static String detectImageFormat(File file) {
        String name = file.getName().toLowerCase();
        if (name.endsWith(".png")) {
            return "png";
        }
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "jpg";
        }
        if (name.endsWith(".gif")) {
            return "gif";
        }
        return "png";
    }
}
