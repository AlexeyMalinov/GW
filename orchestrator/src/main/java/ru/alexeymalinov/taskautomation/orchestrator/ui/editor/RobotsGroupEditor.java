package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotsGroupEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotsGroupRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.view.PipelineView;
import ru.alexeymalinov.taskautomation.orchestrator.ui.view.RobotView;

@SpringComponent
@UIScope
public class RobotsGroupEditor extends VerticalLayout implements KeyNotifier {

    private final RobotsGroupRepository repository;

    private RobotsGroupEntity entity;

    TextField name = new TextField("Name");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel", VaadinIcon.CLOSE.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button edit = new Button("Edit", VaadinIcon.EDIT.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete, edit);

    Binder<RobotsGroupEntity> binder = new Binder<>(RobotsGroupEntity.class);
    private ChangeHandler changeHandler;

    public RobotsGroupEditor(RobotsGroupRepository repository) {
        this.repository = repository;
        add(name, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(entity));
        edit.addClickListener(e -> edit.getUI().ifPresent(ui -> ui.navigate(RobotView.class, entity.getId())));
        setVisible(false);
    }


    public final void edit(RobotsGroupEntity robotsGroupEntity) {
        if (robotsGroupEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = robotsGroupEntity.getName()!= null;
        if (persisted) {
            // Find fresh entity for editing
            entity = repository.findByName(robotsGroupEntity.getName());
        }
        else {
            entity = robotsGroupEntity;
        }
        cancel.setVisible(persisted);
        edit.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(entity);

        setVisible(true);

        // Focus first name initially
        name.focus();
    }

    void save(){
        repository.save(entity);
        changeHandler.onChange();
    }

    void delete(){
        RobotsGroupEntity pipelineEntity = repository.findByName(entity.getName());
        repository.delete(pipelineEntity);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler handler) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = handler;
    }
}
