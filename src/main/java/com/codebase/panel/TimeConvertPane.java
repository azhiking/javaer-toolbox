package com.codebase.panel;

import cn.hutool.core.date.DateUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author taozhi
 * @date 2025/6/7 23:02
 * @since 1.0.0
 */
public class TimeConvertPane extends VBox {
    public TimeConvertPane() {
        setPadding(new Insets(20));
        setSpacing(10);

        // 时间戳转时间
        Label timestampLabel = new Label("时间戳：");
        TextField timestampInput = new TextField();
        timestampInput.setPrefWidth(200);
        Button timestampConvertBtn = new Button("转换");
        Button timestampNowBtn = new Button("当前时间");
        HBox timestampInputBox = new HBox(10, timestampInput, timestampConvertBtn,timestampNowBtn);

        Label dateTimeResultLabel = new Label("时间：");
        TextField dateTimeResult = new TextField();
        dateTimeResult.setEditable(false);
        dateTimeResult.setPromptText("yyyy-MM-dd HH:mm:ss");
        dateTimeResult.setPrefWidth(200);
        Button dateTimeCopyBtn = new Button("复制");
        dateTimeCopyBtn.setDisable(true);
        HBox dateTimeResultBox = new HBox(10, dateTimeResult, dateTimeCopyBtn);

        VBox timestampGroupBox = new VBox(10, timestampLabel, timestampInputBox, dateTimeResultLabel, dateTimeResultBox);
        timestampGroupBox.setPadding(new Insets(10));
        TitledPane timestampTitledPane = new TitledPane("时间戳转时间", timestampGroupBox);
        timestampTitledPane.setCollapsible(false);

        timestampConvertBtn.setOnAction(e -> {
            try {
                long timestamp = Long.parseLong(timestampInput.getText());
                String formatted = DateUtil.format(
                        DateUtil.date(timestamp),
                        "yyyy-MM-dd HH:mm:ss"
                );
                dateTimeResult.setText(formatted);
                dateTimeCopyBtn.setDisable(false);
            } catch (Exception ex) {
                dateTimeResult.setText("输入有误，请输入有效的时间戳");
                dateTimeCopyBtn.setDisable(true);
            }
        });

        timestampNowBtn.setOnAction(e -> {
            long timestamp = System.currentTimeMillis();
            timestampInput.setText(String.valueOf(timestamp));
            String formatted = DateUtil.format(
                    DateUtil.date(timestamp),
                    "yyyy-MM-dd HH:mm:ss"
            );
            dateTimeResult.setText(formatted);
            dateTimeCopyBtn.setDisable(false);
        });

        dateTimeCopyBtn.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(dateTimeResult.getText());
            clipboard.setContent(content);
        });

        // 时间转时间戳
        Label dateTimeLabel = new Label("时间：");
        TextField dateTimeInput = new TextField();
        dateTimeInput.setPrefWidth(200);
        dateTimeInput.setPromptText("yyyy-MM-dd HH:mm:ss");
        Button dateTimeConvertBtn = new Button("转换");
        Button nowBtn = new Button("当前时间");
        HBox dateTimeInputBox = new HBox(10, dateTimeInput, dateTimeConvertBtn,nowBtn);

        Label timestampResultLabel = new Label("时间戳：");
        TextField timestampResult = new TextField();
        timestampResult.setEditable(false);
        timestampResult.setPromptText("时间戳");
        timestampResult.setPrefWidth(200);
        Button timestampCopyBtn = new Button("复制");
        timestampCopyBtn.setDisable(true);
        HBox timestampResultBox = new HBox(10, timestampResult, timestampCopyBtn);

        VBox dateTimeGroupBox = new VBox(10, dateTimeLabel, dateTimeInputBox, timestampResultLabel, timestampResultBox);
        dateTimeGroupBox.setPadding(new Insets(10));
        TitledPane dateTimeTitledPane = new TitledPane("时间转时间戳", dateTimeGroupBox);
        dateTimeTitledPane.setCollapsible(false);

        dateTimeConvertBtn.setOnAction(e -> {
            try {
                long timestamp = DateUtil.parse(dateTimeInput.getText(), "yyyy-MM-dd HH:mm:ss").getTime();
                timestampResult.setText(String.valueOf(timestamp));
                timestampCopyBtn.setDisable(false);
            } catch (Exception ex) {
                timestampResult.setText("输入有误，请输入有效的时间");
                timestampCopyBtn.setDisable(true);
            }
        });

        nowBtn.setOnAction(e -> {

            dateTimeInput.setText(DateUtil.now());
            long timestamp = DateUtil.parse(dateTimeInput.getText(), "yyyy-MM-dd HH:mm:ss").getTime();
            timestampResult.setText(String.valueOf(timestamp));
            timestampCopyBtn.setDisable(false);

        });

        timestampCopyBtn.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(timestampResult.getText());
            clipboard.setContent(content);
        });

        getChildren().addAll(timestampTitledPane, dateTimeTitledPane);
    }
}
