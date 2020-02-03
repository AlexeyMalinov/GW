package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.JobEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.JobRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringComponent
@UIScope
public class StageEditor extends AbstractEditor<JobRepository, JobEntity>{

    TextField name = new TextField("Name");
    TextField taskName = new TextField("taskName");
    TextField repositoryUrl = new TextField("repositoryUrl");
    IntegerField robotsGroupId = new IntegerField("robotsGroupId");
    IntegerField count = new IntegerField("count");
    DatePicker startDatePicker = new DatePicker("Start date");
    IntegerField startHour;
    IntegerField startMinute;
    IntegerField startSecond;

    Button edit = new Button("Edit", VaadinIcon.EDIT.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete, edit);

    Binder<JobEntity> binder = new Binder<>(JobEntity.class);

    public StageEditor(JobRepository repository) {
        super(repository);
        printEditor();
    }


    @Override
    public void printEditor() {
        startHour = prepareIntegerField("Start hour", 0, 23);
        startMinute = prepareIntegerField("Start minute", 0, 59);
        startSecond = prepareIntegerField("Start second", 0, 59);

        HorizontalLayout timeParameters = new HorizontalLayout(startHour, startMinute, startSecond);

        binder.bindInstanceFields(this);
        add(name, taskName, repositoryUrl, robotsGroupId, startDatePicker, timeParameters, count, actions);

    }

    @Override
    public void save() {
        entity.setStartTime(getLocalDateTime());
        repository.save(entity);
        changeHandler.onChange();
    }

    @Override
    public void delete() {
        JobEntity jobEntity = repository.findByNameAndStage(entity.getName(), entity.getStage());
        repository.delete(jobEntity);
        changeHandler.onChange();
    }

    @Override
    public void edit(JobEntity jobEntity) {
        if (jobEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = jobEntity.getName() != null;
        if (persisted) {
            entity = repository.findByNameAndStage(jobEntity.getName(), jobEntity.getStage());
        } else {
            this.entity = jobEntity;
        }
        cancel.setVisible(persisted);
        edit.setVisible(persisted);

        binder.setBean(this.entity);

        setVisible(true);

        name.focus();
    }

    private IntegerField prepareIntegerField(String label, int min, int max) {
        IntegerField integerField = new IntegerField(label);
        integerField.setMin(min);
        integerField.setMax(max);
        return integerField;
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(
                startDatePicker.getValue(),
                LocalTime.of(startHour.getValue(), startMinute.getValue(), startSecond.getValue()));
    }
}
