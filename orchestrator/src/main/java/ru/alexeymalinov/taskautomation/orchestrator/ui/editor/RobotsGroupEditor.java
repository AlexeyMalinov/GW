package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.RobotsGroupEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.RobotsGroupRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.view.RobotView;

@SpringComponent
@UIScope
public class RobotsGroupEditor extends AbstractEditor<RobotsGroupRepository, RobotsGroupEntity>{

    TextField name = new TextField("Name");

    Button edit = new Button("Edit", VaadinIcon.EDIT.create());

    Binder<RobotsGroupEntity> binder = new Binder<>(RobotsGroupEntity.class);

    public RobotsGroupEditor(RobotsGroupRepository repository) {
        super(repository);
        printEditor();
    }


    @Override
    public void edit(RobotsGroupEntity robotsGroupEntity) {
        if (robotsGroupEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = robotsGroupEntity.getName()!= null;
        if (persisted) {
            entity = repository.findByName(robotsGroupEntity.getName());
        } else {
            entity = robotsGroupEntity;
        }
        cancel.setVisible(persisted);
        edit.setVisible(persisted);

        binder.setBean(entity);

        setVisible(true);

        name.focus();
    }

    @Override
    public void printEditor() {
        binder.bindInstanceFields(this);
        actions.add(edit);
        edit.addClickListener(e -> edit.getUI().ifPresent(ui -> ui.navigate(RobotView.class, entity.getId())));
        add(name, actions);

    }

    @Override
    public void delete(){
        RobotsGroupEntity robotsGroupEntity = repository.findByName(entity.getName());
        repository.delete(robotsGroupEntity);
        changeHandler.onChange();
    }

    @Override
    public void save() {
        repository.save(entity);
        changeHandler.onChange();
    }

}
