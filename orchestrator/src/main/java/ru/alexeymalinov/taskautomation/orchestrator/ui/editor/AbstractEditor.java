package ru.alexeymalinov.taskautomation.orchestrator.ui.editor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractEditor<T1 extends JpaRepository, T2> extends VerticalLayout implements KeyNotifier {

    T1 repository;
    T2 entity;

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel", VaadinIcon.CLOSE.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    HorizontalLayout actions = new HorizontalLayout(save,cancel,delete);

    ChangeHandler changeHandler;

    public AbstractEditor(T1 repository) {
        this.repository = repository;
        prepareView();
    }


    public void setChangeHandler(ChangeHandler handler) {
        changeHandler = handler;
    }

    private void prepareView() {
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(entity));

        setVisible(false);
    }

    public abstract void save();

    public abstract void printEditor();

    public abstract void delete();

    public abstract void edit(T2 entity);

}
