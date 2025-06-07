package com.codebase.panel;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author taozhi
 * @date 2025/6/7 23:50
 * @since 1.0.0
 */
public class UuidPane extends VBox {
    public UuidPane() {
        setPadding(new Insets(20));
        setSpacing(15);

        CheckBox removeDashBox = new CheckBox("去除横杠");
        removeDashBox.setSelected(true); // 默认勾选

        Button generateBtn = new Button("生成UUID");

        VBox topBox = new VBox(10, generateBtn, removeDashBox); // 垂直排列

        TextArea uuidArea = new TextArea();
        uuidArea.setEditable(false);
        uuidArea.setPromptText("点击生成按钮，一次生成10条UUID，每行一条");

        // 生成并显示UUID的方法
        Runnable updateUuids = () -> {
            boolean removeDash = removeDashBox.isSelected();
            String uuids = IntStream.range(0, 10)
                    .mapToObj(i -> {
                        String uuid = UUID.randomUUID().toString();
                        return removeDash ? uuid.replace("-", "") : uuid;
                    })
                    .collect(Collectors.joining("\n"));
            uuidArea.setText(uuids);
        };

        generateBtn.setOnAction(e -> updateUuids.run());

        removeDashBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            // 仅在有内容时才更新
            if (!uuidArea.getText().isEmpty()) {
                String[] lines = uuidArea.getText().split("\n");
                String updated = IntStream.range(0, lines.length)
                        .mapToObj(i -> newVal ? lines[i].replace("-", "") : formatWithDash(lines[i]))
                        .collect(Collectors.joining("\n"));
                uuidArea.setText(updated);
            }
        });

        getChildren().addAll(topBox, uuidArea);
    }

    // 辅助方法：将无横杠的UUID恢复为带横杠格式（仅简单处理v4标准UUID）
    private String formatWithDash(String uuid) {
        if (uuid.length() == 32) {
            return uuid.substring(0, 8) + "-" +
                    uuid.substring(8, 12) + "-" +
                    uuid.substring(12, 16) + "-" +
                    uuid.substring(16, 20) + "-" +
                    uuid.substring(20);
        }
        return uuid;
    }
}
