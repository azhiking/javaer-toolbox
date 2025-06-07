package com.codebase;

import com.codebase.panel.JsonFormatPane;
import com.codebase.panel.StorageUnitConvertPane;
import com.codebase.panel.TimeConvertPane;
import com.codebase.panel.UuidPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * @author taozhi
 * @date 2025/6/7 22:52
 * @since 1.0.0
 */
public class MainPane extends Application {

    private final TimeConvertPane timeConvertPane = new TimeConvertPane();

    private final UuidPane uuidPane = new UuidPane();

    private final StorageUnitConvertPane storageUnitConvertPane = new StorageUnitConvertPane();

    private final JsonFormatPane jsonFormatPane = new JsonFormatPane();

    @Override
    public void start(Stage primaryStage) {
        // 创建菜单栏
        MenuBar menuBar = new MenuBar();

        // 创建菜单
        Menu toolsMenu = new Menu("编程工具");

        MenuItem timeConvertItem = new MenuItem("时间转换");
        MenuItem uuidItem = new MenuItem("UUID生成");
        MenuItem cryptoItem = new MenuItem("加密解密");
        MenuItem storageUnitItem = new MenuItem("存储单位转换");
        MenuItem jsonFormatItem = new MenuItem("JSON压缩/格式化");

        toolsMenu.getItems().addAll(timeConvertItem, uuidItem, cryptoItem, storageUnitItem, jsonFormatItem);
        menuBar.getMenus().addAll(toolsMenu);

        // 主布局
        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        // 点击“时间转换”显示时间转换界面
        timeConvertItem.setOnAction(e -> root.setCenter(timeConvertPane));
        // 点击“UUID生成”显示UUID生成界面
        uuidItem.setOnAction(e -> root.setCenter(uuidPane));
        // 点击“存储单位转换”显示存储单位转换界面
        storageUnitItem.setOnAction(e -> root.setCenter(storageUnitConvertPane));
        // 点击“JSON压缩/格式化”显示JSON处理界面
        jsonFormatItem.setOnAction(e -> root.setCenter(jsonFormatPane));

        // 场景设置
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("JavaFX Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
