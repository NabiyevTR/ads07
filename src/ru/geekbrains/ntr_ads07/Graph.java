package ru.geekbrains.ntr_ads07;

import java.util.*;

public class Graph implements IGraph {

    private final List<Vertex> vertexList;
    private final boolean[][] adjMat;

    public Graph(int maxVertexCount) {
        this.vertexList = new ArrayList<>(maxVertexCount);
        this.adjMat = new boolean[maxVertexCount][maxVertexCount];
    }

    @Override
    public void addVertex(String label) {
        vertexList.add(new Vertex(label));
    }

    @Override
    public void addEdge(String startLabel, String endLabel) {
        int startIndex = indexOf(startLabel);
        int endIndex = indexOf(endLabel);

        if (startIndex == -1 || endIndex == -1) {
            throw new IllegalArgumentException("Invalid label for vertex");
        }

        adjMat[startIndex][endIndex] = true;
        adjMat[endIndex][startIndex] = true;
    }

    @Override
    public void addEdges(String startLabel, String secondLabel, String... others) {
        addEdge(startLabel, secondLabel);
        for (String other : others) {
            addEdge(startLabel, other);
        }
    }

    private int indexOf(String startLabel) {
        for (int index = 0; index < getSize(); index++) {
            if (vertexList.get(index).getLabel().equals(startLabel)) {
                return index;
            }
        }

        return -1;
    }

    @Override
    public int getSize() {
        return vertexList.size();
    }

    @Override
    public void display() {
        for (int i = 0; i < getSize(); i++) {
            System.out.print(vertexList.get(i));
            for (int j = 0; j < getSize(); j++) {
                if (adjMat[i][j]) {
                    System.out.print(" -> " + vertexList.get(j));
                }
            }
            System.out.println();
        }
    }

    @Override
    public void dfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Invalid start label");
        }

        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertexAndDisplay(stack, vertex);
        while (!stack.isEmpty()) {
            vertex = getNearUnvisitedVertex(stack.peek());
            if (vertex != null) {
                visitVertexAndDisplay(stack, vertex);
            } else {
                stack.pop();
            }
        }

        resetVertexState();
    }

    @Override
    public void bfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Invalid start label");
        }

        Queue<Vertex> queue = new LinkedList<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertexAndDisplay(queue, vertex);
        while (!queue.isEmpty()) {
            vertex = getNearUnvisitedVertex(queue.peek());
            if (vertex != null) {
                visitVertexAndDisplay(queue, vertex);
            } else {
                queue.remove();
            }
        }

        resetVertexState();
    }

    @Override
    public void shortestRoute(String startLabel, String endLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Invalid start label.");
        }

        int endIndex = indexOf(endLabel);
        if (endIndex == -1) {
            throw new IllegalArgumentException("Invalid end label.");
        }

        if (startIndex == endIndex) {
            System.out.println("Start label is equals to end label.");
            return;
        }

        Queue<Vertex> queue = new LinkedList<>();
        Vertex vertex = vertexList.get(startIndex);

        visitVertex(queue, vertex);
        while (!queue.isEmpty()) {
            vertex = getNearUnvisitedVertex(queue.peek());
            if (vertex != null) {
                visitVertex(queue, vertex);
                if (vertex.getLabel().equals(endLabel)) {
                    break;
                }
            } else {
                queue.remove();
            }
        }
        if (queue.isEmpty()) {
            System.out.printf("The path between %s and %s was not found.\n",
                    vertexList.get(startIndex), vertexList.get(endIndex));
        } else {
            displayPath(getShortestPath(vertexList.get(endIndex)));
        }
        resetVertexState();
    }

    private List<Vertex> getShortestPath(Vertex endVertex) {
        Deque<Vertex> path = new LinkedList<>();
        Vertex vertex = endVertex;
        path.addFirst(vertex);

        while (vertex.getPreviousVertex() != null) {
            vertex = vertex.getPreviousVertex();
            path.addFirst(vertex);
        }
        return new ArrayList<>(path);
    }

    private void displayPath(List<Vertex> vertexList) {
        vertexList.forEach(System.out::println);
    }

    private void resetVertexState() {
        for (Vertex vertex : vertexList) {
            vertex.setVisited(false);
            vertex.setPreviousVertex(null);
        }
    }

    private Vertex getNearUnvisitedVertex(Vertex current) {
        int currentIndex = vertexList.indexOf(current);
        for (int i = 0; i < getSize(); i++) {
            if (adjMat[currentIndex][i] && !vertexList.get(i).isVisited()) {
                return vertexList.get(i);
            }
        }
        return null;
    }

    private void visitVertexAndDisplay(Stack<Vertex> stack, Vertex vertex) {
        System.out.println(vertex);
        vertex.setVisited(true);
        stack.push(vertex);
    }

    private void visitVertexAndDisplay(Queue<Vertex> queue, Vertex vertex) {
        System.out.println(vertex);
        vertex.setVisited(true);
        queue.add(vertex);
    }

    private void visitVertex(Stack<Vertex> stack, Vertex vertex) {
        vertex.setVisited(true);
        stack.push(vertex);
    }

    private void visitVertex(Queue<Vertex> queue, Vertex vertex) {
        vertex.setPreviousVertex(queue.peek());
        vertex.setVisited(true);
        queue.add(vertex);
    }
}
