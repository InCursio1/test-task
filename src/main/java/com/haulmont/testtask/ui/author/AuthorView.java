package com.haulmont.testtask.ui.author;

import com.haulmont.testtask.dao.AuthorDao;
import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Author;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;


public class AuthorView extends VerticalLayout {
    private Grid<Author> grid = new Grid<>(Author.class);
    private DaoServices<Author> services = new AuthorDao();
    private ListDataProvider<Author> dataProvider = new ListDataProvider<>(services.findAll());

    public AuthorView() {
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
                updateButtonComponent(), deleteButtonComponent());
        addComponent(headerLayout);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("lastName", "firstName", "middleName");
    }

    private void updateGridList() {
        grid.setDataProvider(dataProvider);
    }

    // Data filtering.
    private Component searchComponent() {
        TextField textField = new TextField();
        textField.setPlaceholder("Search by last name...");
        textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

        textField.addValueChangeListener(event ->
                dataProvider.addFilter(author ->
                        StringUtils.containsIgnoreCase(author.getLastName(), textField.getValue())));
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        return textField;
    }

    // Add button: opens the add window.
    private Component addButtonComponent() {
        Button button = new Button("Add");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event ->
                getUI().addWindow(new AuthorWindowAdd(dataProvider, services)));
        return button;
    }

    // Update selected row button: check if the selected row is checked,
    // if so, opens the update window.
    private Component updateButtonComponent() {
        Button button = new Button("Update");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Author author = null;
            for (Author authorSelected : grid.getSelectedItems()) {
                author = authorSelected;
            }
            if (author != null) {
                getUI().addWindow(new AuthorWindowUpdate(dataProvider, services, author));
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
            Author authorSelected = null;
            for (Author author : grid.getSelectedItems()) {
                authorSelected = author;
            }
            if (authorSelected != null) {
                try {
                    if (services.getById(authorSelected.getId()).getBooks().isEmpty()) {
                        services.delete(authorSelected);
                        dataProvider.getItems().remove(authorSelected);
                        dataProvider.refreshAll();
                        Notification.show("Success!",
                                "", Notification.Type.HUMANIZED_MESSAGE);
                    } else {
                        Notification.show("Warning: Can't remove a author with a relevant book!",
                                "", Notification.Type.HUMANIZED_MESSAGE);
                    }

                } catch (Exception e) {
                    Notification.show("Error: " + e, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }
}