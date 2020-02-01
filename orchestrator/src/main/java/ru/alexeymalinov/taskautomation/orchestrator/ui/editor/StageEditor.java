package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.JobEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.JobRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@SpringComponent
@UIScope
public class StageEditor extends VerticalLayout implements KeyNotifier {

    private final JobRepository repository;

    private JobEntity entity;

    TextField name = new TextField("Name");
    TextField taskName = new TextField("taskName");
    TextField repositoryUrl = new TextField("repositoryUrl");
    IntegerField robotsGroupId = new IntegerField("robotsGroupId");
    IntegerField count = new IntegerField("count");
    DatePicker startDatePicker = new DatePicker("Start date");
    IntegerField startHour;
    IntegerField startMinute;
    IntegerField startSecond;


    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel", VaadinIcon.CLOSE.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button edit = new Button("Edit", VaadinIcon.EDIT.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete, edit);

    Binder<JobEntity> binder = new Binder<>(JobEntity.class);
    private ChangeHandler changeHandler;


    public StageEditor(JobRepository repository) {
        this.repository = repository;

        startHour = prepareIntegerField("Start hour", 0, 23);
        startMinute = prepareIntegerField("Start minute", 0, 59);
        startSecond = prepareIntegerField("Start second", 0, 59);

        HorizontalLayout timeParameters = new HorizontalLayout(startHour, startMinute, startSecond);

        add(name, taskName, repositoryUrl, robotsGroupId, startDatePicker, timeParameters, count, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(entity));
        setVisible(false);


    }

    void save() {
        entity.setStartTime(getLocalDateTime());
        repository.save(entity);
        changeHandler.onChange();
    }

    void delete() {
        JobEntity jobEntity = repository.findByNameAndStage(entity.getName(), entity.getStage());
        repository.delete(jobEntity);
        changeHandler.onChange();
    }

    public final void edit(JobEntity jobEntity) {
        if (jobEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = jobEntity.getName() != null;
        if (persisted) {
            // Find fresh entity for editing
            entity = repository.findByNameAndStage(jobEntity.getName(), jobEntity.getStage());
        } else {
            this.entity = jobEntity;
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

    private TimePicker prepareTimePicker(int min, int max, int step, ChronoUnit unit) {
        TimePicker timePicker = new TimePicker();
        timePicker.setMin(String.valueOf(min));
        timePicker.setMax(String.valueOf(max));
        timePicker.setStep(Duration.of(step, unit));
        return timePicker;
    }

    public void setChangeHandler(ChangeHandler handler) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = handler;
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
