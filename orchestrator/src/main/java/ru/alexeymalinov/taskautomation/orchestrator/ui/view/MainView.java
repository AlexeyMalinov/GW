package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.UI;
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

    MainView(PipelineRepository repository, MainEditor editor){
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(PipelineEntity.class);
    }

    @PostConstruct
    private void printMainView(){
        listPipelines();

        Button addPipelineButton = new Button("New Pipeline", VaadinIcon.PLUS.create());
        Button robotGroupViewButton = new Button("Groups of robots", VaadinIcon.GROUP.create());

        HorizontalLayout actions = new HorizontalLayout(addPipelineButton, robotGroupViewButton);
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
        robotGroupViewButton.addClickListener(e -> robotGroupViewButton.getUI().ifPresent(ui -> ui.navigate(RobotsGroupView.class)));

    }

    void listPipelines(){
        grid.setItems(repository.findAll());
    }
}
