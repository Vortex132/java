package org.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import org.apache.commons.lang3.tuple.Pair;
import javax.imageio.ImageIO;
import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("images");
        List<Path> files;
        try (Stream<Path> stream = Files.list(path)){
            files = stream.toList();
            ForkJoinPool threads = new ForkJoinPool(2); // Liczba watkow

            long time = System.currentTimeMillis();
            threads.submit(() ->
                    files.parallelStream().forEach(file -> {
                        try {
                            BufferedImage original = ImageIO.read(file.toFile());
                            BufferedImage image = new BufferedImage(original.getWidth(),
                                    original.getHeight(),
                                    original.getType());

                            for (int i = 0; i < original.getWidth(); i++) {
                                for (int j = 0; j < original.getHeight(); j++) {
                                    int rgb = original.getRGB(i, j);
                                    Color color = new Color(rgb);
                                    Color outColor = new Color(color.getRed(), color.getBlue(), color.getGreen());
                                    int outRgb = outColor.getRGB();
                                    original.setRGB(i, j, outRgb);
                                }
                            }

                            for (int i = 0; i < original.getWidth(); i++) {
                                for (int j = 0; j < original.getHeight(); j++) {
                                    int rgb = original.getRGB(i, j);
                                    image.setRGB(i, original.getHeight() - j - 1, rgb);
                                }
                            }

                            File outputfile = new File("images_edited/" + file.getFileName());
                            ImageIO.write(image, "jpg", outputfile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
            ).get();
            System.out.println("Time: " + (System.currentTimeMillis() - time) / 1000.0 + "s");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// files.parallelStream().map(path -> {
//     try {
//         String name = String.valueOf(path.getFileName());
//         BufferedImage original = ImageIO.read(path.toFile());
//         Pair<String, BufferedImage> pair = Pair.of(name, original);
//         return pair;
//     } catch (IOException e) {
//         e.printStackTrace();
//     }
//     }).map(pair -> {
//         BufferedImage og = pair.getRight();
//         BufferedImage image = new BufferedImage(og.getWidth(), og.getHeight(), og.getType());
//         for (int i = 0; i < og.getWidth(); i++) {
//             for (int j = 0; j < og.getHeight(); j++) {
//                 int rgb = og.getRGB(i, j);
//                 Color color = new Color(rgb);
//                 Color outColor = new Color(color.getRed(), color.getBlue(), color.getGreen());
//                 int outRgb = outColor.getRGB();
//                 og.setRGB(i, j, outRgb);
//             }
//         }
//         return Pair.of(pair.getLeft(), og);
//     });