package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.PipelineEditor;


@Route
public class MainView extends VerticalLayout {
    private PipelineRepository repository;
    private Grid<PipelineEntity> grid;
    private PipelineEditor editor;
    private final Button addPipelineButton;

//    MainView(){
//        add(new Button("click me" , e -> Notification.show("Hello world!")));
//    }

//    MainView(PipelineRepository repository){
//        this.repository = repository;
//        this.grid = new Grid<>(PipelineEntity.class);
//        Button button = new Button("Edit", event -> UI.getCurrent().navigate("pipeline") );
//        grid.setItems(repository.findAll());
//        grid.removeColumnByKey("stages");
//        add(grid);
//        add(button);
//    }

    MainView(PipelineRepository repository, PipelineEditor editor){
        this.repository = repository;
        this.editor = editor;
        this.addPipelineButton = new Button("New Pipeline", VaadinIcon.PLUS.create());
        this.grid = new Grid<>(PipelineEntity.class);

        listPipelines();

        HorizontalLayout actions = new HorizontalLayout(addPipelineButton);
        add(actions, grid, editor);

        grid.removeColumnByKey("stages");
        grid.setHeight("300px");
        grid.setColumns("id", "name");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listPipelines();
        });

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editPipeline(e.getValue());
        });

        addPipelineButton.addClickListener(e -> editor.editPipeline(new PipelineEntity()));

    }

    void listPipelines(){
        grid.setItems(repository.findAll());
    }
}
