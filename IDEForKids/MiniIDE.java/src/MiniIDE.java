import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MiniIDE extends JFrame {
    private ArrayList<Shape> shapes = new ArrayList<>();
    private JPanel canvas;
    private JLabel exerciseLabel; // Etiqueta para mostrar el ejercicio
    private boolean showAreas = false;
    private boolean showPerimeters = false;

    public MiniIDE() {
        setTitle("IDE para Niños");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Etiqueta para mostrar ejercicios
        exerciseLabel = new JLabel("Ejercicio: ", SwingConstants.CENTER);
        exerciseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        exerciseLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(exerciseLabel, BorderLayout.NORTH);

        // Canvas para dibujar
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Shape shape : shapes) {
                    shape.draw(g);
                    if (showAreas) {
                        g.setColor(Color.BLACK);
                        g.drawString("Área: " + shape.getArea(), shape.x + 10, shape.y - 10);
                    }
                    if (showPerimeters) {
                        g.setColor(Color.BLUE);
                        g.drawString("Perímetro: " + shape.getPerimeter(), shape.x + 10, shape.y + 10);
                    }
                }
            }
        };
        canvas.setBackground(Color.WHITE);
        add(canvas, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Primera fila: botones de movimiento y agregar figura
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        JButton moveUpButton = new JButton("↑");
        moveUpButton.addActionListener(e -> moveShape(0, -10));
        buttonPanel.add(moveUpButton, gbc);

        gbc.gridx = 1;
        JButton moveDownButton = new JButton("↓");
        moveDownButton.addActionListener(e -> moveShape(0, 10));
        buttonPanel.add(moveDownButton, gbc);

        gbc.gridx = 2;
        JButton moveLeftButton = new JButton("←");
        moveLeftButton.addActionListener(e -> moveShape(-10, 0));
        buttonPanel.add(moveLeftButton, gbc);

        gbc.gridx = 3;
        JButton moveRightButton = new JButton("→");
        moveRightButton.addActionListener(e -> moveShape(10, 0));
        buttonPanel.add(moveRightButton, gbc);

        gbc.gridx = 4;
        JButton addShapeButton = new JButton("Agregar figura");
        addShapeButton.addActionListener(e -> showAddShapeMenu());
        buttonPanel.add(addShapeButton, gbc);

        // Segunda fila: botones de otras acciones
        gbc.gridx = 0;
        gbc.gridy = 1;

        JButton changeColorButton = new JButton("Cambiar color");
        changeColorButton.addActionListener(e -> openColorPicker());
        buttonPanel.add(changeColorButton, gbc);

        gbc.gridx = 1;
        JButton increaseSizeButton = new JButton("Aumentar tamaño");
        increaseSizeButton.addActionListener(e -> resizeShape(10));
        buttonPanel.add(increaseSizeButton, gbc);

        gbc.gridx = 2;
        JButton decreaseSizeButton = new JButton("Reducir tamaño");
        decreaseSizeButton.addActionListener(e -> resizeShape(-10));
        buttonPanel.add(decreaseSizeButton, gbc);

        gbc.gridx = 3;
        JButton toggleAreaButton = new JButton("Área");
        toggleAreaButton.addActionListener(e -> toggleAreaVisibility());
        buttonPanel.add(toggleAreaButton, gbc);

        gbc.gridx = 4;
        JButton togglePerimeterButton = new JButton("Perímetro");
        togglePerimeterButton.addActionListener(e -> togglePerimeterVisibility());
        buttonPanel.add(togglePerimeterButton, gbc);

        // Botón "Añadir ejercicio"
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        JButton addExerciseButton = new JButton("Añadir ejercicio");
        addExerciseButton.addActionListener(e -> addExercise());
        buttonPanel.add(addExerciseButton, gbc);

        // Botón "Seleccionar figura"
        gbc.gridx = 5;
        gbc.gridy = 1;

        JButton selectShapeButton = new JButton("Seleccionar figura");
        selectShapeButton.addActionListener(e -> selectShape());
        buttonPanel.add(selectShapeButton, gbc);

        add(buttonPanel, BorderLayout.SOUTH);

        // Configurar controles de teclado
        canvas.setFocusable(true);
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> moveShape(0, -10);
                    case KeyEvent.VK_DOWN -> moveShape(0, 10);
                    case KeyEvent.VK_LEFT -> moveShape(-10, 0);
                    case KeyEvent.VK_RIGHT -> moveShape(10, 0);
                }
            }
        });
    }

    private void addExercise() {
        String[] exercises = {
            "1. Haz una casa con un techo rojo.",
            "2. Haz un árbol tipo pino.",
            "3. Crea un sol amarillo con rayos.",
            "4. Dibuja un coche con ruedas negras.",
            "5. Haz una flor con pétalos de colores."
        };
        String exercise = exercises[(int) (Math.random() * exercises.length)];
        exerciseLabel.setText("Ejercicio: " + exercise);
    }

    private void showAddShapeMenu() {
        String[] options = {"Círculo", "Cuadrado", "Triángulo", "Trapecio", "Rombo"};
        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona una figura:",
                "Agregar Figura",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice != null) {
            switch (choice) {
                case "Círculo" -> shapes.add(new Circle(100, 100, 50, Color.RED));
                case "Cuadrado" -> shapes.add(new Square(100, 100, 50, Color.BLUE));
                case "Triángulo" -> shapes.add(new Triangle(100, 100, 50, Color.GREEN));
                case "Trapecio" -> shapes.add(new Trapezoid(100, 100, 50, 30, Color.ORANGE));
                case "Rombo" -> shapes.add(new Rhombus(100, 100, 50, Color.MAGENTA));
            }
            canvas.repaint();
        }
    }

    private void openColorPicker() {
        if (!shapes.isEmpty()) {
            Color newColor = JColorChooser.showDialog(this, "Selecciona un color", Color.BLACK);
            if (newColor != null) {
                Shape shape = shapes.get(shapes.size() - 1);
                shape.setColor(newColor);
                canvas.repaint();
            }
        }
    }

    private void moveShape(int dx, int dy) {
        if (!shapes.isEmpty()) {
            Shape shape = shapes.get(shapes.size() - 1);
            shape.move(dx, dy);
            canvas.repaint();
        }
    }

    private void resizeShape(int delta) {
        if (!shapes.isEmpty()) {
            Shape shape = shapes.get(shapes.size() - 1);
            shape.resize(delta);
            canvas.repaint();
        }
    }

    private void toggleAreaVisibility() {
        showAreas = !showAreas;
        canvas.repaint();
    }

    private void togglePerimeterVisibility() {
        showPerimeters = !showPerimeters;
        canvas.repaint();
    }

    // Método para seleccionar o eliminar una figura
    private void selectShape() {
        if (shapes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay figuras para seleccionar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] shapeOptions = new String[shapes.size() + 1];
        for (int i = 0; i < shapes.size(); i++) {
            shapeOptions[i] = "Figura " + (i + 1);
        }
        shapeOptions[shapes.size()] = "Eliminar figura"; // Opción para eliminar una figura

        String selectedOption = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona una figura para editar o eliminar:",
                "Seleccionar Figura",
                JOptionPane.PLAIN_MESSAGE,
                null,
                shapeOptions,
                shapeOptions[0]
        );

        if (selectedOption != null) {
            if (selectedOption.equals("Eliminar figura")) {
                deleteShape();
            } else {
                int index = Integer.parseInt(selectedOption.split(" ")[1]) - 1;
                Shape shape = shapes.get(index);
                shapes.remove(index);
                shapes.add(shape); // Mover la figura seleccionada al final de la lista
                canvas.repaint();
            }
        }
    }

    // Método para eliminar una figura
    private void deleteShape() {
        if (shapes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay figuras para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] shapeOptions = new String[shapes.size()];
        for (int i = 0; i < shapes.size(); i++) {
            shapeOptions[i] = "Figura " + (i + 1);
        }

        String selectedShape = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona una figura para eliminar:",
                "Eliminar Figura",
                JOptionPane.PLAIN_MESSAGE,
                null,
                shapeOptions,
                shapeOptions[0]
        );

        if (selectedShape != null) {
            int index = Integer.parseInt(selectedShape.split(" ")[1]) - 1;
            shapes.remove(index);
            canvas.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MiniIDE ide = new MiniIDE();
            ide.setVisible(true);
        });
    }
}

