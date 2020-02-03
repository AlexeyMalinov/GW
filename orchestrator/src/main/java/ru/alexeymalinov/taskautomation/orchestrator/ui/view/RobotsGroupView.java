package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotsGroupEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotsGroupRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.RobotsGroupEditor;

import javax.annotation.PostConstruct;

@Route
public class RobotsGroupView extends AbstractView<RobotsGroupRepository, RobotsGroupEntity, RobotsGroupEditor> {

    public RobotsGroupView(RobotsGroupRepository repository, RobotsGroupEditor editor) {
        super(repository, editor, new Grid<>(RobotsGroupEntity.class));
    }

    @Override
    @PostConstruct
    public void print() {
        listItem();

        Button addRobotsGroupButton = new Button("New group of robots", VaadinIcon.PLUS.create());
        addRobotsGroupButton.addClickListener(e -> editor.edit(new RobotsGroupEntity()));

        HorizontalLayout actions = new HorizontalLayout(addRobotsGroupButton, backMainButton);

        add(actions, grid, editor);

    }

    @Override
    protected void listItem() {
        grid.setItems(repository.findAll());
    }
}
