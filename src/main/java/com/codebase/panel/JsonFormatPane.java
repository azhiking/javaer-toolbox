package com.codebase.panel;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONWriter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.regex.Pattern;

/**
 * @author taozhi
 * @date 2025/6/8 0:22
 * @since 1.0.0
 */
public class JsonFormatPane extends VBox {

    private static final Pattern SINGLE_QUOTE_KEY = Pattern.compile("(?<=\\{|,|\\[)\\s*'([^']+)'\\s*:");
    private static final Pattern SINGLE_QUOTE_STRING = Pattern.compile(":\\s*'([^']*)'(?=,|\\}|\\])");

    public JsonFormatPane() {
        setPadding(new Insets(20));
        setSpacing(10);

        // 顶部按钮
        Button formatBtn = new Button("格式化");
        Button compressBtn = new Button("压缩");
        HBox btnBox = new HBox(10, formatBtn, compressBtn);

        // 输入区
        Label inputLabel = new Label("输入JSON：");
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("请输入或粘贴JSON文本");
        VBox inputBox = new VBox(5, inputLabel, inputArea);
        VBox.setVgrow(inputArea, Priority.ALWAYS);

        // 输出区
        Label outputLabel = new Label("输出结果：");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        VBox outputBox = new VBox(5, outputLabel, outputArea);
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        // 左右布局
        HBox ioBox = new HBox(10, inputBox, outputBox);
        HBox.setHgrow(inputBox, Priority.ALWAYS);
        HBox.setHgrow(outputBox, Priority.ALWAYS);

        // 按钮事件
        formatBtn.setOnAction(e -> {
            String text = inputArea.getText();
            Object obj = tryParseJson(text);
            if (obj != null) {
                String pretty = JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);
                outputArea.setText(pretty);
            } else {
                outputArea.setText("JSON格式错误: 解析失败");
            }
        });

        compressBtn.setOnAction(e -> {
            String text = inputArea.getText();
            Object obj = tryParseJson(text);
            if (obj != null) {
                String compact = JSON.toJSONString(obj);
                outputArea.setText(compact);
            } else {
                outputArea.setText("JSON格式错误: 解析失败");
            }
        });

        getChildren().addAll(btnBox, ioBox);
        VBox.setVgrow(ioBox, Priority.ALWAYS);
    }

    private Object tryParseJson(String text) {
        try {
            return JSON.parse(text);
        } catch (JSONException e) {
            // 尝试预处理：仅替换属性名和字符串的单引号
            String processed = SINGLE_QUOTE_KEY.matcher(text).replaceAll("\"$1\":");
            processed = SINGLE_QUOTE_STRING.matcher(processed).replaceAll(":\"$1\"");
            try {
                return JSON.parse(processed);
            } catch (JSONException ex) {
                return null;
            }
        }
    }
}