// Clase abstracta para las figuras
abstract class Shape {
    int x, y;
    Color color;

    public Shape(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void resize(int delta);

    public abstract void draw(Graphics g);

    public abstract double getArea();

    public abstract double getPerimeter();
}

// Clase para círculos
class Circle extends Shape {
    int radius;

    public Circle(int x, int y, int radius, Color color) {
        super(x, y, color);
        this.radius = radius;
    }

    @Override
    public void resize(int delta) {
        radius = Math.max(10, radius + delta);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}

// Clase para cuadrados
class Square extends Shape {
    int size;

    public Square(int x, int y, int size, Color color) {
        super(x, y, color);
        this.size = size;
    }

    @Override
    public void resize(int delta) {
        size = Math.max(10, size + delta);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

    @Override
    public double getArea() {
        return size * size;
    }

    @Override
    public double getPerimeter() {
        return 4 * size;
    }
}

// Clase para triángulos
class Triangle extends Shape {
    int size;

    public Triangle(int x, int y, int size, Color color) {
        super(x, y, color);
        this.size = size;
    }

    @Override
    public void resize(int delta) {
        size = Math.max(10, size + delta);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int[] xPoints = {x, x - size / 2, x + size / 2};
        int[] yPoints = {y, y + size, y + size};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public double getArea() {
        return (size * size) / 2.0;
    }

    @Override
    public double getPerimeter() {
        return 3 * size;
    }
}

// Clase para trapecios
class Trapezoid extends Shape {
    int topWidth, bottomWidth, height;

    public Trapezoid(int x, int y, int topWidth, int bottomWidth, Color color) {
        super(x, y, color);
        this.topWidth = topWidth;
        this.bottomWidth = bottomWidth;
        this.height = 50;
    }

    @Override
    public void resize(int delta) {
        topWidth = Math.max(10, topWidth + delta);
        bottomWidth = Math.max(10, bottomWidth + delta);
        height = Math.max(10, height + delta);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int[] xPoints = {x - topWidth / 2, x + topWidth / 2, x + bottomWidth / 2, x - bottomWidth / 2};
        int[] yPoints = {y, y, y + height, y + height};
        g.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    public double getArea() {
        return ((topWidth + bottomWidth) / 2.0) * height;
    }

    @Override
    public double getPerimeter() {
        return topWidth + bottomWidth + 2 * height;
    }
}

// Clase para rombos
class Rhombus extends Shape {
    int size;

    public Rhombus(int x, int y, int size, Color color) {
        super(x, y, color);
        this.size = size;
    }

    @Override
    public void resize(int delta) {
        size = Math.max(10, size + delta);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int[] xPoints = {x, x - size / 2, x, x + size / 2};
        int[] yPoints = {y - size / 2, y, y + size / 2, y};
        g.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    public double getArea() {
        return (size * size) / 2.0;
    }

    @Override
    public double getPerimeter() {
        return 4 * Math.sqrt((size / 2.0) * (size / 2.0) + (size / 2.0) * (size / 2.0));
    }
}