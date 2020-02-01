package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.MainEditor;

import javax.annotation.PostConstruct;


@Route(value = "")
public class MainView extends VerticalLayout {
    private PipelineRepository repository;
    private Grid<PipelineEntity> grid;
    private MainEditor editor;
    private final Button addPipelineButton;

    MainView(PipelineRepository repository, MainEditor editor){
        this.repository = repository;
        this.editor = editor;
        this.addPipelineButton = new Button("New Pipeline", VaadinIcon.PLUS.create());
        this.grid = new Grid<>(PipelineEntity.class);
    }

    @PostConstruct
    private void printMainView(){
        listPipelines();

        HorizontalLayout actions = new HorizontalLayout(addPipelineButton);
        add(actions, grid, this.editor);

        grid.removeColumnByKey("stages");
        grid.setHeight("300px");
        grid.setColumns("id", "name");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listPipelines();
        });

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.edit(e.getValue());
        });

        addPipelineButton.addClickListener(e -> editor.edit(new PipelineEntity()));

    }

    void listPipelines(){
        grid.setItems(repository.findAll());
    }
}
