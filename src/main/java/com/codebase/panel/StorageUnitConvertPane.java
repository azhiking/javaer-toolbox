package com.codebase.panel;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

/**
 * @author taozhi
 * @date 2025/6/8 0:11
 * @since 1.0.0
 */
public class StorageUnitConvertPane extends VBox {
    private boolean updating = false;

    public StorageUnitConvertPane() {
        setPadding(new Insets(20));
        setSpacing(15);

        String[] units = {"比特(bit)", "字节(B)", "KB", "MB", "GB", "PB"};
        double[] factors = {1, 8, 8 * 1024, 8 * 1024 * 1024, 8 * 1024 * 1024 * 1024L, 8d * 1024 * 1024 * 1024 * 1024 * 1024};

        TextField[] fields = new TextField[units.length];

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        for (int i = 0; i < units.length; i++) {
            Label label = new Label(units[i]);
            TextField field = new TextField();
            field.setPrefWidth(200);
            fields[i] = field;
            grid.add(label, 0, i);
            grid.add(field, 1, i);
        }

        ChangeListener<String>[] listeners = new ChangeListener[units.length];

        for (int i = 0; i < units.length; i++) {
            final int idx = i;
            listeners[i] = (obs, oldVal, newVal) -> {
                if (updating) return;
                updating = true;
                try {
                    if (newVal == null || newVal.isEmpty()) {
                        for (int j = 0; j < fields.length; j++) {
                            if (j != idx) fields[j].setText("");
                        }
                        updating = false;
                        return;
                    }
                    BigDecimal value = new BigDecimal(newVal);
                    // 先统一转为 bit
                    BigDecimal bits = value;
                    if (idx == 0) {
                        // 比特
                    } else if (idx == 1) {
                        bits = value.multiply(BigDecimal.valueOf(8));
                    } else {
                        bits = value.multiply(BigDecimal.valueOf(factors[idx]));
                    }
                    // 更新其它输入框
                    for (int j = 0; j < fields.length; j++) {
                        if (j == idx) continue;
                        BigDecimal v;
                        if (j == 0) {
                            v = bits;
                        } else if (j == 1) {
                            v = bits.divide(BigDecimal.valueOf(8), 10, BigDecimal.ROUND_HALF_UP);
                        } else {
                            v = bits.divide(BigDecimal.valueOf(factors[j]), 10, BigDecimal.ROUND_HALF_UP);
                        }
                        fields[j].setText(v.stripTrailingZeros().toPlainString());
                    }
                } catch (Exception e) {
                    // 非法输入时清空其它框
                    for (int j = 0; j < fields.length; j++) {
                        if (j != idx) fields[j].setText("");
                    }
                }
                updating = false;
            };
            fields[i].textProperty().addListener(listeners[i]);
        }

        getChildren().addAll(grid);
    }

}
