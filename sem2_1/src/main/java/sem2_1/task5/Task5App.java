package sem2_1.task5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Task5App extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(8));

        TableView<LanguageEntry> table = new TableView<>();
        table.setEditable(true);

        TableColumn<LanguageEntry, String> languageColumn = new TableColumn<>("Язык");
        languageColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        languageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        languageColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        TableColumn<LanguageEntry, String> authorColumn = new TableColumn<>("Автор");
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setOnEditCommit(event -> event.getRowValue().setAuthor(event.getNewValue()));

        TableColumn<LanguageEntry, Integer> yearColumn = new TableColumn<>("Год");
        yearColumn.setCellValueFactory(data -> data.getValue().yearProperty().asObject());
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        yearColumn.setOnEditCommit(event -> event.getRowValue().setYear(event.getNewValue()));

        table.getColumns().setAll(languageColumn, authorColumn, yearColumn);
        table.setItems(seedData());
        table.getSortOrder().add(yearColumn);

        TextField languageField = new TextField();
        TextField authorField = new TextField();
        TextField yearField = new TextField();

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(6);
        form.addRow(0, new Label("Язык"), languageField);
        form.addRow(1, new Label("Автор"), authorField);
        form.addRow(2, new Label("Год"), yearField);

        Button addButton = new Button("Добавить");
        addButton.setOnAction(event -> {
            LanguageEntry newEntry = createEntry(languageField, authorField, yearField);
            if (newEntry == null) {
                return;
            }
            table.getItems().add(newEntry);
            table.getSelectionModel().select(newEntry);
        });

        Button updateButton = new Button("Обновить выбранную");
        updateButton.setOnAction(event -> {
            LanguageEntry selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                new Alert(Alert.AlertType.WARNING, "Сначала выберите строку в таблице.").showAndWait();
                return;
            }
            LanguageEntry edited = createEntry(languageField, authorField, yearField);
            if (edited == null) {
                return;
            }
            selected.setName(edited.getName());
            selected.setAuthor(edited.getAuthor());
            selected.setYear(edited.getYear());
            table.refresh();
        });

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            languageField.setText(newValue.getName());
            authorField.setText(newValue.getAuthor());
            yearField.setText(String.valueOf(newValue.getYear()));
        });

        CheckBox showLanguage = new CheckBox("Показывать \"Язык\"");
        showLanguage.setSelected(true);
        showLanguage.selectedProperty().addListener((observable, oldValue, newValue) -> languageColumn.setVisible(newValue));

        CheckBox showAuthor = new CheckBox("Показывать \"Автор\"");
        showAuthor.setSelected(true);
        showAuthor.selectedProperty().addListener((observable, oldValue, newValue) -> authorColumn.setVisible(newValue));

        CheckBox showYear = new CheckBox("Показывать \"Год\"");
        showYear.setSelected(true);
        showYear.selectedProperty().addListener((observable, oldValue, newValue) -> yearColumn.setVisible(newValue));

        HBox buttons = new HBox(8, addButton, updateButton);
        buttons.setAlignment(Pos.CENTER_LEFT);

        VBox rightPanel = new VBox(10,
                new Label("Управление данными"),
                form,
                buttons,
                new Label("Видимость столбцов"),
                showLanguage,
                showAuthor,
                showYear
        );
        rightPanel.setPadding(new Insets(6));
        rightPanel.setPrefWidth(320);

        root.setCenter(table);
        root.setRight(rightPanel);

        Scene scene = new Scene(root, 1080, 720);
        stage.setTitle("Task 5 - TableView");
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<LanguageEntry> seedData() {
        return FXCollections.observableArrayList(
                new LanguageEntry("C", "Деннис Ритчи", 1972),
                new LanguageEntry("C++", "Бьерн Страуструп", 1983),
                new LanguageEntry("Python", "Гвидо ван Россум", 1991),
                new LanguageEntry("Java", "Джеймс Гослинг", 1995),
                new LanguageEntry("JavaScript", "Брендон Айк", 1995),
                new LanguageEntry("C#", "Андерс Хейлсберг", 2001),
                new LanguageEntry("Scala", "Мартин Одерски", 2003)
        );
    }

    private LanguageEntry createEntry(TextField languageField, TextField authorField, TextField yearField) {
        String language = languageField.getText().trim();
        String author = authorField.getText().trim();
        int year;
        try {
            year = Integer.parseInt(yearField.getText().trim());
        } catch (NumberFormatException exception) {
            new Alert(Alert.AlertType.ERROR, "Год должен быть целым числом.").showAndWait();
            return null;
        }
        if (language.isEmpty() || author.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Поля \"Язык\" и \"Автор\" не должны быть пустыми.").showAndWait();
            return null;
        }
        return new LanguageEntry(language, author, year);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
