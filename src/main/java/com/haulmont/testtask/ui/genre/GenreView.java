package com.haulmont.testtask.ui.genre;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.GenreDao;
import com.haulmont.testtask.entity.Genre;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;

public class GenreView extends VerticalLayout {
    private Grid<Genre> grid = new Grid<>(Genre.class);
    private DaoServices<Genre> services = new GenreDao();
    private ListDataProvider<Genre> dataProvider = new ListDataProvider<>(services.findAll());

    public GenreView() {
        setSizeFull();
        configurationHeader();

        addComponent(grid);
        updateGridList();
        configureGrid();
    }

    // Buttons layout.
    private void configurationHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();

        headerLayout.addComponents(searchComponent(), addButtonComponent(),
                updateButtonComponent(), deleteButtonComponent(), statisticButtonComponent());
        addComponent(headerLayout);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("title");
    }

    private void updateGridList() {
        grid.setDataProvider(dataProvider);
    }

    // Data filtering.
    private Component searchComponent() {
        TextField textField = new TextField();
        textField.setPlaceholder("Search by title...");
        textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

        textField.addValueChangeListener(event ->
                dataProvider.addFilter(genre ->
                        StringUtils.containsIgnoreCase(genre.getTitle(), textField.getValue())));
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        return textField;
    }

    // Add button: opens the add window.
    private Component addButtonComponent() {
        Button button = new Button("Add");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event ->
                getUI().addWindow(new GenreWindowAdd(dataProvider, services)));
        return button;
    }

    // Update selected row button: check if the selected row is checked,
    // if so, opens the update window.
    private Component updateButtonComponent() {
        Button button = new Button("Update");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Genre genre = null;
            for (Genre genreSelected : grid.getSelectedItems()) {
                genre = genreSelected;
            }
            if (genre != null) {
                getUI().addWindow(new GenreWindowUpdate(dataProvider, services, genre));
            } else {
                Notification.show("Warning: You did not select any row!",
                        "", Notification.Type.WARNING_MESSAGE);
            }
        });
        return button;
    }

    // Delete selected row button: check if the selected row is checked,
    // if so, delete the data and updates the table.
    private Component deleteButtonComponent() {
        Button button = new Button("Delete");
        button.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Genre genreSelected = null;
            for (Genre genre : grid.getSelectedItems()) {
                genreSelected = genre;
            }
            if (genreSelected != null) {
                try {
                    if (services.getById(genreSelected.getId()).getBooks().isEmpty()) {
                        services.delete(genreSelected);
                        dataProvider.getItems().remove(genreSelected);
                        dataProvider.refreshAll();
                        Notification.show("Success!",
                                "", Notification.Type.HUMANIZED_MESSAGE);
                    } else {
                        Notification.show("Warning: Can't remove a genre with a relevant book!",
                                "", Notification.Type.HUMANIZED_MESSAGE);
                    }

                } catch (Exception e) {
                    Notification.show("Error: " + e, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }

    private Component statisticButtonComponent() {
        Button button = new Button("Statistic");
        button.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> getUI().addWindow(new GenreWindowStatistic()));
        return button;
    }
}