package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.AbstractEditor;

public abstract class AbstractView<T1 extends JpaRepository, T2, T3 extends AbstractEditor> extends VerticalLayout{

    protected T1 repository;
    protected Grid<T2> grid;
    protected T3 editor;
    Button backMainButton = new Button("Home page", VaadinIcon.ARROW_BACKWARD.create());

    AbstractView(T1 repository, T3 editor, Grid<T2> grid){
        this.repository = repository;
        this.editor = editor;
        this.grid = grid;
        backMainButton.addClickListener(e -> {
            backMainButton.getUI().ifPresent(ui -> ui.navigate(MainView.class));
        });
        prepareGrid();
    }

    private void prepareGrid(){
        grid.setHeight("300px");
        grid.setColumns("id","name");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listItem();
        });

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.edit(e.getValue());
        });
    }

    protected abstract void print();

    protected abstract void listItem();
}
