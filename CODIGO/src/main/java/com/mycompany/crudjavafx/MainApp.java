package com.mycompany.crudjavafx;

import com.mycompany.crudjavafx.dao.UsuarioDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private TableView<Usuario> table;
    private TextField txtNome, txtEmail, txtTelefone;
    private UsuarioDAO usuarioDAO;

    @Override
    public void start(Stage primaryStage) {
        usuarioDAO = new UsuarioDAO();

        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        table = new TableView<>();
        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());

        TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Usuario, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefone()));

        table.getColumns().addAll(colId, colNome, colEmail, colTelefone);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        txtNome = new TextField();
        txtEmail = new TextField();
        txtTelefone = new TextField();

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(new Label("Email:"), 0, 1);
        form.add(txtEmail, 1, 1);
        form.add(new Label("Telefone:"), 0, 2);
        form.add(txtTelefone, 1, 2);

        Button btnAdd = new Button("Adicionar");
        btnAdd.setOnAction(e -> addUsuario());

        Button btnUpdate = new Button("Atualizar");
        btnUpdate.setOnAction(e -> updateUsuario());

        Button btnDelete = new Button("Deletar");
        btnDelete.setOnAction(e -> deleteUsuario());

        form.add(btnAdd, 0, 3);
        form.add(btnUpdate, 1, 3);
        form.add(btnDelete, 2, 3);

        root.getChildren().addAll(form, table);

        loadUsuarios();

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("CRUD com JavaFX e SQLite");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadUsuarios() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(usuarioDAO.getAllUsuarios());
        table.setItems(usuarios);
    }

    private void addUsuario() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();
        usuarioDAO.addUsuario(new Usuario(0, nome, email, telefone));
        loadUsuarios();
    }

    private void updateUsuario() {
        Usuario selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNome(txtNome.getText());
            selected.setEmail(txtEmail.getText());
            selected.setTelefone(txtTelefone.getText());
            usuarioDAO.updateUsuario(selected);
            loadUsuarios();
        }
    }

    private void deleteUsuario() {
        Usuario selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            usuarioDAO.deleteUsuario(selected.getId());
            loadUsuarios();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
