package lesson8.task3

import java.util.*

class Graph {
    data class Vertex(val name: String) {
        val neighbors = mutableSetOf<Vertex>()
    }

    val vertices = mutableMapOf<String, Vertex>()

    private operator fun get(name: String) = vertices[name] ?: throw IllegalArgumentException()

    fun allVertexName() = vertices.toList().map { it.first }

    fun addVertex(name: String) {
        vertices[name] = Vertex(name)
    }

    private fun connect(first: Vertex, second: Vertex) {
        first.neighbors.add(second)
        second.neighbors.add(first)
    }

    fun connect(first: String, second: String) = connect(this[first], this[second])

    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в ширину
     */
    fun bfs(start: String, finish: String) = bfs(this[start], this[finish])

    private fun bfs(start: Vertex, finish: Vertex): Int {
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) return distance
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited.put(neighbor, distance + 1)
                queue.add(neighbor)
            }
        }
        return -1
    }

    fun bfsWithList(start: String, finish: String) = bfsWithList(this[start], this[finish])

    fun bfsWithList(start: Vertex, finish: Vertex): List<String> {
        val prev = mutableMapOf<Graph.Vertex, Graph.Vertex>()
        prev[start] = Vertex("-1")
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val cur = queue.first
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) break
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited.put(neighbor, distance + 1)
                queue.add(neighbor)
                prev[neighbor] = cur
            }
        }
        val path = mutableListOf<Graph.Vertex>()
        var cur = finish
        path.add(cur)
        while (prev[cur] != Vertex("-1")) {
            cur = prev[cur]!!
            path.add(cur)
        }
        return path.map { it.name }.reversed().toList()
    }

    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в глубину
     */
    fun dfs(start: String, finish: String): Int = dfs(this[start], this[finish], setOf()) ?: -1

    private fun dfs(start: Vertex, finish: Vertex, visited: Set<Vertex>): Int? =
            if (start == finish) 0
            else {
                val min = start.neighbors
                        .filter { it !in visited }
                        .mapNotNull { dfs(it, finish, visited + start) }
                        .min()
                if (min == null) null else min + 1
            }
}
