package org.home.dev.db.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text", nullable = true)
    private String text;

    @Column(name = "done", nullable = false)
    private boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_group_id", nullable = false)
    private TodoGroup todoGroup;

    public Todo() {
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public TodoGroup getTodoGroup() {
        return todoGroup;
    }

    public void setTodoGroup(TodoGroup todoGroup) {
        this.todoGroup = todoGroup;
    }
}
