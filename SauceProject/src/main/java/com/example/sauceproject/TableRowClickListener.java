package com.example.sauceproject;

import javafx.scene.control.TableRow;

public class TableRowClickListener extends TableRow<Dato> {
    public TableRowClickListener() {
        this.setOnMouseClicked(event -> {
            if (!isEmpty() && event.getClickCount() == 1) {
                Dato rowData = getItem();
                System.out.println("Haz hecho clic en la fila: " + rowData.getSymbol());
            }
        });
    }
}
