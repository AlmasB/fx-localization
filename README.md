## fx-localization
A tiny JavaFX library that provides localization API.

### Usage

```
public class BasicApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXLocal local = new FXLocal(Locale.ENGLISH);
        local.addLocaleData(Locale.ENGLISH, Map.of("someKey", "someValueEnglish"));
        local.addLocaleData(Locale.FRENCH, Map.of("someKey", "someValueFrench"));
        
        Button button = new Button();
        button.textProperty().bind(local.localizedStringBinding("someKey"));
        button.setOnAction(e -> {
            local.setSelectedLocale(Locale.FRENCH);
        });

        stage.setScene(new Scene(new StackPane(button), 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```