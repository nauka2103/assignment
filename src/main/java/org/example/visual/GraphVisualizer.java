package org.example.visual;

import org.example.model.Edge;
import org.example.model.Graph;
import org.example.model.ResultDTO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphVisualizer extends JPanel {
    private final Graph graph;
    private final Set<String> mstEdges;
    private final Color mstColor;
    private final Point[] positions;
    private final int width = 800;
    private final int height = 600;

    public GraphVisualizer(Graph graph, ResultDTO.Algo algo, Color mstColor) {
        this.graph = graph;
        this.mstEdges = new HashSet<>();
        this.mstColor = mstColor;
        this.positions = new Point[graph.V];
        generatePositions();

        if (algo != null && algo.mstEdges != null) {
            for (var e : algo.mstEdges) {
                mstEdges.add(e.u + "-" + e.v);
                mstEdges.add(e.v + "-" + e.u);
            }
        }

        setPreferredSize(new Dimension(width, height));
    }

    private void generatePositions() {
        Random rand = new Random();
        for (int i = 0; i < graph.V; i++) {
            positions[i] = new Point(
                    100 + rand.nextInt(width - 200),
                    100 + rand.nextInt(height - 200)
            );
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Edges
        for (Edge e : graph.edges) {
            Point p1 = positions[e.u];
            Point p2 = positions[e.v];
            boolean inMST = mstEdges.contains(e.u + "-" + e.v);

            g2.setStroke(new BasicStroke(inMST ? 3f : 1f));
            g2.setColor(inMST ? mstColor : Color.LIGHT_GRAY);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);

            int mx = (p1.x + p2.x) / 2;
            int my = (p1.y + p2.y) / 2;
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(e.w), mx, my);
        }

        // Vertices
        for (int i = 0; i < graph.V; i++) {
            Point p = positions[i];
            g2.setColor(Color.ORANGE);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);
            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - 15, p.y - 15, 30, 30);
            g2.drawString(String.valueOf(i), p.x - 4, p.y + 5);
        }
    }



    public static void saveGraphImage(Graph g, ResultDTO.Algo algo, Color color, String fileName) throws Exception {
        GraphVisualizer panel = new GraphVisualizer(g, algo, color);
        panel.setSize(800, 600);
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        panel.paint(g2);
        g2.dispose();
        boolean png = ImageIO.write(image, "png", new File(fileName));
    }
}
