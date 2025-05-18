package com.example.hastakayit;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HeapTreePane extends Pane {
    private MaxHeap heap;
    private static final double CIRCLE_RADIUS = 30;
    private static final double H_GAP = 220;
    private static final double V_GAP = 60;
    private static final double START_X = 500;
    private static final double START_Y = 50;

    public HeapTreePane(MaxHeap heap) {
        this.heap = heap;
        setStyle("-fx-background-color: white;");
    }

    public void drawHeap() {
        getChildren().clear();
        drawHeapNode(0, START_X, START_Y, H_GAP);
    }

    private void drawHeapNode(int index, double x, double y, double hSpacing) {
        if (index >= heap.getSize()) return;

        Hasta hasta = heap.getElementAt(index);
        if (hasta == null) return;


        Circle circle = new Circle(x, y, CIRCLE_RADIUS, Color.LIGHTBLUE);
        circle.setStroke(Color.BLUE);


        Text text = new Text(hasta.getHastaAdi() + "\n" + hasta.getOncelikPuani());
        text.setTextAlignment(TextAlignment.CENTER);
        text.setX(x - 25);
        text.setY(y);

        getChildren().addAll(circle, text);


        int leftIndex = 2 * index + 1;
        if (leftIndex < heap.getSize() && heap.getElementAt(leftIndex) != null) {
            double childX = x - hSpacing;
            double childY = y + V_GAP;
            getChildren().add(new Line(x, y, childX, childY));
            drawHeapNode(leftIndex, childX, childY, hSpacing / 2);
        }


        int rightIndex = 2 * index + 2;
        if (rightIndex < heap.getSize() && heap.getElementAt(rightIndex) != null) {
            double childX = x + hSpacing;
            double childY = y + V_GAP;
            getChildren().add(new Line(x, y, childX, childY));
            drawHeapNode(rightIndex, childX, childY, hSpacing / 2);
        }
    }
}