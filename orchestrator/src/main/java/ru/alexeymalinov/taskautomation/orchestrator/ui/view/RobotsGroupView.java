package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotsGroupEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotsGroupRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.ChangeHandler;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.RobotsGroupEditor;

import javax.annotation.PostConstruct;

@Route
public class RobotsGroupView extends VerticalLayout {

    private final RobotsGroupRepository repository;
    private RobotsGroupEditor editor;
    private Grid<RobotsGroupEntity> grid;
    private ChangeHandler changeHandler;


    public RobotsGroupView(RobotsGroupRepository repository) {

        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(RobotsGroupEntity.class);

    }

    @PostConstruct
    private void printMainView(){
        listRobotsGroups();

        Button addPipelineButton = new Button("New robots group", VaadinIcon.PLUS.create());

        Button backPipelineButton = new Button("Pipelines", VaadinIcon.ARROW_BACKWARD.create());
        backPipelineButton.addClickListener(e -> backPipelineButton.getUI().ifPresent((ui -> ui.navigate(MainView.class))));

        HorizontalLayout actions = new HorizontalLayout(addPipelineButton, backPipelineButton);
        add(actions, grid, editor);

        grid.removeColumnByKey("robotId");
        grid.setHeight("300px");
        grid.setColumns("id");
        grid.setColumns("name");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listRobotsGroups();
        });

    }

    private void listRobotsGroups(){
        grid.setItems(repository.findAll());
    }

    public void setChangeHandler(ChangeHandler handler) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = handler;
    }

}
