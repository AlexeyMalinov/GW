package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.MainEditor;

import javax.annotation.PostConstruct;


@Route(value = "")
public class MainView extends AbstractView<PipelineRepository, PipelineEntity, MainEditor> {

    MainView(PipelineRepository repository, MainEditor editor){
        super(repository,editor,new Grid<>(PipelineEntity.class));
    }

    @Override
    @PostConstruct
    protected void print() {
        listItem();

        Button addPipelineButton = new Button("New Pipeline", VaadinIcon.PLUS.create());
        addPipelineButton.addClickListener(e -> editor.edit(new PipelineEntity()));

        Button robotGroupViewButton = new Button("Groups of robots", VaadinIcon.GROUP.create());
        robotGroupViewButton.addClickListener(e -> robotGroupViewButton.getUI().ifPresent(ui -> ui.navigate(RobotsGroupView.class)));

        HorizontalLayout actions = new HorizontalLayout(addPipelineButton, robotGroupViewButton);
        add(actions, grid, this.editor);

    }

    @Override
    protected void listItem() {
        grid.setItems(repository.findAll());
    }
}
