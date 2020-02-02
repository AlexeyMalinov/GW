package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.view.StageView;

@SpringComponent
@UIScope
public class PipelineEditor extends VerticalLayout implements KeyNotifier {
    private final StageRepository repository;

    private StageEntity entity;

    TextField name = new TextField("Name");
    IntegerField previousStageId = new IntegerField("PreviousId");
    IntegerField nextStageId = new IntegerField("NextId");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel", VaadinIcon.CLOSE.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button edit = new Button("Edit", VaadinIcon.EDIT.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete, edit);

    Binder<StageEntity> binder = new Binder<>(StageEntity.class);
    private ChangeHandler changeHandler;


    public PipelineEditor(StageRepository repository){
        this.repository = repository;
        add(name, previousStageId, nextStageId, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editStage(entity));
        edit.addClickListener(e -> UI.getCurrent().navigate(StageView.class, entity.getId()));
        setVisible(false);
    }

    void save(){
        repository.save(entity);
        changeHandler.onChange();
    }

    void delete(){
        StageEntity stageEntity = repository.findByNameAndPipeline(entity.getName(), entity.getPipeline());
        repository.delete(stageEntity);
        changeHandler.onChange();
    }

    public final void editStage(StageEntity stageEntity) {
        if (stageEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = stageEntity.getName()!= null;
        if (persisted) {
            // Find fresh entity for editing
            entity = repository.findByNameAndPipeline(stageEntity.getName(), stageEntity.getPipeline());
        }
        else {
            this.entity = stageEntity;
        }
        cancel.setVisible(persisted);
        edit.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(this.entity);

        setVisible(true);

        // Focus first name initially
        name.focus();
    }

    public void setChangeHandler(ChangeHandler handler) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = handler;
    }
}