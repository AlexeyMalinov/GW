package ru.alexeymalinov.taskautomation.orchestrator.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.JobEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.PipelineEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.entity.StageEntity;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.JobRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.PipelineRepository;
import ru.alexeymalinov.taskautomation.orchestrator.db.repository.StageRepository;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.PipelineEditor;
import ru.alexeymalinov.taskautomation.orchestrator.ui.editor.StageEditor;

@Route
public class StageView extends AbstractView<JobRepository, JobEntity, StageEditor> implements HasUrlParameter<Integer> {

    private StageRepository stageRepository;
    private StageEntity stageEntity;

    StageView(StageRepository stageRepository, JobRepository jobRepository, StageEditor editor) {
        super(jobRepository,editor,new Grid<>(JobEntity.class));
        this.stageRepository = stageRepository;
    }

    @Override
    public void print() {
        listItem();

        Button addJobButton = new Button("Create Job", VaadinIcon.PLUS.create());
        addJobButton.addClickListener(e -> editor.edit(new JobEntity(stageEntity)));

        Button backStagesButton = new Button("Stages", VaadinIcon.ARROW_BACKWARD.create());
        backStagesButton.addClickListener(e -> {
            backStagesButton.getUI().ifPresent(ui -> ui.navigate(PipelineView.class, stageEntity.getPipeline().getId()));
        });

        HorizontalLayout actions = new HorizontalLayout(addJobButton, backStagesButton, backMainButton);

        grid.addColumns("taskName", "repositoryUrl", "robotsGroupId", "startTime", "count");

        add(actions, grid, this.editor);
    }

    @Override
    protected void listItem() {
        grid.setItems(repository.findAllByStage(stageEntity));
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer id) {
        stageEntity = stageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Stage not found"));
        print();
    }

}
