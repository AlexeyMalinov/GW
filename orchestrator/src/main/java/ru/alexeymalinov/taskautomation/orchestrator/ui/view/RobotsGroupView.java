package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotsGroupRepository;

@Route
public class RobotsGroupView extends VerticalLayout {

    private final RobotsGroupRepository repository;


    public RobotsGroupView(RobotsGroupRepository repository) {
        this.repository = repository;
    }
}
