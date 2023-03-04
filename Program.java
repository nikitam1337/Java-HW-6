import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Program {
    public static void main(String[] args) {
        Map myMap = new Map();
        myMap.generateStartMap();
        System.out.println(new MapPrinter().mapToString(myMap.getMap()));

        var waveAlgorithm = new WaveAlgorithm(myMap.getMap());
        var startPoint = new Point2D(3, 4);
        waveAlgorithm.fillMap(startPoint);

        System.out.println(new MapPrinter().mapToString(myMap.getMap()));

        var finishPoint = new Point2D(8, 4);
        var road = waveAlgorithm.getRoad(finishPoint, startPoint, myMap.getMap());
        
        for (Point2D coordinate : road) {
            System.out.println(coordinate);
        }
    }
}

class Point2D {
    int x, y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("x: %d  y: %d", x, y);
    }
}

class Map {
    int[][] map;

    public void generateStartMap() {
        int[][] map = {
                { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                { -1, 00, 00, 00, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, 00, 00, 00, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, -1, -1, -1, -1, -1, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, -1, 00, 00, 00, -1, 00, -1 },
                { -1, 00, 00, 00, 00, 00, 00, 00, -1 },
                { -1, 00, 00, 00, 00, 00, 00, 00, -1 },
                { -1, -1, -1, -1, -1, -1, -1, -1, -1 }
        };

        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    public void StartPos(Point2D pos) {
        map[pos.x][pos.y] = -2;
    }

    public void ExitPos(Point2D pos) {
        map[pos.x][pos.y] = -3;
    }

}

class MapPrinter {
    public String mapToString(int[][] map) {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                sb.append(String.format("%3d", map[row][col]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

class WaveAlgorithm {
    int[][] map;

    public WaveAlgorithm(int[][] map) {
        this.map = map;
    }

    public void fillMap(Point2D startPoint) {
        Queue<Point2D> queue = new LinkedList<Point2D>();
        queue.add(startPoint);
        map[startPoint.x][startPoint.y] = 1;

        while (queue.size() != 0) {
            Point2D p = queue.remove();

            if (map[p.x - 1][p.y] == 0) {
                queue.add(new Point2D(p.x - 1, p.y));
                map[p.x - 1][p.y] = map[p.x][p.y] + 1;
            }
            if (map[p.x][p.y - 1] == 0) {
                queue.add(new Point2D(p.x, p.y - 1));
                map[p.x][p.y - 1] = map[p.x][p.y] + 1;
            }
            if (map[p.x + 1][p.y] == 0) {
                queue.add(new Point2D(p.x + 1, p.y));
                map[p.x + 1][p.y] = map[p.x][p.y] + 1;
            }
            if (map[p.x][p.y + 1] == 0) {
                queue.add(new Point2D(p.x, p.y + 1));
                map[p.x][p.y + 1] = map[p.x][p.y] + 1;
            }
        }
    }

    public ArrayList<Point2D> getRoad(Point2D exit, Point2D start, int[][] map) {
        ArrayList<Point2D> road = new ArrayList<>();
        if (map[exit.x][exit.y] != 0 && map[exit.x][exit.y]!=-1) {
            Point2D current_position = exit;
            road.add(new Point2D(current_position.x, current_position.y));

            System.out.println("There is definitely a path");

            while (map[current_position.x][current_position.y] != map[start.x][start.y]) {

                if (map[current_position.x - 1][current_position.y] == map[current_position.x][current_position.y] - 1) {
                    current_position.x = current_position.x - 1;
                    road.add(new Point2D(current_position.x, current_position.y));
                }

                else if (map[current_position.x][current_position.y - 1] == map[current_position.x][current_position.y] - 1) {
                    current_position.y = current_position.y - 1;
                    road.add(new Point2D(current_position.x, current_position.y));
                }

                else if (map[current_position.x + 1][current_position.y] == map[current_position.x][current_position.y] - 1) {
                    current_position.x = current_position.x + 1;
                    road.add(new Point2D(current_position.x, current_position.y));
                }

                else if (map[current_position.x][current_position.y + 1] == map[current_position.x][current_position.y] - 1) {
                    current_position.y = current_position.y + 1;
                    road.add(new Point2D(current_position.x, current_position.y));
                }
            }

        } else {
            System.out.println("Path not found");
        }
        return road;
    }
}