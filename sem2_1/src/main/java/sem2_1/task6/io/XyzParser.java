package sem2_1.task6.io;

import sem2_1.task6.model.Atom;
import sem2_1.task6.model.Molecule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class XyzParser {
    public Molecule parse(Path path) throws IOException {
        try (InputStream inputStream = Files.newInputStream(path)) {
            return parse(inputStream);
        }
    }

    public Molecule parse(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String countLine = reader.readLine();
            if (countLine == null) {
                throw new IllegalArgumentException("Пустой XYZ-файл.");
            }
            int count;
            try {
                count = Integer.parseInt(countLine.trim());
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Первая строка должна содержать количество атомов.");
            }

            String description = reader.readLine();
            if (description == null) {
                throw new IllegalArgumentException("Отсутствует строка с описанием молекулы.");
            }

            List<Atom> atoms = new ArrayList<>(count);
            for (int index = 0; index < count; index++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IllegalArgumentException("Недостаточно строк атомов. Ожидалось: " + count);
                }
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Некорректная строка атома: " + line);
                }
                try {
                    atoms.add(new Atom(
                            parts[0],
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                } catch (NumberFormatException exception) {
                    throw new IllegalArgumentException("Некорректные координаты атома: " + line);
                }
            }

            String extra;
            while ((extra = reader.readLine()) != null) {
                if (!extra.trim().isEmpty()) {
                    throw new IllegalArgumentException("В файле присутствуют лишние строки после описания атомов.");
                }
            }
            return new Molecule(description, atoms);
        }
    }
}
